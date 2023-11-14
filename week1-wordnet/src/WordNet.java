import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {

    private final HashMap<Integer, String> synsets = new HashMap<>();
    private final HashMap<String, HashSet<Integer>> words = new HashMap<>();
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments to WordNet constructor cant be null");

        populateSynsetsAndWords(new In(synsets));
        Digraph graph = populateGraph(new In(hypernyms));
        validateGraphIsRootedDAG(graph);

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return words.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("arguments to isNoun method cant be null");

        return words.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("arguments to distance method must be words");

        return sap.length(words.get(nounA), words.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in the shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException("arguments to sap method must be words");

        return synsets.get(sap.ancestor(words.get(nounA), words.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        // run from command line
        WordNet wordNet = new WordNet(args[0], args[1]);
//        assertThat(wordNet.distance("hunchback", "accessory_fruit") == 11);

        for (String word : wordNet.nouns()) StdOut.println(word);

        assertThat(wordNet.isNoun("a"));
        assertThat(!wordNet.isNoun("nope"));

        assertThat(wordNet.distance("a", "c") == 2);
        assertThat(wordNet.distance("b", "c") == 1);
        assertThat(wordNet.distance("a", "a") == 0);

        assertThat("c".equals(wordNet.sap("a", "c")));
        assertThat("c".equals(wordNet.sap("b", "c")));
        assertThat("a".equals(wordNet.sap("a", "a")));
        assertThat("b".equals(wordNet.sap("a", "b")));
    }

    private void populateSynsetsAndWords(In in) {
        String[] nextLine;
        while (!in.isEmpty()) {
            nextLine = in.readLine().split(",");
            int synsetIndex = Integer.parseInt(nextLine[0]);
            String[] nouns = nextLine[1].split(" ");

            for (String noun : nouns) {
                synsets.put(synsetIndex, nextLine[1]);
                words.computeIfAbsent(noun, k -> new HashSet<>());
                words.get(noun).add(synsetIndex);
            }
        }
    }

    private Digraph populateGraph(In in) {
        Digraph graph = new Digraph(this.synsets.size());
        String[] hypernyms;

        while (!in.isEmpty()) {
            hypernyms = in.readString().split(",");

            for (int i = 1; i < hypernyms.length; i++) {
                graph.addEdge(Integer.parseInt(hypernyms[0]), Integer.parseInt(hypernyms[i]));
            }
        }

        return graph;
    }

    private void validateGraphIsRootedDAG(Digraph graph) {
        Topological topological = new Topological(graph);
        if (!topological.hasOrder())
            throw new IllegalArgumentException("the data do not correspond to a rooted DAG");

        int cc = 0;
        for (int v = 0; v < graph.V(); v++) {
            if (graph.outdegree(v) == 0)
                cc++;
        }
        if (cc > 1)
            throw new IllegalArgumentException("the data do not correspond to a rooted DAG");
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}
