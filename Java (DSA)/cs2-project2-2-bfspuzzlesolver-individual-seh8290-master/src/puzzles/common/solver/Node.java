package puzzles.common.solver;

import java.util.*;

public abstract class Node {

    private final Node parent;
    private LinkedList<Node> adjacent;

    public Node(Node parent) {
        this.adjacent = new LinkedList<>();
        this.parent = parent;
    }

    public abstract boolean isGoal();

    public abstract String toString();

    public abstract List<Node> generateChildren();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public void printGraph(boolean weird) {
        StringBuilder buffer = new StringBuilder(50);
        if (weird)
        {
            weirdPrint(buffer, "", "");
        } else
        {
            print(buffer, "", "");
        }
        System.out.println(buffer.toString());
    }

    public void weirdPrint(StringBuilder buffer, String prefix, String childrenPrefix) {

        buffer.append(prefix);

        Node current = this;
        LinkedList<Node> path = new LinkedList<>();

        // Go Through The Path From Goal -> Root
        while (current.getParent() != null)
        {
            path.add(current);
            current = current.getParent();
        }

        path.add(current);

        Collections.reverse(path);
        System.out.println(path);

    }

    // Fancy Printing Boy, Reused from Another Project, Just Works
    public void print(StringBuilder buffer, String prefix, String childrenPrefix) {

        buffer.append(prefix);
        buffer.append(this.toString());
        buffer.append('\n');

        for (Iterator<Node> it = getAdjacent().iterator(); it.hasNext(); )
        {
            Node next = it.next();
            if (it.hasNext())
            {
                next.print(buffer, childrenPrefix + "├ ", childrenPrefix + "│ ");
            } else
            {
                next.print(buffer, childrenPrefix + "└ ", childrenPrefix + "   ");
            }
        }
    }

    public Node getParent() {
        return parent;
    }

    public LinkedList<Node> getAdjacent() {
        return adjacent;
    }

    public void addAdjacent(Collection<Node> nodes) {
        adjacent.addAll(nodes);
    }

}