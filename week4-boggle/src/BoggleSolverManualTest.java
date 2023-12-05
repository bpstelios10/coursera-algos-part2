import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class BoggleSolverManualTest {

    private static final BoggleSolverManualTest tester = new BoggleSolverManualTest();

    public static void main(String[] args) {
        String yawlDictionary = args[0] + "/dictionary-yawl.txt";
        String boardWith2Points = args[0] + "/board-points2.txt";
        String boardWith4540Points = args[0] + "/board-points4540.txt";
        String boardWith13464Points = args[0] + "/board-points13464.txt";
        String boardWith26539Points = args[0] + "/board-points26539.txt";

        tester.testValidWords(yawlDictionary, boardWith2Points);
        tester.testValidWordsWithPoints(yawlDictionary, boardWith4540Points, 4540);
        tester.testValidWordsWithPoints(yawlDictionary, boardWith13464Points, 13464);
        tester.testValidWordsWithPoints(yawlDictionary, boardWith26539Points, 26539);
    }

    private void testValidWords(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = getAllValidWords(boardFileName, solver);
//        allValidWords.forEach(System.out::println);
        assertThat(allValidWords.containsAll(List.of("NTH", "PHT")));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
//        System.out.println(valueOfAllValidWords);
        assertThat(valueOfAllValidWords == 2);
    }

    private void testValidWordsWithPoints(String dictionaryFileName, String boardFileName, int score) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = getAllValidWords(boardFileName, solver);
//        allValidWords.forEach(System.out::println);
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
//        System.out.println(valueOfAllValidWords);
        assertThat(valueOfAllValidWords == score);
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
