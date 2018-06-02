package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.parsedbytecode.HashMaps;
import cz.george.superfastscanner.utils.tree.Node;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * When constructor is called, all parents of given Clazz are recursively loaded and all interfaces or abstract classes
 * of its methods list are enriched for methods which are inherited but not mentioned in the class.
 *
 * For example: if interface A (which is also considered as class) extends interface B, then A have same methods as B.
 * Unfortunatelly, this inherited methods are not mentioned in class parsedbytecode. That's the reason why they have to be
 * obtained by thos class.
 */
public class ClassInheritanceNode extends Node<Clazz> {
    private @Getter Set<ClassInheritanceNode> interfaces = new HashSet<>(); // although interface can only Extends, it's considered as Implements
    private @Getter ClassInheritanceNode superClass = null;
    private HashMaps hashMaps;
    private AnalysisUtils analysisUtils;

    public ClassInheritanceNode(Clazz clazz, HashMaps hashMaps) {
        super(clazz);
        this.hashMaps = hashMaps;
        this.analysisUtils = new AnalysisUtils(hashMaps);
        setValue(clazz);

        enrichClazzMethods();
    }

    private void enrichClazzMethods() {
        Set<Method> newClazzMethods = walkToLeafInterfaces();
        this.getValue().getMethods().addAll( newClazzMethods );

        newClazzMethods = walkToLeafSuperClass();
        this.getValue().getMethods().addAll(newClazzMethods);
    }

    /**
     *  Bbring down all methods of parrent interface
     * @return Set of method from interface
     */
    public Set<Method> walkToLeafInterfaces() {
        Set<Method> inheritedMethods = new HashSet<>();

        Set<Clazz> interfaces = analysisUtils.findInterfaces(getValue());
        for(Clazz interfac : interfaces) {
            this.interfaces.add(new ClassInheritanceNode(interfac, hashMaps)); // RECURSION
        }

        // Bring down methods from parrent
        for(Clazz interfac : interfaces) {
            for(Method method : interfac.getMethods()) {
                Method inheritedMethodNewDesign = new Method(method.getName(), method.getDescription(), getValue());
                inheritedMethods.add(inheritedMethodNewDesign);
            }
        }

        return inheritedMethods;
    }

    /**
     *  Bbring down all methods of parrent superClass
     * @return Set of method from superClass
     */
    public Set<Method> walkToLeafSuperClass() {
        Set<Method> inheritedMethods = new HashSet<>();

        Clazz foundSuperClass = analysisUtils.findSuperClass(getValue());
        if(foundSuperClass != null) {
            this.superClass = new ClassInheritanceNode(foundSuperClass, hashMaps); // RECURSION

            // Bring methods from parrent
            for (Method method : foundSuperClass.getMethods()) {
                Method inheritedMethodNewDesign = new Method(method.getName(), method.getDescription(), getValue());
                inheritedMethods.add(inheritedMethodNewDesign);
            }
        }

        return inheritedMethods;
    }

    public void setValue(Clazz value) {
        Clazz realOccurence = analysisUtils.findRealOccurenceInMap(value);

        if (realOccurence == null)
            throw new IllegalStateException("Class '" + value + "' not found.");

        this.value = realOccurence;
    }
}
