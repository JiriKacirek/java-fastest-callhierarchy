package kacirekj.fastcallhierarchy;

import kacirekj.fastcallhierarchy.datastructures.Node;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;

public abstract class AnalysisUtilsHandler {
    public abstract void onNewFindUsage(Node<Method> newCallerNode);
    public abstract boolean shouldContinue(Node<Method> newCallerNode);


}
