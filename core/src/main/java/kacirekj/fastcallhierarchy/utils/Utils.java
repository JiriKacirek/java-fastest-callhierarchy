package kacirekj.fastcallhierarchy.utils;

import kacirekj.fastcallhierarchy.datastructures.Node;

public class Utils {

    public static void consolePrintNode(Node rootNode) {
        consolePrintNodeRecursively(rootNode, 0);
    }
    private static void consolePrintNodeRecursively(Node node, int layer) {
        System.out.println(spacing(layer).append( node.toString() ));
        for(Object childNode : node.getChildNodes()) {
            Node n = (Node)childNode;
            consolePrintNodeRecursively(n, layer + 1); // RECURSION
        }
    }

    private static StringBuilder spacing(int num) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < num; i++) {
            sb.append(i).append(" ");
        }
        return sb;
    }
}
