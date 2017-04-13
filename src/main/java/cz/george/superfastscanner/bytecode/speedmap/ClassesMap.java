package cz.george.superfastscanner.bytecode.speedmap;

import cz.george.superfastscanner.bytecode.clazz.Clazz;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Class-oriented hashmap
 */
public class ClassesMap {
    public Map<Clazz, Clazz> map = new HashMap<>();

    public ClassesMap(Set<Clazz> classes) { initialize(classes); }

    private void initialize(Set<Clazz> classes) {
        for(Clazz clazz : classes)
                map.put(clazz, clazz);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Clazz, Clazz> entry : map.entrySet()) {
            stringBuilder(sb, entry.getKey().toString(), "\n");
        }
        return sb.toString();
    }

    private static StringBuilder stringBuilder(StringBuilder sb, String... s) {
        Arrays.stream(s).forEach(x -> sb.append(x) );
        return sb;
    }
}
