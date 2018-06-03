package kacirekj.fastcallhierarchy.parsedbytecode.clazz;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Clazz {

    private @Getter String name;
    private @Getter String superName = "";
    private @Getter String[] interfaces = new String[0];
    private @Getter Set<Method> methods = new HashSet<>();

    public Clazz(String name, String superName, String[] interfaces) {
        this.name = name; this.superName = superName; this.interfaces = interfaces;
    }

    @Override
    public int hashCode() {
        return name.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Clazz && ((Clazz)obj).name.equals(name);
    }

    @Override
    public String toString() {
        return name != null ? name : "null";
    }
}
