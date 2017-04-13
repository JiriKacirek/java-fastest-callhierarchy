package cz.george.superfastscanner.bytecode.hashmap;

import cz.george.superfastscanner.bytecode.clazz.*;

import java.util.*;

/**
 * Instructions-oriented hashmap
 */
public class InstructionsMap {
    public Map<Instruction, List<Instruction>> map = new HashMap<>();

    public InstructionsMap(Set<Clazz> classes) { initialize(classes); }

    private void initialize(Set<Clazz> classes) {
        for(Clazz clazz : classes)
            for(Method method : clazz.methods)
                for(Instruction instruction : method.instructions) {
                    put(instruction, instruction);
                }
    }

    private void put(Instruction key, Instruction value) {
        if(map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<Instruction> instructions = new ArrayList<>();
            instructions.add(value);
            map.put(key, instructions);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Instruction, List<Instruction>> entry : map.entrySet()) {
            stringBuilder(sb, entry.getKey().toString(), "\n");
            for(Instruction instruction : entry.getValue()) {
                stringBuilder(sb, "\t", instruction.ownerMethod.ownerClass.name, " ");
                stringBuilder(sb, instruction.ownerMethod.toString(), "\n");

            }
        }
        return sb.toString();
    }

    private static StringBuilder stringBuilder(StringBuilder sb, String... s) {
        Arrays.stream(s).forEach( x -> sb.append(x) );
        return sb;
    }
}
