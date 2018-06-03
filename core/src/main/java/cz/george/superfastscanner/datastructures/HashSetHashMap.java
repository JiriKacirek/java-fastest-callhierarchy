package cz.george.superfastscanner.datastructures;

        import java.util.*;

/**
 * Created by John on 4/14/2017.
 */
public class HashSetHashMap<Key, Value> extends OneToManyHashMap<Key, Value, Set<Value>> {
    @Override
    protected Set createCollectionInstance() {
        return new HashSet<>();
    }
}