package cz.george.superfastscanner;

import cz.george.superfastscanner.bytecode.clazz.Clazz;
import cz.george.superfastscanner.bytecode.clazz.Method;
import cz.george.superfastscanner.bytecode.speedmap.ClassesMap;
import cz.george.superfastscanner.bytecode.speedmap.InstructionsMap;
import cz.george.superfastscanner.hierarchytree.CallersTreeNode;
import cz.george.superfastscanner.bytecode.speedmap.MethodsMap;

import java.util.Set;

/**
 * Created by John on 4/8/2017.
 */
public class StaticAnalysisTool {
    // The ultimate Memory-complexity performance solution
    public MethodsMap methodsMap;
    public ClassesMap classesMap;
    public InstructionsMap instructionsMap;

    StaticAnalysisTool(Set<Clazz> classes) {
        methodsMap = new MethodsMap(classes);
        classesMap = new ClassesMap(classes);
        instructionsMap = new InstructionsMap(classes);
    }

    CallersTreeNode getCallers(Method method) {
        return new CallersTreeNode(method, this);
    }


}
