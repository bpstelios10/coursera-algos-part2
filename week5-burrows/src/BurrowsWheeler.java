import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    private static final int NUMBER_OF_CHARACTERS = 256;

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {
        String inputWord = BinaryStdIn.readString();

        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(inputWord);
        for (int i = 0; i < inputWord.length(); i++)
            if (circularSuffixArray.index(i) == 0)
                BinaryStdOut.write(i, 32);

        for (int i = 0; i < inputWord.length(); i++) {
            if (circularSuffixArray.index(i) == 0)
                BinaryStdOut.write(inputWord.charAt(inputWord.length() - 1));
            else
                BinaryStdOut.write(inputWord.charAt(circularSuffixArray.index(i) - 1));
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int nextIndex = BinaryStdIn.readInt();
//        System.out.println(nextIndex);

        String transformedWord = BinaryStdIn.readString();
//        System.out.println(transformedWord);

        char[] lastColumn = transformedWord.toCharArray();
        int[] next = new int[transformedWord.length()];
        int[] charFrequency = new int[NUMBER_OF_CHARACTERS + 1];
        char[] firstColumn = new char[transformedWord.length()];

        for (int i = 0; i < transformedWord.length(); i++)
            charFrequency[lastColumn[i] + 1]++;
        for (int i = 0; i < NUMBER_OF_CHARACTERS; i++)
            charFrequency[i + 1] += charFrequency[i];
        for (int i = 0; i < transformedWord.length(); i++) {
            int rowNumber = charFrequency[lastColumn[i]]++;
            firstColumn[rowNumber] = lastColumn[i];
            next[rowNumber] = i;
        }

        for (int i = 0; i < transformedWord.length(); i++) {
            BinaryStdOut.write(firstColumn[nextIndex]);
            nextIndex = next[nextIndex];
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) BurrowsWheeler.transform();
        if (args[0].equals("+")) BurrowsWheeler.inverseTransform();
    }
}
