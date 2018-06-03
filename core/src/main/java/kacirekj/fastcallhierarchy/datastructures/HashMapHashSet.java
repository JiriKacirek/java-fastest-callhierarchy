package kacirekj.fastcallhierarchy.datastructures;
import java.util.*;

/**
 * HashMap<Key, HashSet<Value> >
 */
public class HashMapHashSet<Key, Value> extends OneToManyHashMap<Key, Value, Set<Value>> {
    @Override
    protected Set<Value> createCollectionInstance() {
        return new HashSet<>();
    }
}