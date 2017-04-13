package cz.george.superfastscanner.bytecode.clazz;

/**
 * Created by John on 4/8/2017.
 */
public class Instruction {
    public Method ownerMethod;

    public String name;
    public String description;
    public String owner; // is the Class where's the method have been created

    public Instruction(String name, String description, String owner, Method ownerMethod) {
        this.name = name; this.description = description; this.owner = owner; this.ownerMethod = ownerMethod;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Instruction && ((Instruction)obj).toString().equals(this.toString());
    }

    @Override
    public String toString() {
        return owner + " " + name + " " + description;
    }
}