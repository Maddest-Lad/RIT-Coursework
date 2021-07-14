package edu.rit.cs.labgraph;

import java.util.List;

/**
 * A class representing graph edges that are labeled with flow values.
 * This class was custom designed for the Max Flow problem.
 *
 * @author Sam Harris
 */
public class Edge {

    /**
     * How much traffic, or flow, this edge can handle,
     * in either direction (but not in both)
     */
    private final long capacity;

    /**
     * The name of the first node.
     * When flow is assigned, it is positive if the flow comes
     * <em>from</em> this node.
     */
    private final String in;

    /**
     * The name of the second node.
     * When flow is assigned, it is positive if the flow goes
     * <em>toward</em> this node.
     */
    private final String out;

    /**
     * The flow in the direction from in to out.
     * Negative if the flow is in the opposite direction.
     */
    private long flow;

    /**
     * Create a new edge with an initial flow of 0.
     *
     * @param in       The name of the first node.
     *                 When flow is assigned, it is positive if the flow comes from this node.
     * @param out      The name of the second node.
     *                 When flow is assigned, it is positive if the flow comes
     *                 <em>towards</em> this node.
     * @param capacity the maximum flow that can travel through this edge
     *                 in either direction.
     */
    public Edge(String in, String out, long capacity) {
        this.in = in;
        this.out = out;
        this.capacity = capacity;
        this.flow = 0;
    }

    public Edge(String[] listVersion) {
        this.in = listVersion[0];
        this.out = listVersion[1];
        this.capacity = Long.parseLong(listVersion[2]);
        this.flow = 0;
    }

    public long availableFlow(String in, String out) {
        return this.flow;
    }

    public void changeFlow(String in, String out, long delta) {

    }

    public int direction(String in, String out) {
        return -1;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public long getFlow(String in, String out) {
        return 0;
    }

    public String getInNode() {
        return "";
    }

    public String getOutNode() {
        return "";
    }

    public String getOtherEnd(String thisEnd) {
        return "";
    }

    /**
     * Indicate all the information stored in this edge.
     *
     * @return a String in the format "<code>[in==>flow/capacity==>out]</code>"
     */
    @Override
    public String toString() {
        return "[" + this.in +
                "==>" +
                this.direction(this.in, this.out) * this.flow +
                '/' +
                this.capacity +
                "==>" +
                this.out + ']';
    }

    /**
     * Indicate node names and flow stored in this edge.
     *
     * @return a String in the format "<code>[in==>flow==>out]</code>"
     */
    public String toStringNoMax() {
        return "[" + this.in +
                "==>" +
                this.direction(this.in, this.out) * this.flow +
                "==>" +
                this.out + ']';
    }
}
