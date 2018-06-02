package cz.george.superfastscanner;

import cz.george.superfastscanner.parsedbytecode.clazz.*;
import cz.george.superfastscanner.parsedbytecode.hashmap.ClassesHashMap;
import cz.george.superfastscanner.parsedbytecode.hashmap.MethodsMap;
import cz.george.superfastscanner.hierarchytree.ClassInheritanceNode;
import cz.george.superfastscanner.hierarchytree.hashmap.InheritanceHierarchyHashMap;

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
        List<Instruction> completeInstructionsOccurence = tool.instructionsMap.getMap().get(instruction);

        // Obtain complete list of given method used in parrents and iterfaces of Class. It will be considered also as usages.
        Set<Method> completeParrentsOccurencee = null;
        InheritanceHierarchyHashMap methodNamesMap = new InheritanceHierarchyHashMap(new ClassInheritanceNode(method.ownerClass, tool));
        completeParrentsOccurencee = methodNamesMap.getMap().get(method.name + " " + method.description); // = methodNamesMap.map.get(method.name);

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

    public static Set<Clazz> findInterfaces(Clazz clazz, ClassesHashMap classesHashMap) {
        Set<Clazz> realInterfaces = new HashSet<>();
        Clazz realClazz = findRealOccurenceInMap(clazz, classesHashMap);

        if(realClazz == null)
            return realInterfaces;

        String[] interfaces = realClazz.interfaces;
        for(String interf : interfaces) {
            Clazz realInterf = findRealOccurenceInMap(new Clazz(interf, null, null), classesHashMap);
            if(realInterf != null)
            realInterfaces.add(realInterf);
        }

        return realInterfaces;
    }

    public static Clazz findSuperClass(Clazz clazz, ClassesHashMap classesHashMap) {
        Clazz realClazz = findRealOccurenceInMap(clazz, classesHashMap);
        Clazz superClass = findRealOccurenceInMap(new Clazz(realClazz.superName, null, null), classesHashMap);
        return superClass;
    }

    public static Method findRealOccurenceInMap(Method method, MethodsMap methodsMap) {
        return methodsMap.getMap().get(method);
    }

    public static Clazz findRealOccurenceInMap(Clazz clazz, ClassesHashMap classesHashMap) {
        Clazz realClass = classesHashMap.getMap().get(clazz);
        return realClass;
    }

//    public static MethodNamesHashMap findParrentsAndInterfaces(Clazz clazz, ClassesHashMap classesHashMap) {
//        Set<Clazz> hierarchytree = findInterfacesHierarchy(clazz, classesHashMap);
//        hierarchytree.addAll(findSuperClassHierarchy(clazz, classesHashMap));
//        return new MethodNamesHashMap(hierarchytree);
//    }
//
//    public static Set<Clazz> findSuperClassHierarchy(Clazz clazz, ClassesHashMap classesHashMap) {
//        Set<Clazz> allSuperClasses = new HashSet<>();
//
//        Clazz realClazz = findRealOccurenceInMap(clazz, classesHashMap);
//
//        if ("java/lang/Object".equals(realClazz.superName) || realClazz.superName == null)
//            return allSuperClasses;
//
//        Clazz realSuperClass = findRealOccurenceInMap(new Clazz(realClazz.superName, null, null), classesHashMap);
//        allSuperClasses.add(realSuperClass);
//        allSuperClasses.addAll(findSuperClassHierarchy(realSuperClass, classesHashMap));
//
//        return allSuperClasses;
//    }
//
//    public static Set<Clazz> findInterfacesHierarchy(Clazz clazz, ClassesHashMap classesHashMap) {
//        Set<Clazz> allInterfaces = new HashSet<>();
//
//        Clazz realClass = findRealOccurenceInMap(clazz, classesHashMap);
//
//        for (String interf : realClass.interfaces) {
//            Clazz realInterf = findRealOccurenceInMap(new Clazz(interf, null, null), classesHashMap);
//            allInterfaces.add(realInterf);
//            allInterfaces.addAll(findInterfacesHierarchy(realInterf, classesHashMap));
//        }
//
//        return allInterfaces;
//    }
}
