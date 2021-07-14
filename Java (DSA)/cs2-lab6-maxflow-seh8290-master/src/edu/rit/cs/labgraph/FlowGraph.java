package edu.rit.cs.labgraph;

import java.io.*;
import java.util.*;

/**
 * A Graph representation where nodes are just strings that contain no data,
 * and edges are named by their node endpoints, their flow, and their flow
 * capacity.
 * This graph class was custom-designed for the Max Flow Problem.
 *
 * @author RIT CS
 * @author Sam Harris
 */
public class FlowGraph {

    /**
     * The starting node for the {@link #doBFS()} method
     */
    private final String source;

    /**
     * The destination node for the {@link #doBFS()} method
     */
    private final String sink;

    /**
     * The internal graph data structure: a map where each node name
     * is associated with the set of edges connected to it
     */
    private final LinkedHashMap<String, LinkedHashSet<Edge>> adjList;

    /**
     * <em>This constant is only used for testing.</em>
     */
    public static final long CAP = 4L;

    /**
     * Build a fixed graph for testing.
     * <em>This method is here only for testing.</em>
     * <pre>
     *        B
     *      / | \
     *     A  |  D
     *      \ | /
     *        C
     * </pre>
     */
    public FlowGraph() {
        final String A = "A";
        final String B = "B";
        final String C = "C";
        final String D = "D";

        Edge eAB = new Edge( A, B, CAP );
        Edge eAC = new Edge( A, C, CAP );
        Edge eBC = new Edge( B, C, CAP );
        Edge eDB = new Edge( D, B, CAP );
        Edge eDC = new Edge( D, C, CAP );

        this.source = A;
        this.sink = D;
        this.adjList = new LinkedHashMap<>();

        for ( String node: List.of( A, B, C, D ) ) {
            this.adjList.put( node, new LinkedHashSet<>() );
        }
        this.adjList.get( A ).add( eAB );
        this.adjList.get( B ).add( eAB );
        this.adjList.get( A ).add( eAC );
        this.adjList.get( C ).add( eAC );
        this.adjList.get( B ).add( eBC );
        this.adjList.get( C ).add( eBC );
        this.adjList.get( D ).add( eDB );
        this.adjList.get( B ).add( eDB );
        this.adjList.get( D ).add( eDC );
        this.adjList.get( C ).add( eDC );
    }


    public FlowGraph(String graphFileName, String source, String sink) throws IOException, GraphException {
        LinkedHashMap<String, LinkedHashSet<Edge>> adjList = new LinkedHashMap<String, LinkedHashSet<Edge>>();

        BufferedReader reader = new BufferedReader(new FileReader(graphFileName));
        String current_line;

        while ((current_line = reader.readLine()) != null)
        {
            // Extract Data From The Line
            String[] input = current_line.split(" ");
            String in = input[0];
            String out = input[1];
            long capacity = Long.parseLong(input[2]);

            // Create The LinkedHashSets Where Needed
            if (!adjList.containsKey(in))
            {
                adjList.put(in, new LinkedHashSet<Edge>());
            }

            // Create The LinkedHashSets Where Needed
            if (!adjList.containsKey(out))
            {
                adjList.put(out, new LinkedHashSet<Edge>());
            }

            // Add The Edges
            Edge temp = new Edge(in, out, capacity);
            adjList.get(in).add(temp);
            adjList.get(out).add(temp);
        }

        this.adjList = adjList;
        this.source = source;
        this.sink = sink;
    }


    /**
     * What edges are connected to this node?
     *
     * @param node the node's name
     * @return A Set of {@link Edge} objects whose in or out node equals
     * the parameter node
     * @rit.pre node is not null and is in the graph.
     */
    public Set<Edge> getEdgesAt(String node) {
        Set<Edge> edges = new HashSet<>();

        // TODO Might Be Overkill - Probably Make This Dumber
        for (String s : this.adjList.keySet())
        {
            if (s.equals(node))
            {
                edges.addAll(adjList.get(s));
            }
        }

        return edges;
    }

    public long getAvailableFlow(String in, String out) {
        return -1;
    }

    public Edge getEdge(String a, String b) {
        return new Edge(a, b, 0);
    }

    public String getSource() {
        return this.source;
    }

    public String getSink() {
        return this.sink;
    }

    // BFS
    public Optional<List<String>> doBFS() {
        return Optional.empty();
    }

    public void show(boolean b) {

    }
}
