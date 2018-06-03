package kacirekj.fastcallhierarchy;

import kacirekj.fastcallhierarchy.parsedbytecode.ParsedClassesContainer;
import kacirekj.fastcallhierarchy.parsedbytecode.clazz.Method;
import kacirekj.fastcallhierarchy.datastructures.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// This class is specialization of AnalysisUtils and it provide extra methods for generating complete call hierarchies
public class HierarchyGen extends AnalysisUtils {
    public HierarchyGen(ParsedClassesContainer parsedClassesContainer) {
        super(parsedClassesContainer);
    }

    /**
     * @param callee method
     * @return Root node of method call hierarchy
     */
    public Node<Method> findCallHierarchy(Method callee) {
        Node<Method> rootNode = new Node<>(callee);
        findCallersRecursively(rootNode);
        return rootNode;
    }

    private void findCallersRecursively(Node<Method> calleNode) {
        Set<Method> callers = findUsages( calleNode.getValue() );
        for (Method caller : callers) {
            Node<Method> newCallerNode = new Node<Method>(caller, calleNode);
            calleNode.getChildNodes().add(newCallerNode);
            if(!isMethodAlreadyCalledInCurrentPath(newCallerNode)) {
                findCallersRecursively(newCallerNode); // RECURSION
            }
        }
    }

    /**
     * Find if method have been already called in Current-Node-to-Root-Node path. If yes, it will lead into
     * infinite recursion which has to be avoided.
     */
    private boolean isMethodAlreadyCalledInCurrentPath(Node<Method> calleNode) {
        List<Node> nodes = new ArrayList<>();
        getAllNodesToRootRecursively(calleNode, nodes);
        return nodes.contains(calleNode);
    }

    private void getAllNodesToRootRecursively(Node node, List<Node> nodes) {
        if( node.getParrentNode() == null )
            return;

        nodes.add(node.getParrentNode());
        getAllNodesToRootRecursively(node.getParrentNode(), nodes);
    }


}
