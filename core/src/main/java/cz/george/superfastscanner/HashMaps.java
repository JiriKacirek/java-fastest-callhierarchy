package cz.george.superfastscanner;

import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;
import cz.george.superfastscanner.parsedbytecode.hashmap.ClassesHashMap;
import cz.george.superfastscanner.parsedbytecode.hashmap.InstructionsHashMap;
import cz.george.superfastscanner.hierarchytree.CallersTreeNode;
import cz.george.superfastscanner.parsedbytecode.hashmap.MethodsMap;

import java.util.Set;

public class HashMaps {
    public MethodsMap methodsMap;
    public ClassesHashMap classesHashMap;
    public InstructionsHashMap instructionsMap;

    HashMaps(Set<Clazz> parsedBinaryClasses) {
        methodsMap = new MethodsMap(parsedBinaryClasses);
        classesHashMap = new ClassesHashMap(parsedBinaryClasses);
        instructionsMap = new InstructionsHashMap(parsedBinaryClasses);
    }
}
