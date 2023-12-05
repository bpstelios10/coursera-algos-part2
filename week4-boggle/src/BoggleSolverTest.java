import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class BoggleSolverTest {

    private static final BoggleSolverTest tester = new BoggleSolverTest();

    public static void main(String[] args) {
        String algs4Dictionary = args[0] + "/dictionary-algs4.txt";
        String board4x4 = args[0] + "/board4x4.txt";
        String boardWithQ = args[0] + "/board-q.txt";

        tester.testConstructor();
        tester.testValidWords(algs4Dictionary, board4x4);
        tester.testValidWordsWithQ(algs4Dictionary, boardWithQ);
        tester.testScoreOf();
    }

    private void testConstructor() {
        List<String> dictionary1 = List.of("WORDONE", "WORDTWO", "TEST", "EMPTYWORD");
        new BoggleSolver(dictionary1.toArray(new String[4]));
//        BoggleSolver solver1 = new BoggleSolver(dictionary1.toArray(new String[4]));
//        assertThat(solver1.dictionary.contains("WORDONE"));
//        assertThat(solver1.dictionary.contains("WORDTWO"));
//        assertThat(solver1.dictionary.contains("TEST"));
//        assertThat(solver1.dictionary.contains("EMPTYWORD"));
//        assertThat(!solver1.dictionary.contains("TOST"));
//        assertThat(solver1.dictionary.size() == 4);
//
//        BoggleSolver solver2 = new BoggleSolver(new String[0]);
//        assertThat(solver2.dictionary.size() == 0);
    }

    private void testValidWords(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = getAllValidWords(boardFileName, solver);
//        allValidWords.forEach(System.out::println);
        assertThat(allValidWords.containsAll(List.of("AID", "DIE", "END", "YOU")));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
        assertThat(valueOfAllValidWords == 33);
    }

    private void testValidWordsWithQ(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = getAllValidWords(boardFileName, solver);
//        allValidWords.forEach(System.out::println);
        assertThat(allValidWords.containsAll(List.of("EQUATION", "QUESTION", "QUESTIONS", "TRIES")));
        assertThat(!allValidWords.contains("TRIE"));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
        assertThat(valueOfAllValidWords == 84);
    }

    private void testScoreOf() {
        BoggleSolver solver = new BoggleSolver(
                new String[]{"", "T", "BS", "HAM", "TOST", "TOSTT", "TTOSTT", "LETTERS", "LETTERSS", "MORELETTERS"});
        assertThat(solver.scoreOf("") == 0);
        assertThat(solver.scoreOf("T") == 0);
        assertThat(solver.scoreOf("BS") == 0);
        assertThat(solver.scoreOf("HAM") == 1);
        assertThat(solver.scoreOf("TOS") == 0);
        assertThat(solver.scoreOf("TOST") == 1);
        assertThat(solver.scoreOf("TOSTT") == 2);
        assertThat(solver.scoreOf("TTOSTT") == 3);
        assertThat(solver.scoreOf("LETTERS") == 5);
        assertThat(solver.scoreOf("LETTERSS") == 11);
        assertThat(solver.scoreOf("MORELETTERS") == 11);

        assertThat(solver.scoreOf("WRONGWORD") == 0);
    }

    private static Set<String> getAllValidWords(String boardFileName, BoggleSolver solver) {
        Bag<String> allValidWordsBag = (Bag<String>) solver.getAllValidWords(new BoggleBoard(boardFileName));
        Iterator<String> iterator = allValidWordsBag.iterator();
        Set<String> allValidWords = new HashSet<>();
        while (iterator.hasNext()) {
            allValidWords.add(iterator.next());
        }
        return allValidWords;
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}