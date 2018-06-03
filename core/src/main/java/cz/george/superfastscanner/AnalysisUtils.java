package cz.george.superfastscanner;

import cz.george.superfastscanner.datastructures.ParsedClassesContainer;
import cz.george.superfastscanner.parsedbytecode.clazz.*;
import cz.george.superfastscanner.hierarchytree.ClassInheritanceNode;
import cz.george.superfastscanner.hierarchytree.hashmap.InheritanceHierarchyHashMap;
import lombok.var;

import java.util.*;

public class AnalysisUtils {

    private ParsedClassesContainer parsedClassesContainer;

    public AnalysisUtils(ParsedClassesContainer parsedClassesContainer) {
        this.parsedClassesContainer = parsedClassesContainer;
    }

    /**
     * @param method For which callers have to be found
     * @return Empty Set if any caller has been found
     */
    public Set<Method> findUsages(Method method) {
        if (method.getOwnerClass().getName() == null)
            return null;

        // instrcutionsMap have hashcode from Instruction, so I convert Method to Instruction for confidence...
        var instruction = new Instruction(
                method.getName(),
                method.getDescription(),
                method.getOwnerClass().getName(),
                null);

        // Obtain complete list of instructions matching with given Instruction
        List<Instruction> completeInstructionsOccurence = parsedClassesContainer.instructionsMap.getMap().get(instruction);

        // Obtain complete list of given method used in parrents and iterfaces of Class. It will be considered also
        // as usages.
        Set<Method> completeParrentsOccurencee = null;
        var methodNamesMap = new InheritanceHierarchyHashMap(new ClassInheritanceNode(method.getOwnerClass(), parsedClassesContainer));
        completeParrentsOccurencee = methodNamesMap.getMap().get(method.getName() + " " + method.getDescription());

        // Remove itself from this hierarchy
        completeParrentsOccurencee.remove(method);


        Set<Method> usages = new HashSet<>();

        // Usage is method, in which instrcution is used
        if (completeInstructionsOccurence != null)
            for (Instruction i : completeInstructionsOccurence) {
                usages.add(i.getOwnerMethod());
            }

        // And finally usages are also thoose which method overrides
        if (completeParrentsOccurencee != null)
            usages.addAll(completeParrentsOccurencee);

        return usages;
    }

    /**
     * @param clazz For which interfaces have to be found
     * @return Empty Set if any Interface has been found
     */
    public Set<Clazz> findInterfaces(Clazz clazz) {
        Set<Clazz> realInterfaces = new HashSet<>();
        Clazz realClazz = findRealOccurenceInMap(clazz);

        if(realClazz == null)
            return realInterfaces;

        String[] interfaces = realClazz.getInterfaces();
        for(String interf : interfaces) {
            Clazz realInterf = findRealOccurenceInMap(new Clazz(interf, null, null));
            if(realInterf != null)
            realInterfaces.add(realInterf);
        }

        return realInterfaces;
    }

    /**
     * @param clazz For which interfaces have to be found
     * @return Null if any super Class has been found
     */
    public Clazz findSuperClass(Clazz clazz) {
        Clazz realClass = findRealOccurenceInMap(clazz);
        Clazz superClass = findRealOccurenceInMap(new Clazz(realClass.getSuperName(), null, null));
        return superClass;
    }

    /**
     * @param method Custom created Method which maybe not exists in parsed Bytecode
     * @return null if @method not exists in parsed bytecode or instance of real parsed Method
     */
    public Method findRealOccurenceInMap(Method method) {
        return parsedClassesContainer.methodsMap.getMap().get(method);
    }

    public Clazz findRealOccurenceInMap(Clazz clazz) {
        Clazz realClass = parsedClassesContainer.classesHashMap.getMap().get(clazz);
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
