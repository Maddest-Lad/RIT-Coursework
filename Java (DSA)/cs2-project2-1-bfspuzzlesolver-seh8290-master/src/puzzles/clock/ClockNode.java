package puzzles.clock;

import puzzles.solver.Node;

import java.util.List;

import static java.util.Objects.hash;

public class ClockNode extends Node {

    // Problem Parameters
    public static int hoursCap;
    public static int endTime;

    // Where We Are
    public int currentTime;

    public ClockNode(int currentTime, Node parent) {
        super(parent);
        this.currentTime = currentTime;
    }

    @Override
    public boolean isGoal() {
        return currentTime == endTime;
    }

    @Override
    public List<Node> generateChildren() {
        if (currentTime == hoursCap) // Roll Over From HoursCap -> 0
        {
            return List.of(new ClockNode(1, this), new ClockNode(hoursCap - 1, this));
        } else if (currentTime == 1)  // Roll Over From 0 -> HoursCap
        {
            return List.of(new ClockNode(hoursCap, this), new ClockNode(currentTime + 1, this));
        } else
        {
            return List.of(new ClockNode(currentTime + 1, this), new ClockNode(currentTime - 1, this));
        }
    }

    @Override
    public String toString() {
        return "Node(" + currentTime + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClockNode)
        {
            return ((ClockNode) obj).currentTime == currentTime;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hash(currentTime);
    }

}
