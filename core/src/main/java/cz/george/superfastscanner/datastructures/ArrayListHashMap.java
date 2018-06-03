package cz.george.superfastscanner.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 4/14/2017.
 */
public class ArrayListHashMap<Key, Value> extends OneToManyHashMap<Key, Value, List<Value>> {
    @Override
    protected List createCollectionInstance() {
        return new ArrayList<>();
    }
}
