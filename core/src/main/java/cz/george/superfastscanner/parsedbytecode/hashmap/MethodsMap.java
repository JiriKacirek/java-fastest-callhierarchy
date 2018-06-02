package cz.george.superfastscanner.parsedbytecode.hashmap;

import cz.george.superfastscanner.utils.map.OneToOneHashMap;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;

import java.util.*;

/**
 * Methods-oriented hashmap.
 */
public class MethodsMap extends OneToOneHashMap<Method,Method> {
    public MethodsMap(Set<Clazz> classes) {
        for (Clazz clazz : classes)
            for (Method method : clazz.getMethods())
                map.put(method, method);
    }
}
