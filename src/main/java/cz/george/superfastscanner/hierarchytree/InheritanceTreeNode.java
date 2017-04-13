package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.HashMaps;
import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;

import java.util.HashSet;
import java.util.Set;

/**
 * When constructor is called, all parents of given Clazz are recursively loaded and all interfaces or abstract classes
 * of its methods list are enriched for methods which are inherited but not mentioned in the class.
 *
 * For example: if interface A (which is also considered as class) extends interface B, then A have same methods as B.
 * Unfortunatelly, this inherited methods are not mentioned in class bytecode. That's the reason why they have to be
 * obtained by thos class.
 */
public class InheritanceTreeNode {
    public Clazz clazz;
    public Set<InheritanceTreeNode> interfaces = new HashSet<>(); // although interface can only Extends, it's considered as Implements
    public InheritanceTreeNode superClass = null;
    HashMaps hashMaps;

    public InheritanceTreeNode(Clazz clazz, HashMaps hashMaps) {
        this.clazz = AnalysisUtils.findRealOccurenceInMap(clazz, hashMaps.classesMap);

        if (this.clazz == null)
            throw new IllegalStateException("Class not found.");

        this.hashMaps = hashMaps;

        enrichClazzMethods();
    }

    private void enrichClazzMethods() {
        Set<Method> newClazzMethods = walkToLeafInterfaces();
        this.clazz.methods.addAll( newClazzMethods );

        newClazzMethods = walkToLeafSuperClass();
        this.clazz.methods.addAll(newClazzMethods);
    }

    /**
     *  Bbring down all methods of parrent interface
     * @return Set of method from interface
     */
    public Set<Method> walkToLeafInterfaces() {
        Set<Method> inheritedMethods = new HashSet<>();

        Set<Clazz> interfaces = AnalysisUtils.findInterfaces(clazz, hashMaps.classesMap);
        for(Clazz interfac : interfaces) {
            this.interfaces.add(new InheritanceTreeNode(interfac, hashMaps)); // RECURSION
        }

        // Bring down methods from parrent
        for(Clazz interfac : interfaces) {
            for(Method method : interfac.methods) {
                Method inheritedMethodNewDesign = new Method(method.name, method.description, this.clazz);
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

        Clazz foundSuperClass = AnalysisUtils.findSuperClass(clazz, hashMaps.classesMap);
        if(foundSuperClass != null) {
            this.superClass = new InheritanceTreeNode(foundSuperClass, hashMaps); // RECURSION

            // Bring methods from parrent
            for (Method method : foundSuperClass.methods) {
                Method inheritedMethodNewDesign = new Method(method.name, method.description, this.clazz);
                inheritedMethods.add(inheritedMethodNewDesign);
            }
        }

        return inheritedMethods;
    }



}
