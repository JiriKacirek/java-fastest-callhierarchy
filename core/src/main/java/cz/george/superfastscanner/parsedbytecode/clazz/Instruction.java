package cz.george.superfastscanner.parsedbytecode.clazz;

import lombok.Getter;

public class Instruction {
    private @Getter Method ownerMethod;

    private @Getter String name;
    private @Getter String description;
    private @Getter String owner; // is the Class where's the method have been created

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