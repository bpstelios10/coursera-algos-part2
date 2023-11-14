import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarverTest {

    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver seamCarver = new SeamCarver(pic);
//        pic.show();

        assertThat(seamCarver.picture().equals(pic));
        assertThat(seamCarver.width() == 6);
        assertThat(seamCarver.height() == 5);

        testEnergies(seamCarver);

        testSeamFinders(seamCarver);

        testRemoveHorizontalExceptions(seamCarver);
        testRemoveVerticalExceptions(seamCarver);

        int[] toBeRemovedVerticalSeam = {4, 4, 3, 2, 2};
        seamCarver.removeVerticalSeam(toBeRemovedVerticalSeam);
        System.out.println();
        seamCarver.picture();

        System.out.println("-----");
        seamCarver = new SeamCarver(pic);
        seamCarver.picture();
        int[] toBeRemovedHorizontalSeam = {2, 2, 1, 2, 1, 1};
        seamCarver.removeHorizontalSeam(toBeRemovedHorizontalSeam);
        System.out.println();
        seamCarver.picture();

        testRemovingAllVerticals();
        testRemovingAllHorizontal();
    }

    private static void testRemovingAllVerticals() {
//        SeamCarver seamCarver = new SeamCarver();
//
//        seamCarver.printEnergies();
//        seamCarver.printPixels();
//
//        int[] verticalSeam = seamCarver.findVerticalSeam();
//        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//
//        int[] horizontalSeam = seamCarver.findHorizontalSeam();
//        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//
//        seamCarver.removeVerticalSeam(verticalSeam);
//        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
//        seamCarver.printPixels();
//        verticalSeam = seamCarver.findVerticalSeam();
//        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
    }

    private static void testRemovingAllHorizontal() {
//        Picture pic = new Picture(args[0]);
//        SeamCarver seamCarver = new SeamCarver(pic);
//
//        seamCarver.printEnergies();
//        seamCarver.printPixels();
//
//        int[] verticalSeam = seamCarver.findVerticalSeam();
//        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//
//        int[] horizontalSeam = seamCarver.findHorizontalSeam();
//        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//
//        seamCarver.removeHorizontalSeam(horizontalSeam);
//        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
//        seamCarver.printPixels();
//        horizontalSeam = seamCarver.findHorizontalSeam();
//        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//        seamCarver.removeHorizontalSeam(horizontalSeam);
//        seamCarver.printPixels();
//
//        horizontalSeam = seamCarver.findHorizontalSeam();
//        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
//        try {
//            seamCarver.removeHorizontalSeam(horizontalSeam);
//        } catch (Exception ex) {
//            System.out.println("should throw illegal argument exception. it actually is: " + ex.getClass());
//        }
    }

    private static void testEnergies(SeamCarver seamCarver) {
        assertThat(seamCarver.energy(0, 0) == 1000);
        assertThat(seamCarver.energy(0, 4) == 1000);
        assertThat(seamCarver.energy(5, 0) == 1000);
        assertThat(seamCarver.energy(5, 4) == 1000);
        assertThat(seamCarver.energy(0, 1) == 1000);
        assertThat(seamCarver.energy(5, 1) == 1000);
        assertThat(seamCarver.energy(1, 0) == 1000);
        assertThat(seamCarver.energy(1, 4) == 1000);

        assertThat(seamCarver.energy(1, 1) == 237.34784599823104);
        assertThat(seamCarver.energy(1, 2) == 138.69390758068647);
    }

    private static void testSeamFinders(SeamCarver seamCarver) {
        int[] verticalSeam = seamCarver.findVerticalSeam();
        int[] expectedVerticalSeam = {4, 4, 3, 2, 2};
//        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
        assertThat(Arrays.equals(verticalSeam, expectedVerticalSeam));

        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        int[] expectedHorizontalSeam = {2, 2, 1, 2, 1, 1};
//        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
//        System.out.println();
        assertThat(Arrays.equals(horizontalSeam, expectedHorizontalSeam));


    }

    private static void testRemoveHorizontalExceptions(SeamCarver seamCarver) {
        try {
            seamCarver.removeHorizontalSeam(null);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("argument to removeHorizontalSeam cant be null".equals(ex.getMessage()));
        }

        try {
            int[] testShorterSequence = {1, 1, 1, 1, 1};
            seamCarver.removeHorizontalSeam(testShorterSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("cant remove horizontal seam when its length is not equal to picture width".equals(ex.getMessage()));
        }

        try {
            int[] testLongerSequence = {1, 1, 1, 1, 1, 1, 1};
            seamCarver.removeHorizontalSeam(testLongerSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("cant remove horizontal seam when its length is not equal to picture width".equals(ex.getMessage()));
        }

        try {
            int[] testInvalidRangeSequence = {1, 2, 3, 4, 5, 6};
            seamCarver.removeHorizontalSeam(testInvalidRangeSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("not a valid seam sequence".equals(ex.getMessage()));
        }

        try {
            int[] testInvalidSequence = {3, 4, 2, 4, 5, 4};
            seamCarver.removeHorizontalSeam(testInvalidSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("not a valid seam sequence".equals(ex.getMessage()));
        }
    }

    private static void testRemoveVerticalExceptions(SeamCarver seamCarver) {
        try {
            seamCarver.removeVerticalSeam(null);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("argument to removeVerticalSeam cant be null".equals(ex.getMessage()));
        }

        try {
            int[] testShorterSequence = {1, 1, 1, 1};
            seamCarver.removeVerticalSeam(testShorterSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("cant remove vertical seam when its length is not equal to picture height".equals(ex.getMessage()));
        }

        try {
            int[] testLongerSequence = {1, 1, 1, 1, 1, 1};
            seamCarver.removeVerticalSeam(testLongerSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("cant remove vertical seam when its length is not equal to picture height".equals(ex.getMessage()));
        }

        try {
            int[] testInvalidRangeSequence = {1, 2, 3, 4, 6};
            seamCarver.removeVerticalSeam(testInvalidRangeSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("not a valid seam sequence".equals(ex.getMessage()));
        }

        try {
            int[] testInvalidSequence = {3, 4, 2, 4, 5};
            seamCarver.removeVerticalSeam(testInvalidSequence);
        } catch (Exception ex) {
            assertThat(IllegalArgumentException.class == ex.getClass());
            assertThat("not a valid seam sequence".equals(ex.getMessage()));
        }
    }

    private static void assertThat(boolean assertion) {
        if (!assertion) throw new RuntimeException("Assertion failed");
    }
}
