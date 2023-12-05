import edu.princeton.cs.algs4.Bag;

public class BoggleSolver {

    private final BoggleTrieSET dictionary = new BoggleTrieSET();
    private int currentBoardId = 0;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) this.dictionary.add(word);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Bag<String> validWords = new Bag<>();
        currentBoardId++;

        for (int x = 0; x < board.rows(); x++) {
            for (int y = 0; y < board.cols(); y++) {
                populateValidWords(validWords, board, x, y, new boolean[board.rows()][board.cols()], new StringBuilder(), dictionary.getRoot());
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

    // this is a dfs like traverse method using visitedSquares to mark the visited ones.
    // We also keep the current Node each time, since we will only move by 1 letter each time. So this is a nice optimization
    // which allows us to check if the current node is null and if it is, then any other letter combination forward
    // won't be a word as well (it is like checking if this specific word is a prefix of any word in dictionary)
    // Keeping this node also allows us to set/check if a word is already added, instead of using a set and contains
    // which are both slower and check if it is actually a word, instead again of using get, from root, in dictionary
    private void populateValidWords(Bag<String> validWords, BoggleBoard board, int x, int y, boolean[][] visitedSquares,
                                    StringBuilder currentWord, BoggleTrieSET.Node previousNode) {
        if (x < 0 || y < 0 || x >= board.rows() || y >= board.cols()) return;

        if (visitedSquares[x][y]) return;
        visitedSquares[x][y] = true;

        final char currentChar = board.getLetter(x, y);
        currentWord.append(currentChar);
        BoggleTrieSET.Node nextNode = dictionary.getNextChar(previousNode, currentChar);
        if (nextNode == null) {
            goBackOneSquare(visitedSquares, x, y, currentWord);
            return;
        }

        if (currentChar == 'Q') {
            nextNode = dictionary.getNextChar(nextNode, 'U');
            if (nextNode == null) {
                goBackOneSquare(visitedSquares, x, y, currentWord);
                return;
            }
            currentWord.append('U');
        }
        String currentWordString = currentWord.toString();

//        StdOut.println(currentWordString);
        if (nextNode.isWord() && !nextNode.isWordFound(currentBoardId)) {
//        StdOut.println(currentWordString);
            validWords.add(currentWordString);
            nextNode.setLastBoardIdWordWasFound(currentBoardId);
        }

        populateValidWords(validWords, board, x + 1, y, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x, y - 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x + 1, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x + 1, y - 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y + 1, visitedSquares, currentWord, nextNode);
        populateValidWords(validWords, board, x - 1, y - 1, visitedSquares, currentWord, nextNode);
        goBackOneSquare(visitedSquares, x, y, currentWord);
    }

    private static void goBackOneSquare(boolean[][] visitedSquares, int x, int y, StringBuilder currentWord) {
        visitedSquares[x][y] = false;
        currentWord.deleteCharAt(currentWord.length() - 1);
        if (currentWord.length() > 0 && currentWord.charAt(currentWord.length() - 1) == 'Q')
            currentWord.deleteCharAt(currentWord.length() - 1);
    }
}
