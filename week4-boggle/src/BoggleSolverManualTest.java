import edu.princeton.cs.algs4.In;

import java.io.File;
import java.util.List;
import java.util.Set;

class BoggleSolverManualTest {

    private static final BoggleSolverManualTest tester = new BoggleSolverManualTest();

    public static void main(String[] args) {
        tester.testValidWords(args[0], args[1]);
    }

    private void testValidWords(String dictionaryFileName, String boardFileName) {
        In in = new In(new File(dictionaryFileName));
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);

        Set<String> allValidWords = (Set<String>) solver.getAllValidWords(new BoggleBoard(boardFileName));
        allValidWords.forEach(System.out::println);
//        assertThat(allValidWords.containsAll(List.of("AID", "DIE", "END", "YOU")));
        int valueOfAllValidWords = allValidWords.stream().mapToInt(solver::scoreOf).sum();
        System.out.println(valueOfAllValidWords);
//        assertThat(valueOfAllValidWords == 33);
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}