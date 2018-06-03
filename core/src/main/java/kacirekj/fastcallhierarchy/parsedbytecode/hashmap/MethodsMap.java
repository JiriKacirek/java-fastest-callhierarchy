package kacirekj.fastcallhierarchy.parsedbytecode.hashmap;

import kacirekj.fastcallhierarchy.datastructures.OneToOneHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;

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
