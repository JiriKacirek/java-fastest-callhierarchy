package cz.george.superfastscanner.bytecode.clazz;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by John on 4/8/2017.
 */
public class Clazz {
    public String name = "";
    public String superName = "";
    public String[] interfaces = new String[0];
    public Set<Method> methods = new HashSet<Method>();

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

    protected static StringBuilder append(StringBuilder sb, String... strings) {
        Arrays.stream(strings).forEach(str -> sb.append( str != null ? str : "null" ));
        return sb;
    }


}
