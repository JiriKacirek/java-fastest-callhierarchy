package kacirekj.fastcallhierarchy.datastructures;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class Node<Value> {

    protected @Getter
    Value value;
    protected @EqualsAndHashCode.Exclude
    @Getter
    Node<Value> parrentNode; // Null means this is root
    protected @EqualsAndHashCode.Exclude
    @Getter
    Set<Node<Value>> childNodes = new HashSet<>();
    protected Integer layer; // null means not loaded yet

    public Node(Value value) {
        this(value, null);
    }

    public Node(Value value, Node<Value> parrentNode) {
        this.value = value;
        this.parrentNode = parrentNode;
    }

    public int getLayer() {
        if (layer == null) {
            Node<Value> currentNode = this;
            layer = 0;
            while (currentNode.parrentNode != null) {
                currentNode = currentNode.parrentNode;
                layer++;
            }
        }
        return layer;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
