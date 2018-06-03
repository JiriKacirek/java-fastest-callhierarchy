package kacirekj.fastcallhierarchy.parsedbytecode;

import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.hashmap.ClassesHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.hashmap.InstructionsHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.hashmap.MethodsMap;

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
