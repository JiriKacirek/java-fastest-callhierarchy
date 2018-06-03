package cz.george.superfastscanner.datastructures;

import java.util.ArrayList;
import java.util.List;

public class ArrayListHashMap<Key, Value> extends OneToManyHashMap<Key, Value, List<Value>> {
    @Override
    protected List createCollectionInstance() {
        return new ArrayList<>();
    }
}
