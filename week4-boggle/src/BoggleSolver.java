import edu.princeton.cs.algs4.Stack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BoggleSolver {

    private final BoggleTrieSET dictionary = new BoggleTrieSET();

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) this.dictionary.add(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> validWords = new HashSet<>();

        for (int x = 0; x < board.rows(); x++) {
            for (int y = 0; y < board.cols(); y++) {
                populateValidWords(validWords, board, x, y, new Stack<>(), new StringBuilder(), dictionary.getRoot());
            }
        }

        return validWords;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (!dictionary.contains(word)) return 0;

        if (word.length() < 3) return 0;
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
        }

        return 11;
    }

    // this is a dfs like traverse method that uses DimensionalPoint in stack to mark the visited squares (see marked[]
    // in dfs).
    // We also keep the current Node each time, since we will only move by 1 letter each time. So this is a nice optimization
    // which also allows us to check if the current node is null and if it is, then any other letter combination forward
    // won't be a word as well (it is like checking if this specific word is a prefix of any word in dictionary)
    private void populateValidWords(Set<String> validWords, BoggleBoard board, int x, int y, Stack<DimensionalPoint> visitedSquares,
                                    StringBuilder currentWord, BoggleTrieSET.Node currentNode) {
        if (x < 0 || y < 0 || x >= board.rows() || y >= board.cols()) return;

        DimensionalPoint currentSquare = new DimensionalPoint(x, y);
        if (squareIsVisited(visitedSquares, currentSquare)) return;
        visitedSquares.push(currentSquare);

        final char currentChar = board.getLetter(x, y);
        currentWord.append(currentChar);
        BoggleTrieSET.Node nextNode = dictionary.getNextChar(currentNode, currentChar);
        if (nextNode == null) {
            goBackOneSquare(visitedSquares, currentWord);
            return;
        }

        if (currentChar == 'Q') {
            currentWord.append('U');
            nextNode = dictionary.getNextChar(nextNode, 'U');
            if (nextNode == null) {
                goBackOneSquare(visitedSquares, currentWord);
                return;
            }
        }
        String currentWordString = currentWord.toString();

        if (!(currentWord.length() < 3)) {
//            StdOut.println(currentWordString);
            if (!validWords.contains(currentWordString) && nextNode.isWord()) {
                validWords.add(currentWordString);
            }
        }

        populateValidWords(validWords, board, x + 1, y, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x, y - 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x + 1, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x + 1, y - 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y - 1, visitedSquares, currentWord, nextNode);
        goBackOneSquare(visitedSquares, currentWord);
    }

    private static void goBackOneSquare(Stack<DimensionalPoint> visitedSquares, StringBuilder currentWord) {
        visitedSquares.pop();
        currentWord.deleteCharAt(currentWord.length() - 1);
        if (currentWord.length() > 0 && currentWord.charAt(currentWord.length() - 1) == 'Q')
            currentWord.deleteCharAt(currentWord.length() - 1);
    }

    private boolean squareIsVisited(Stack<DimensionalPoint> visitedSquares, DimensionalPoint currentSquare) {
        for (DimensionalPoint d : visitedSquares) {
            if (d.equals(currentSquare)) return true;
        }

        return false;
    }

    private static class DimensionalPoint {
        private final int x;
        private final int y;

        DimensionalPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) return false;
            if (this == other) return true;
            if (!(other.getClass() == this.getClass())) return false;
            DimensionalPoint that = (DimensionalPoint) other;

            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "x: " + x + ", y: " + y;
        }
    }
}
