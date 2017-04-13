package cz.george.superfastscanner.bytecode.speedmap;

import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;

import java.util.*;

/**
 * Methods-oriented hashmap.
 */
public class MethodsMap {
    public Map<Method, Method> map = new HashMap<>();

    public MethodsMap(Set<Clazz> classes) { initialize(classes); }

    private void initialize(Set<Clazz> classes) {
        for(Clazz clazz : classes)
            for(Method method : clazz.methods)
                    map.put(method, method);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Method, Method> entry : map.entrySet()) {
            stringBuilder(sb, entry.getKey().toString(), "\n");
        }
        return sb.toString();
    }

    private static StringBuilder stringBuilder(StringBuilder sb, String... s) {
        Arrays.stream(s).forEach( x -> sb.append(x) );
        return sb;
    }
}
