package cz.george.superfastscanner.hierarchytree.hashmap;

import cz.george.superfastscanner.bytecode.clazz.Method;
import cz.george.superfastscanner.hierarchytree.InheritanceTreeNode;

import java.util.*;

/**
 * Map for Inheritance hierarchytree tree.
 */
public class InheritanceHierarchyMap {
    public Map<String, Set<Method>> map = new HashMap<>();
    StringBuilder sb = new StringBuilder();

    public InheritanceHierarchyMap(InheritanceTreeNode interfacesTree) {
        initializeMap(interfacesTree);
    }

    private void initializeMap(InheritanceTreeNode interfacesTree) {
        walkThroughTheTreeRecursively(interfacesTree);
    }

    public void walkThroughTheTreeRecursively(InheritanceTreeNode interfacesTree) {
        // for interfaces
        for(InheritanceTreeNode childNode : interfacesTree.interfaces) {
            walkThroughTheTreeRecursively(childNode);
        }
        fullfillMap(interfacesTree.clazz.methods);

        // for super classes
        InheritanceTreeNode childNode = interfacesTree.superClass;
        if(childNode != null)
            walkThroughTheTreeRecursively(childNode);

        fullfillMap(interfacesTree.clazz.methods);
    }

    public void fullfillMap(Set<Method> methods) {
        for(Method method : methods) {
            sb.setLength(0); // clear
            put(sb.append(method.name).append(" ").append(method.description).toString() , method);
        }
    }

    private void put(String key, Method value) {
        if(map.containsKey(key)) {
            map.get(key).add(value);
        } else {
            Set<Method> methods = new HashSet<>();
            methods.add(value);
            map.put(key, methods);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, Set<Method>> entry : map.entrySet()) {
            stringBuilder(sb, entry.getKey().toString(), "\n");
            for(Method m : entry.getValue()) {
                stringBuilder(sb, "  ", m.toString(), "\n");
            }
        }
        return sb.toString();
    }

    private static StringBuilder stringBuilder(StringBuilder sb, String... s) {
        Arrays.stream(s).forEach(x -> sb.append(x) );
        return sb;
    }
}
