package cz.george.superfastscanner;

import cz.george.superfastscanner.bytecode.clazz.*;
import cz.george.superfastscanner.bytecode.hashmap.ClassesMap;
import cz.george.superfastscanner.bytecode.hashmap.MethodsMap;
import cz.george.superfastscanner.hierarchytree.InheritanceTreeNode;
import cz.george.superfastscanner.hierarchytree.hashmap.InheritanceHierarchyMap;

import java.util.*;

/**
 * Created by John on 4/8/2017.
 */
public class AnalysisUtils {

    public static Set<Method> findUsages(Method method, HashMaps tool) {
        if (method.ownerClass.name == null)
            return null;

        // instrcutionsMap have hashcode from Instruction, so I convert Method to Instruction for confidence...
        Instruction instruction = new Instruction(method.name, method.description, method.ownerClass.name, null);

        // Obtain complete list of instructions matching with given Instruction
        List<Instruction> completeInstructionsOccurence = tool.instructionsMap.map.get(instruction);

        // Obtain complete list of given method used in parrents and iterfaces of Class. It will be considered also as usages.
        Set<Method> completeParrentsOccurencee = null;
        InheritanceHierarchyMap methodNamesMap = new InheritanceHierarchyMap(new InheritanceTreeNode(method.ownerClass, tool));
        completeParrentsOccurencee = methodNamesMap.map.get(method.name + " " + method.description); // = methodNamesMap.map.get(method.name);

        // Remove itself from this hierarchy
        completeParrentsOccurencee.remove(method);


        Set<Method> usages = new HashSet<>();

        // Usage is method, in which instrcution is used
        if (completeInstructionsOccurence != null)
            for (Instruction i : completeInstructionsOccurence) {
                usages.add(i.ownerMethod);
            }

        // And finally usages are also thoose which method overrides
        if (completeParrentsOccurencee != null)
            usages.addAll(completeParrentsOccurencee);

        return usages;
    }

    public static Set<Clazz> findInterfaces(Clazz clazz, ClassesMap classesMap) {
        Set<Clazz> realInterfaces = new HashSet<>();
        Clazz realClazz = findRealOccurenceInMap(clazz, classesMap);

        if(realClazz == null)
            return realInterfaces;

        String[] interfaces = realClazz.interfaces;
        for(String interf : interfaces) {
            Clazz realInterf = findRealOccurenceInMap(new Clazz(interf, null, null), classesMap);
            if(realInterf != null)
            realInterfaces.add(realInterf);
        }

        return realInterfaces;
    }

    public static Clazz findSuperClass(Clazz clazz, ClassesMap classesMap) {
        Clazz realClazz = findRealOccurenceInMap(clazz, classesMap);
        Clazz superClass = findRealOccurenceInMap(new Clazz(realClazz.superName, null, null), classesMap);
        return superClass;
    }

    public static Method findRealOccurenceInMap(Method method, MethodsMap methodsMap) {
        return methodsMap.map.get(method);
    }

    public static Clazz findRealOccurenceInMap(Clazz clazz, ClassesMap classesMap) {
        Clazz realClass = classesMap.map.get(clazz);
        return realClass;
    }

//    public static MethodNamesMap findParrentsAndInterfaces(Clazz clazz, ClassesMap classesMap) {
//        Set<Clazz> hierarchytree = findInterfacesHierarchy(clazz, classesMap);
//        hierarchytree.addAll(findSuperClassHierarchy(clazz, classesMap));
//        return new MethodNamesMap(hierarchytree);
//    }
//
//    public static Set<Clazz> findSuperClassHierarchy(Clazz clazz, ClassesMap classesMap) {
//        Set<Clazz> allSuperClasses = new HashSet<>();
//
//        Clazz realClazz = findRealOccurenceInMap(clazz, classesMap);
//
//        if ("java/lang/Object".equals(realClazz.superName) || realClazz.superName == null)
//            return allSuperClasses;
//
//        Clazz realSuperClass = findRealOccurenceInMap(new Clazz(realClazz.superName, null, null), classesMap);
//        allSuperClasses.add(realSuperClass);
//        allSuperClasses.addAll(findSuperClassHierarchy(realSuperClass, classesMap));
//
//        return allSuperClasses;
//    }
//
//    public static Set<Clazz> findInterfacesHierarchy(Clazz clazz, ClassesMap classesMap) {
//        Set<Clazz> allInterfaces = new HashSet<>();
//
//        Clazz realClass = findRealOccurenceInMap(clazz, classesMap);
//
//        for (String interf : realClass.interfaces) {
//            Clazz realInterf = findRealOccurenceInMap(new Clazz(interf, null, null), classesMap);
//            allInterfaces.add(realInterf);
//            allInterfaces.addAll(findInterfacesHierarchy(realInterf, classesMap));
//        }
//
//        return allInterfaces;
//    }
}
