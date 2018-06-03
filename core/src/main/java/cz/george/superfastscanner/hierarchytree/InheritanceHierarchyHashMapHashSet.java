package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.parsedbytecode.clazz.Method;
import cz.george.superfastscanner.datastructures.HashMapHashSet;

import java.util.*;

/**
 * Hashmap for Inheritance hierarchytree tree.
 */
public class InheritanceHierarchyHashMapHashSet extends HashMapHashSet<String, Method> {

    public InheritanceHierarchyHashMapHashSet(ClassInheritanceNode interfacesTree) {
        initializeMap(interfacesTree);
    }

    private void initializeMap(ClassInheritanceNode interfacesTree) {
        walkThroughTheTreeRecursively(interfacesTree);
    }

    private void walkThroughTheTreeRecursively(ClassInheritanceNode interfacesTree) {
        // for interfaces
        for (ClassInheritanceNode childNode : interfacesTree.getInterfaces()) {
            walkThroughTheTreeRecursively(childNode);
        }
        fullfillMap(interfacesTree.getValue().getMethods());

        // for super classes
        ClassInheritanceNode childNode = interfacesTree.getSuperClass();
        if (childNode != null)
            walkThroughTheTreeRecursively(childNode);

        fullfillMap(interfacesTree.getValue().getMethods());
    }

    private void fullfillMap(Set<Method> methods) {
        for (Method method : methods) {
            sb.setLength(0); // clear
            String key = sb.append(method.getName()).append(" ").append(method.getDescription()).toString();
            putEntry(key, method);
        }
    }
}
