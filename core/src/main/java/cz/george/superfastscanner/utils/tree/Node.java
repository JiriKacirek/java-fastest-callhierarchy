package cz.george.superfastscanner.utils.tree;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by John on 4/14/2017.
 */
@EqualsAndHashCode
public class Node<Value> {

    protected @Getter Value value;
    protected @EqualsAndHashCode.Exclude @Getter Node<Value> parrentNode;
    protected @EqualsAndHashCode.Exclude @Getter Set<Node<Value>> childNodes = new HashSet<>();

    public Node(Value value) {
        this.value = value;
    }

    public Node(Value value, Node<Value> parrentNode) {
        this.value = value;
        this.parrentNode = parrentNode;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
