package cz.george.superfastscanner.bytecode.clazz;

import java.util.HashSet;
import java.util.Set;
import static cz.george.superfastscanner.bytecode.clazz.Clazz.*;

/**
 * Created by John on 4/8/2017.
 */
public class Method {
    public Clazz ownerClass;

    public String name;
    public String description;
    public Set<Instruction> instructions = new HashSet<Instruction>();

    public Method(String name, String description, Clazz owner) {
        init(name, description, owner);
    }

    /**
     *
     * @param signature for example for method returning void a taking no arguments:  path/to/some/Clazz someMethodName ()V
     */
    public Method(String signature) {
        String[] signatures = signature.split(" ");
        if(signatures.length != 3)
            throw new IllegalArgumentException("Invalid Method signature: " + signature);

        init(signatures[1], signatures[2], new Clazz(signatures[0], null, null) );

    }

    void init(String name, String description, Clazz owner) {
        this.name = name; this.description = description; this.ownerClass = owner;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Method && ((Method)obj).toString().equals(toString());
    }

    @Override
    public String toString() {
        return append(new StringBuilder(), ownerClass.name, " ", name, " ", description).toString();
    }
}