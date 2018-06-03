package kacirekj.fastcallhierarchy.parsedbytecode.hashmap;

import kacirekj.fastcallhierarchy.datastructures.OneToOneHashMap;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Clazz;

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
