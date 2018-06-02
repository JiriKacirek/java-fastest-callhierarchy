package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.HashMaps;
import cz.george.superfastscanner.utils.tree.Node;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;

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
    public Set<ClassInheritanceNode> interfaces = new HashSet<>(); // although interface can only Extends, it's considered as Implements
    public ClassInheritanceNode superClass = null;
    HashMaps hashMaps;

    public ClassInheritanceNode(Clazz clazz, HashMaps hashMaps) {
        super(clazz);
        this.hashMaps = hashMaps;
        setNodeValue(clazz);

        enrichClazzMethods();
    }

    private void enrichClazzMethods() {
        Set<Method> newClazzMethods = walkToLeafInterfaces();
        this.getNodeValue().methods.addAll( newClazzMethods );

        newClazzMethods = walkToLeafSuperClass();
        this.getNodeValue().methods.addAll(newClazzMethods);
    }

    /**
     *  Bbring down all methods of parrent interface
     * @return Set of method from interface
     */
    public Set<Method> walkToLeafInterfaces() {
        Set<Method> inheritedMethods = new HashSet<>();

        Set<Clazz> interfaces = AnalysisUtils.findInterfaces(getNodeValue(), hashMaps.classesHashMap);
        for(Clazz interfac : interfaces) {
            this.interfaces.add(new ClassInheritanceNode(interfac, hashMaps)); // RECURSION
        }

        // Bring down methods from parrent
        for(Clazz interfac : interfaces) {
            for(Method method : interfac.methods) {
                Method inheritedMethodNewDesign = new Method(method.name, method.description, getNodeValue());
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

        Clazz foundSuperClass = AnalysisUtils.findSuperClass(getNodeValue(), hashMaps.classesHashMap);
        if(foundSuperClass != null) {
            this.superClass = new ClassInheritanceNode(foundSuperClass, hashMaps); // RECURSION

            // Bring methods from parrent
            for (Method method : foundSuperClass.methods) {
                Method inheritedMethodNewDesign = new Method(method.name, method.description, getNodeValue());
                inheritedMethods.add(inheritedMethodNewDesign);
            }
        }

        return inheritedMethods;
    }

    @Override
    public void setNodeValue(Clazz nodeValue) {
        Clazz realOccurence = AnalysisUtils.findRealOccurenceInMap(nodeValue, hashMaps.classesHashMap);

        if (realOccurence == null)
            throw new IllegalStateException("Class '" + nodeValue + "' not found.");

        super.setNodeValue(realOccurence);
    }
}
