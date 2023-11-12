import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int maxDistance = 0;

        for (String currentNoun : nouns) {
            int currentDistance = 0;
            for (String otherNoun : nouns) {
                currentDistance += wordNet.distance(currentNoun, otherNoun);
            }

            if (currentDistance > maxDistance) {
                maxDistance = currentDistance;
                outcast = currentNoun;
            }
        }

        return outcast;
    }

    // test client
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
