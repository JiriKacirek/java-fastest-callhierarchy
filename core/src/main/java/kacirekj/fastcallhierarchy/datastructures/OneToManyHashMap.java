package kacirekj.fastcallhierarchy.datastructures;

import java.util.*;

public abstract class OneToManyHashMap<Key, Value, Collect extends Collection<Value>> {
    protected Map<Key, Collect> map = new HashMap<>();
    protected StringBuilder sb = new StringBuilder();

    public Map<Key, Collect> getMap() {
        return map;
    }

    protected void putEntry(Key key, Value value) {

        if(map.containsKey(key)) {
            Collection<Value> values = map.get(key);
            values.add(value);
        } else {
            Collect c = createCollectionInstance();
            c.add(value);
            map.put(key,c);
        }
    }

    protected abstract Collect createCollectionInstance();

    @Override
    public String toString() {
        sb.setLength(0); // flush

        for (Map.Entry<Key, Collect> entry : map.entrySet()) {
            sb.append(entry.getKey().toString()).append("\n");

            for (Object value : entry.getValue())
                sb.append("  ").append(value.toString()).append("\n");
        }
        return sb.toString();
    }
}