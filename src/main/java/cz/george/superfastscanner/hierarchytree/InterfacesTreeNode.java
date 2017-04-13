package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.StaticAnalysisTool;
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
public class InterfacesTreeNode {
    public Clazz clazz;
    public Set<InterfacesTreeNode> interfaces = new HashSet<>(); // although interface can only Extends, it's considered as Implements
    public InterfacesTreeNode superClass = null;
    StaticAnalysisTool staticAnalysisTool;

    public InterfacesTreeNode(Clazz clazz, StaticAnalysisTool staticAnalysisTool) {
        this.clazz = AnalysisUtils.findRealOccurenceInMap(clazz, staticAnalysisTool.classesMap);
        this.staticAnalysisTool = staticAnalysisTool;

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

        Set<Clazz> interfaces = AnalysisUtils.findInterfaces(clazz, staticAnalysisTool.classesMap);
        for(Clazz interfac : interfaces) {
            this.interfaces.add(new InterfacesTreeNode(interfac, staticAnalysisTool)); // RECURSION
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

        Clazz foundSuperClass = AnalysisUtils.findSuperClass(clazz, staticAnalysisTool.classesMap);
        if(foundSuperClass != null) {
            this.superClass = new InterfacesTreeNode(foundSuperClass, staticAnalysisTool); // RECURSION

            // Bring methods from parrent
            for (Method method : foundSuperClass.methods) {
                Method inheritedMethodNewDesign = new Method(method.name, method.description, this.clazz);
                inheritedMethods.add(inheritedMethodNewDesign);
            }
        }

        return inheritedMethods;
    }



}
