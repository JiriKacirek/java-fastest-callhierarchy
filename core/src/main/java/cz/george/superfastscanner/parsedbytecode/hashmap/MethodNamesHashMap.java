package cz.george.superfastscanner.parsedbytecode.hashmap;

import cz.george.superfastscanner.utils.map.ArrayListHashMap;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;

import java.util.*;

/**
 * Method-names-oriented map. Used for finding overrided methods in parrents.
 */
public class MethodNamesHashMap extends ArrayListHashMap<String, Method> {
    public MethodNamesHashMap(Set<Clazz> classes) {
        for (Clazz clazz : classes)
            for (Method method : clazz.getMethods())
                putEntry(method.getName(), method);
    }
}
