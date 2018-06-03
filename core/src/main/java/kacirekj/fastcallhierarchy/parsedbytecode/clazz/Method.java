package kacirekj.fastcallhierarchy.parsedbytecode.clazz;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Method {
    private @Getter Clazz ownerClass;   // path/to/some/Clazz
    private @Getter String name;        // someMethodName
    private @Getter String description; // ()V
    private @Getter Set<Instruction> instructions = new HashSet<>(); // Methods used in Method body are called Instructions

    /**
     * @param signature for example for method returning void and taking no arguments:
     *                  path/to/some/Clazz someMethodName ()V
     */
    public Method(String signature) {
        String[] signatures = signature.split(" ");
        if(signatures.length != 3)
            throw new IllegalArgumentException("Invalid Method signature: " + signature);

        init(signatures[1], signatures[2], new Clazz(signatures[0], null, null) );
    }

    public Method(String name, String description, Clazz owner) {
        init(name, description, owner);
    }

    private void init(String name, String description, Clazz owner) {
        this.name = name;
        this.description = description;
        this.ownerClass = owner;
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
        return  ownerClass.getName() + " " + name + " " + description;
    }
}