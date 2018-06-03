package kacirekj.fastcallhierarchy.parsedbytecode.hashmap;

import kacirekj.fastcallhierarchy.datastructures.ArrayListHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;

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
