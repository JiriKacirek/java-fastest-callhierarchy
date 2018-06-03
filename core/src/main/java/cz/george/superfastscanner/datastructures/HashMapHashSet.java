package cz.george.superfastscanner.datastructures;
import java.util.*;

/**
 * HashMap<Key, HashSet<Value> >
 */
public class HashMapHashSet<Key, Value> extends OneToManyHashMap<Key, Value, Set<Value>> {
    @Override
    protected Set createCollectionInstance() {
        return new HashSet<>();
    }
}