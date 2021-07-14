package puzzles.test;

import puzzles.solver.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.util.Objects.hash;

class TestNode extends Node {

    public int a, b, c;

    TestNode(int a, int b, int c, Node parent) {
        super(parent);

        this.a = a;
        this.b = b;
        this.c = c;
    }

    // Helper Method
    private static int randInt(int min, int max) {

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    // Goal is Ascending Order of A B C
    @Override
    public boolean isGoal() {
        return a <= b && b <= c;
    }

    @Override
    public List<Node> generateChildren() {
        List<Node> children = new LinkedList<>();

        for (int i = 0; i < 2; i++)
        {
            children.add(new TestNode(randInt(1, 9), randInt(1, 9), randInt(1, 9), this));
        }

        return children;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TestNode)
        {
            return ((TestNode) obj).a == a && ((TestNode) obj).b == b && ((TestNode) obj).c == c;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hash(a, b, c); // Awful, I Know
    }

    @Override
    public String toString() {
        return "Node(" + a + " " + b + " " + c + ")";
    }
}