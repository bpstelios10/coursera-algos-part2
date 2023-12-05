public class BoggleTrieSET {

    private static final int NUMBER_OF_CAPITAL_LETTERS = 26;
    private static final int CAPITAL_LETTERS_OFFSET = 65;

    private Node root;

    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");

        root = add(root, key, 0);
    }

    public Node get(String key) {
        return get(root, key, 0);
    }

    public Node getNextChar(Node x, char c) {
        if (x == null) return null;

        return x.next[c - CAPITAL_LETTERS_OFFSET];
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;

        return x.isString;
    }

    public Node getRoot() {
        return root;
    }

    public static class Node {
        private final Node[] next = new Node[NUMBER_OF_CAPITAL_LETTERS];
        private boolean isString;

        public boolean isWord() {
            return isString;
        }
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();

        if (d == key.length()) {
            x.isString = true;
        } else {
            int c = key.charAt(d);
            x.next[c - CAPITAL_LETTERS_OFFSET] = add(x.next[c - CAPITAL_LETTERS_OFFSET], key, d + 1);
        }

        return x;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        int c = key.charAt(d);

        return get(x.next[c - CAPITAL_LETTERS_OFFSET], key, d + 1);
    }
}
