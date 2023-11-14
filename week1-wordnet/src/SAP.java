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

        CustomBFS bfsFromNoun1 = new CustomBFS(graph, vGroup);
        CustomBFS bfsFromNoun2 = new CustomBFS(graph, wGroup);

        for (int i = 0; i < graph.V(); i++) {
            if ((!bfsFromNoun1.isVertexVisited(i) && bfsFromNoun1.getDistToFrom(i) == 0)
                    || (!bfsFromNoun2.isVertexVisited(i) && bfsFromNoun2.getDistToFrom(i) == 0))
                continue;

            int currentDistance = bfsFromNoun1.getDistToFrom(i) + bfsFromNoun2.getDistToFrom(i);
            if (currentDistance < commonAnchestor.getDistance())
                commonAnchestor = new CommonAnchestor(i, currentDistance);
        }

        return commonAnchestor.getVertex() == -1 ? new CommonAnchestor(-1, -1) : commonAnchestor;
    }

    private static class CustomBFS {
        private final Set<Integer> visitedVertexes;
        private final int[] distToFrom;

        CustomBFS(Digraph graph, Iterable<Integer> nounsGroup) {
            Queue<Integer> q = new Queue<>();
            visitedVertexes = new HashSet<>();
            boolean[] markedFirstNoun = new boolean[graph.V()];
            distToFrom = new int[graph.V()];

            for (Integer v : nounsGroup) {
                if (v == null)
                    throw new IllegalArgumentException("argument of iterable cant be null");
                validateVertex(graph, v);

                visitedVertexes.add(v);
                markedFirstNoun[v] = true;
                distToFrom[v] = 0;
                q.enqueue(v);
            }

            while (!q.isEmpty()) {
                int i = q.dequeue();
                for (int j : graph.adj(i)) {
                    if (!markedFirstNoun[j]) {
                        distToFrom[j] = distToFrom[i] + 1;
                        markedFirstNoun[j] = true;
                        q.enqueue(j);
                    }
                }
            }
        }

        boolean isVertexVisited(int i) {
            return visitedVertexes.contains(i);
        }

        int getDistToFrom(int i) {
            return distToFrom[i];
        }

        private void validateVertex(Digraph graph, int v) {
            if (v < 0 || v >= graph.V())
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (graph.V() - 1));
        }
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
