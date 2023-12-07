import java.util.Arrays;

public class CircularSuffixArray {

    private final Integer[] index;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException("the argument to CircularSuffixArray constructor cannot be null");

        int length = s.length();
        char[] value = new char[length];
        index = new Integer[length];

        for (int i = 0; i < length; i++) {
            index[i] = i;
            value[i] = s.charAt(i);
        }

        Arrays.sort(index, (e1, e2) -> {
            for (int i = 0; i < length; i++) {
                char c1 = value[(i + e1) % length];
                char c2 = value[(i + e2) % length];
                if (c1 > c2) return 1;
                if (c1 < c2) return -1;
            }
            return 0;
        });
    }

    // length of s
    public int length() {
        return index.length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length())
            throw new IllegalArgumentException("the argument to index() is invalid: " + i);

        return index[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray("ABRACADABRA!");

        assertThat(circularSuffixArray.length() == 12);
        assertThat(circularSuffixArray.index(11) == 2);
        assertThat(circularSuffixArray.index(3) == 0);
        assertThat(circularSuffixArray.index(0) == 11);
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}
