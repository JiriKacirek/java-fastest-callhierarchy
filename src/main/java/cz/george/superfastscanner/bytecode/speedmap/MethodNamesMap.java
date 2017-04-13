package cz.george.superfastscanner.bytecode.speedmap;

import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;

import java.util.*;

/**
 * Method-names-oriented map. Used for finding overrrided methods in parrents.
 */
public class MethodNamesMap {
    public Map<String, List<Method>> map = new HashMap<>();

    public MethodNamesMap(Set<Clazz> classes) { initialize(classes); }

    private void initialize(Set<Clazz> classes) {
        for(Clazz clazz : classes)
            for(Method method : clazz.methods)
                put(method.name, method);
    }

    private void put(String key, Method value) {
        if(map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            List<Method> methods = new ArrayList<>();
            methods.add(value);
            map.put(key, methods);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, List<Method>> entry : map.entrySet()) {
            stringBuilder(sb, entry.getKey().toString(), "\n");
        }
        return sb.toString();
    }

    private static StringBuilder stringBuilder(StringBuilder sb, String... s) {
        Arrays.stream(s).forEach(x -> sb.append(x) );
        return sb;
    }
}
