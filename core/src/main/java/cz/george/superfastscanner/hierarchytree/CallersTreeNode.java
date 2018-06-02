package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.HashMaps;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;

import java.util.HashSet;
import java.util.Set;

public class CallersTreeNode {
    Method callee; // current node Method
    Set<CallersTreeNode> callers = new HashSet<>();

    CallersTreeNode parrent; // if null then this node is Root

    int layer = 0;
    int MAX_LAYER = Integer.MAX_VALUE;

    HashMaps hashMaps;

    public CallersTreeNode(Method callee, HashMaps map) {
        init(callee, null, map, 0);
    }

    private CallersTreeNode(Method callee, CallersTreeNode parrent, HashMaps map, int layer) {
        init(callee, parrent, map, layer);
    }

    private void init(Method callee, CallersTreeNode parrent, HashMaps hashMaps, int layer) {
        this.callee = callee;
        this.hashMaps = hashMaps;
        this.layer = layer;
        this.parrent = parrent;

        printNode();
        if (layer < MAX_LAYER)
            this.callers = findCallersRecursively();
    }

    Set<CallersTreeNode> findCallersRecursively() {
        Set<Method> callers = AnalysisUtils.findUsages(callee, hashMaps);
        if (callers != null) {
            for (Method caller : callers) {
                if (!isMethodAlreadyCalled(caller))
                    this.callers.add(new CallersTreeNode(caller, this, hashMaps, layer + 1));  // RECURSION
            }
        }
        return this.callers;
    }

    /**
     * Find if method have been yet called in Current-Node-to-Root-Node path. If yes, it will lead into
     * infinite recursion which have to be avoided.
     */
    boolean isMethodAlreadyCalled(Method method) {
        // Walk right into the root-1
        if (this.parrent != null) {
            boolean isNullYet = this.parrent.isMethodAlreadyCalled(method);
            if (this.parrent.callee.equals(method) || isNullYet) {
                return true;
            }
        }
        return false;
    }

    protected void printNode() {
        String slash = "";
        for (int i = 0; i < layer; i++) {
                slash+=i + " ";
        }
       System.out.println(slash + callee.toString());
    }

    @Override
    public int hashCode() {
        return this.callee.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.callee.equals(obj);
    }
}
