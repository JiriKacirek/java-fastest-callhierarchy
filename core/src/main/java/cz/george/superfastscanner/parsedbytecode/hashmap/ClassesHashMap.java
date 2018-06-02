package cz.george.superfastscanner.parsedbytecode.hashmap;

import cz.george.superfastscanner.utils.map.OneToOneHashMap;
import cz.george.superfastscanner.parsedbytecode.clazz.Clazz;

import java.util.Set;

/**
 * Class-oriented hashmap
 */
public class ClassesHashMap extends OneToOneHashMap<Clazz, Clazz> {
    public ClassesHashMap(Set<Clazz> classes) {
        for(Clazz clazz : classes)
            map.put(clazz, clazz);
    }
}
