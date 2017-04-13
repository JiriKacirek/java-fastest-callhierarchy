package cz.george.superfastscanner;

import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;
import cz.george.superfastscanner.bytecode.hashmap.ClassesMap;
import cz.george.superfastscanner.bytecode.hashmap.InstructionsMap;
import cz.george.superfastscanner.hierarchytree.CallersTreeNode;
import cz.george.superfastscanner.bytecode.hashmap.MethodsMap;

import java.util.Set;

/**
 * Created by John on 4/8/2017.
 */
public class HashMaps {
    // The ultimate Memory-complexity performance solution
    public MethodsMap methodsMap;
    public ClassesMap classesMap;
    public InstructionsMap instructionsMap;

    HashMaps(Set<Clazz> parsedBinaryClasses) {
        methodsMap = new MethodsMap(parsedBinaryClasses);
        classesMap = new ClassesMap(parsedBinaryClasses);
        instructionsMap = new InstructionsMap(parsedBinaryClasses);
    }

    CallersTreeNode getCallers(Method method) {
        return new CallersTreeNode(method, this);
    }


}
