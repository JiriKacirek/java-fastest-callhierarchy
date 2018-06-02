package cz.george.superfastscanner.hierarchytree;

import cz.george.superfastscanner.AnalysisUtils;
import cz.george.superfastscanner.parsedbytecode.HashMaps;
import cz.george.superfastscanner.parsedbytecode.clazz.Method;

import java.util.HashSet;
import java.util.Set;

public class CallerNode {
    private Method callee; // current node Method
    private Set<CallerNode> callers = new HashSet<>();

    private CallerNode parrent; // if null then this node is Root

    private AnalysisUtils analysisUtils;

    private int layer = 0;
    private int MAX_LAYER = Integer.MAX_VALUE;

    private HashMaps hashMaps;

    public CallerNode(Method callee, HashMaps map) {
        init(callee, null, map, 0);
    }

    private CallerNode(Method callee, CallerNode parrent, HashMaps map, int layer) {
        init(callee, parrent, map, layer);
    }

    private void init(Method callee, CallerNode parrent, HashMaps hashMaps, int layer) {
        this.analysisUtils = new AnalysisUtils(hashMaps);
        this.callee = callee;
        this.hashMaps = hashMaps;
        this.layer = layer;
        this.parrent = parrent;

        printNode();
        if (layer < MAX_LAYER)
            this.callers = findCallersRecursively();
    }

    Set<CallerNode> findCallersRecursively() {
        Set<Method> callers = analysisUtils.findUsages(callee);
        if (callers != null) {
            for (Method caller : callers) {
                if (!isMethodAlreadyCalled(caller))
                    this.callers.add(new CallerNode(caller, this, hashMaps, layer + 1));  // RECURSION
            }
        }
        return this.callers;
    }

    /**
     * Find if method have been already called in Current-Node-to-Root-Node path. If yes, it will lead into
     * infinite recursion which has to be avoided.
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
