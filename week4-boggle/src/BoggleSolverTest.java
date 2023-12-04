import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.List;
import java.util.Set;

class BoggleSolverTest {

    private static final BoggleSolverTest tester = new BoggleSolverTest();

    public static void main(String[] args) {
        tester.testConstructor();
        tester.testValidWords(args[0], args[1]);
        tester.testValidWordsWithQ(args[0], args[2]);
        tester.testScoreOf();
    }

    private void testConstructor() {
        List<String> dictionary1 = List.of("word1", "word2", "test", "emptyword");
        new BoggleSolver(dictionary1.toArray(new String[4]));
//        BoggleSolver solver1 = new BoggleSolver(dictionary1.toArray(new String[4]));
//        assertThat(solver1.dictionary.contains("word1"));
//        assertThat(solver1.dictionary.contains("word2"));
//        assertThat(solver1.dictionary.contains("test"));
//        assertThat(solver1.dictionary.contains("emptyword"));
//        assertThat(!solver1.dictionary.contains("tost"));
//        assertThat(solver1.dictionary.size() == 4);
//
//        BoggleSolver solver2 = new BoggleSolver(new String[0]);
//        assertThat(solver2.dictionary.size() == 0);
    }

    private void testValidWords(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = (Set<String>) solver.getAllValidWords(new BoggleBoard(boardFileName));
//        allValidWords.forEach(System.out::println);
        assertThat(allValidWords.containsAll(List.of("AID", "DIE", "END", "YOU")));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
        assertThat(valueOfAllValidWords == 33);
    }

    private void testValidWordsWithQ(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = (Set<String>) solver.getAllValidWords(new BoggleBoard(boardFileName));
//        allValidWords.forEach(System.out::println);
        assertThat(allValidWords.containsAll(List.of("EQUATION", "QUESTION", "QUESTIONS", "TRIES")));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
        assertThat(valueOfAllValidWords == 84);
    }

    private void testScoreOf() {
        BoggleSolver solver = new BoggleSolver(
                new String[]{"", "t", "bs", "ham", "tost", "tost1", "1tost1", "7letter", "8letters", "more-than-8-letters"});
        assertThat(solver.scoreOf("") == 0);
        assertThat(solver.scoreOf("t") == 0);
        assertThat(solver.scoreOf("bs") == 0);
        assertThat(solver.scoreOf("ham") == 1);
        assertThat(solver.scoreOf("tost") == 1);
        assertThat(solver.scoreOf("tost1") == 2);
        assertThat(solver.scoreOf("1tost1") == 3);
        assertThat(solver.scoreOf("7letter") == 5);
        assertThat(solver.scoreOf("8letters") == 11);
        assertThat(solver.scoreOf("more-than-8-letters") == 11);

        assertThat(solver.scoreOf("wrong-word") == 0);
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}