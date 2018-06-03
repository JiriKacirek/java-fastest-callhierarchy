package cz.george.superfastscanner.parsedbytecode;

import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.hashmap.ClassesHashMap;
import cz.george.superfastscanner.parsedbytecode.hashmap.InstructionsHashMap;
import cz.george.superfastscanner.parsedbytecode.hashmap.MethodsMap;

import java.util.Set;

public class ParsedClassesContainer {
    public MethodsMap methodsMap;
    public ClassesHashMap classesHashMap;
    public InstructionsHashMap instructionsMap;

    public ParsedClassesContainer(Set<Clazz> parsedBinaryClasses) {
        methodsMap = new MethodsMap(parsedBinaryClasses);
        classesHashMap = new ClassesHashMap(parsedBinaryClasses);
        instructionsMap = new InstructionsHashMap(parsedBinaryClasses);
    }
}
