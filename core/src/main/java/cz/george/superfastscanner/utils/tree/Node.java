package cz.george.superfastscanner.utils.tree;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by John on 4/14/2017.
 */
public class Node<Value> {

    private Value nodeValue;
    private final Set<Node<Value>> childNodes = new HashSet<>();

    public Node(Value value) {
        nodeValue = value;
    }

    public Set<Node<Value>> walk() {
        return walk(new HashSet<Node<Value>>());
    }

    private Set<Node<Value>> walk(Set<Node<Value>> collectedNodes) {
        for(Node child : childNodes) {
            child.walk(collectedNodes); // RECURSION
        }
        collectedNodes.add(this);
        return collectedNodes;
    }

    public Value getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(Value nodeValue) {
        this.nodeValue = nodeValue;
    }

    public Set<Node<Value>> getChildNodes() {
        return childNodes;
    }
}
