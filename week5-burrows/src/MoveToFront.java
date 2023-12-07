import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int NUMBER_OF_CHARACTERS = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        Node root = initializeLinkedNodes();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
//            System.out.println(c);
            int nodeIndex = 0;
            boolean isFound = false;
            Node currentNode = root;
            do {
                if (currentNode.value == c) {
                    isFound = true;
//                    System.out.println(c);
                    BinaryStdOut.write(nodeIndex, 8);
                } else {
                    nodeIndex++;
                    currentNode = currentNode.next;
                }
            } while (!isFound && currentNode != null);

            if (currentNode == null) throw new RuntimeException("character not supported: " + c);
            moveNodeFirst(root, currentNode);
            root = currentNode;
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        Node root = initializeLinkedNodes();

        while (!BinaryStdIn.isEmpty()) {
            int nextLetterIndex = BinaryStdIn.readInt(8);
            Node characterNode = getByIndex(root, nextLetterIndex);
            if (characterNode == null) throw new RuntimeException("character not found!");
            BinaryStdOut.write(characterNode.value, 8);

            moveNodeFirst(root, characterNode);
            root = characterNode;
        }

        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) MoveToFront.encode();
        if (args[0].equals("+")) MoveToFront.decode();
    }

    private static Node initializeLinkedNodes() {
        Node root = new Node(null, null, (char) 0);

        for (int i = 1; i < NUMBER_OF_CHARACTERS; i++) {
            add(root, (char) i);
        }

        return root;
    }

    private static void add(Node root, char ch) {
        if (root == null) throw new RuntimeException("trying to add a character, while root is null");

        Node nextNode = new Node(null, null, ch);
        Node currentNode = root;
        while (currentNode.next != null) currentNode = currentNode.next;
        currentNode.next = nextNode;
        nextNode.previous = currentNode;
    }

    private static Node getByIndex(Node root, int index) {
        if (root == null) throw new RuntimeException("trying to get a character, while root is null");

        Node currentNode = root;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
            if (currentNode == null) throw new RuntimeException("Character provided is out of index");
        }

        return currentNode;
    }

    private static void moveNodeFirst(Node root, Node characterNode) {
        if (root == null) throw new RuntimeException("trying to get a character, while root is null");
        if (root == characterNode) return;

        if (characterNode.previous != null) characterNode.previous.next = characterNode.next;
        if (characterNode.next != null) characterNode.next.previous = characterNode.previous;
        characterNode.next = root;
        characterNode.previous = null;
        root.previous = characterNode;
    }

    private static class Node {
        private Node next;
        private Node previous;
        private final char value;

        public Node(Node next, Node previous, char ch) {
            this.next = next;
            this.previous = previous;
            value = ch;
        }
    }
}
