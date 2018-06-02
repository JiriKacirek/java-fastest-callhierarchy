package cz.george.superfastscanner.hierarchytree.hashmap;

import cz.george.superfastscanner.parsedbytecode.clazz.Method;
import cz.george.superfastscanner.utils.map.HashSetHashMap;
import cz.george.superfastscanner.hierarchytree.ClassInheritanceNode;

import java.util.*;

/**
 * Hashmap for Inheritance hierarchytree tree.
 */
public class InheritanceHierarchyHashMap extends HashSetHashMap<String, Method> {

    public InheritanceHierarchyHashMap(ClassInheritanceNode interfacesTree) {
        initializeMap(interfacesTree);
    }

    private void initializeMap(ClassInheritanceNode interfacesTree) {
        walkThroughTheTreeRecursively(interfacesTree);
    }

    private void walkThroughTheTreeRecursively(ClassInheritanceNode interfacesTree) {
        // for interfaces
        for (ClassInheritanceNode childNode : interfacesTree.interfaces) {
            walkThroughTheTreeRecursively(childNode);
        }
        fullfillMap(interfacesTree.getNodeValue().methods);

        // for super classes
        ClassInheritanceNode childNode = interfacesTree.superClass;
        if (childNode != null)
            walkThroughTheTreeRecursively(childNode);

        fullfillMap(interfacesTree.getNodeValue().methods);
    }

    private void fullfillMap(Set<Method> methods) {
        for (Method method : methods) {
            sb.setLength(0); // clear
            String key = sb.append(method.name).append(" ").append(method.description).toString();
            putEntry(key, method);
        }
    }
}
