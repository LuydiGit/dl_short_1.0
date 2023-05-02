package inter;

import java.util.LinkedList;

public abstract class Node {
    private LinkedList<Node> children = 
            new LinkedList<Node>();

        protected void addChild (Node n) {
            children.add(n);
        }

        protected LinkedList<Node> children(){
            return children;
        }
            
}
