package kacirekj.fastcallhierarchy.parsedbytecode.hashmap;

import kacirekj.fastcallhierarchy.datastructures.ArrayListHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Instruction;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;

import java.util.*;

/**
 * Instructions-oriented hashmap
 */
public class InstructionsHashMap extends ArrayListHashMap<Instruction, Instruction> {
    public InstructionsHashMap(Set<Clazz> classes) {
        for (Clazz clazz : classes)
            for (Method method : clazz.getMethods())
                for (Instruction instruction : method.getInstructions()) {
                    putEntry(instruction, instruction);
                }
    }
}
