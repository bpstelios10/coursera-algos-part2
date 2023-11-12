import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SAP {

    private final Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("arguments to SAP constructor cant be null");

        graph = cloneGraph(G);
    }

    // length of the shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        return getCommonAnchestor(List.of(v), List.of(w)).getDistance();
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        return getCommonAnchestor(List.of(v), List.of(w)).getVertex();
    }

    // length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("arguments to length method cant be null");

        return getCommonAnchestor(v, w).getDistance();
    }

    // a common ancestor that participates in the shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException("arguments to ancestor method cant be null");

        return getCommonAnchestor(v, w).getVertex();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private Digraph cloneGraph(Digraph G) {
        Digraph copy = new Digraph(G.V());
        for (int i = 0; i < G.V(); i++) {
            for (int j : G.adj(i))
                copy.addEdge(i, j);
        }

        return copy;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= graph.V())
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (graph.V() - 1));
    }

    private CommonAnchestor getCommonAnchestor(Iterable<Integer> vGroup, Iterable<Integer> wGroup) {
        CommonAnchestor commonAnchestor = new CommonAnchestor(-1, Integer.MAX_VALUE);

        Queue<Integer> q = new Queue<>();
        Set<Integer> vSet = new HashSet<>();
        boolean[] markedFirstNoun = new boolean[graph.V()];
        int[] distToFromFirstNoun = new int[graph.V()];

        for (Integer v : vGroup) {
            if (v == null)
                throw new IllegalArgumentException("argument of iterable cant be null");
            validateVertex(v);

            vSet.add(v);
            markedFirstNoun[v] = true;
            distToFromFirstNoun[v] = 0;
            q.enqueue(v);
        }

        while (!q.isEmpty()) {
            int i = q.dequeue();
            for (int j : graph.adj(i)) {
                if (!markedFirstNoun[j]) {
                    distToFromFirstNoun[j] = distToFromFirstNoun[i] + 1;
                    markedFirstNoun[j] = true;
                    q.enqueue(j);
                }
            }
        }

        Set<Integer> wSet = new HashSet<>();
        boolean[] markedSecondNoun = new boolean[graph.V()];
        int[] distToFromSecondNoun = new int[graph.V()];

        for (Integer w : wGroup) {
            if (w == null)
                throw new IllegalArgumentException("argument of iterable cant be null");
            validateVertex(w);

            wSet.add(w);
            markedSecondNoun[w] = true;
            distToFromSecondNoun[w] = 0;
            q.enqueue(w);
        }

        while (!q.isEmpty()) {
            int i = q.dequeue();
            for (int j : graph.adj(i)) {
                if (!markedSecondNoun[j]) {
                    distToFromSecondNoun[j] = distToFromSecondNoun[i] + 1;
                    markedSecondNoun[j] = true;
                    q.enqueue(j);
                }
            }
        }

        for (int i = 0; i < graph.V(); i++) {
//            StdOut.println("for i: " + i + ", dist from first is: " + distToFromFirstNoun[i] + " and dist from second is: " + distToFromSecondNoun[i]);
            if ((!vSet.contains(i) && distToFromFirstNoun[i] == 0) || (!wSet.contains(i) && distToFromSecondNoun[i] == 0))
                continue;

            int currentDistance = distToFromFirstNoun[i] + distToFromSecondNoun[i];
            if (currentDistance < commonAnchestor.getDistance())
                commonAnchestor = new CommonAnchestor(i, currentDistance);
        }

        return commonAnchestor.getVertex() == -1 ? new CommonAnchestor(-1, -1) : commonAnchestor;
    }

    private static class CommonAnchestor {
        private final int distance;
        private final int vertex;

        CommonAnchestor(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        int getVertex() {
            return vertex;
        }

        int getDistance() {
            return distance;
        }
    }
}
