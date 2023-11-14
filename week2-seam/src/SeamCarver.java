import edu.princeton.cs.algs4.Picture;

import java.awt.Color;
import java.util.Arrays;

public class SeamCarver {

    private int[][] picturePixels;
    private double[][] energies;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("arguments to SeamCarver constructor cant be null");

        this.picturePixels = new int[picture.height()][picture.width()];

        // in one pair of loops create the array of pixes, energies and also the copy of the initial picture
        for (int x = 0; x < picture.height(); x++) {
            for (int y = 0; y < picture.width(); y++) {
                picturePixels[x][y] = picture.getRGB(y, x);
            }
        }
        calculateEnergies();
    }

    // current picture
    public Picture picture() {
        Picture picture = new Picture(width(), height());
        for (int x = 0; x < picture.height(); x++) {
            for (int y = 0; y < picture.width(); y++) {
                picture.setRGB(y, x, picturePixels[x][y]);
            }
        }

        return picture;
    }

    // width of current picture
    public int width() {
        return picturePixels[0].length;
    }

    // height of current picture
    public int height() {
        return picturePixels.length;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        validatePixel(x, y);

        return getEnergies()[y][x];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (height() < 2 || width() < 2) {
            int[] seam = new int[width()];
            for (int i = 0; i < width(); i++) {
                seam[i] = 0;
            }
            return seam;
        }

        return AcyclicWeightedVertexShortestPath.computeShortestHorizontalPath(getEnergies());
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (height() < 2 || width() < 2) {
            int[] seam = new int[height()];
            for (int i = 0; i < height(); i++) {
                seam[i] = 0;
            }
            return seam;
        }

        return AcyclicWeightedVertexShortestPath.computeShortestVerticalPath(getEnergies());
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("argument to removeHorizontalSeam cant be null");
        if (height() <= 1) throw new IllegalArgumentException("cant remove horizontal seam when height is less than 2");
        if (seam.length != width()) {
            throw new IllegalArgumentException("cant remove horizontal seam when its length is not equal to picture width");
        }
        validateSeamSequence(seam, height());

        int[][] tempPixels = new int[height() - 1][width()];

        for (int y = 0; y < width(); y++) {
            int seamX = 0;
            for (int x = 0; x < height(); x++) {
                if (x != seam[y]) {
                    tempPixels[seamX++][y] = picturePixels[x][y];
                }
            }
        }

        picturePixels = tempPixels;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("argument to removeVerticalSeam cant be null");
        if (width() <= 1) throw new IllegalArgumentException("cant remove vertical seam when width is less than 2");
        if (seam.length != height())
            throw new IllegalArgumentException("cant remove vertical seam when its length is not equal to picture height");
        validateSeamSequence(seam, width());

        int[][] tempPixels = new int[height()][width() - 1];

        for (int x = 0; x < height(); x++) {
            int seamY = 0;
            for (int y = 0; y < width(); y++) {
                if (y != seam[x]) {
                    tempPixels[x][seamY++] = picturePixels[x][y];
                }
            }
        }

        picturePixels = tempPixels;
    }

    //  unit testing (optional)
    public static void main(String[] args) {
        Picture pic = new Picture(args[0]);
        SeamCarver seamCarver = new SeamCarver(pic);

        seamCarver.printEnergies();
        seamCarver.printPixels();

        int[] verticalSeam = seamCarver.findVerticalSeam();
        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
        System.out.println();

        int[] horizontalSeam = seamCarver.findHorizontalSeam();
        Arrays.stream(horizontalSeam).forEach(i -> System.out.print(i + " "));
        System.out.println();

        seamCarver.removeVerticalSeam(verticalSeam);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.printPixels();
        verticalSeam = seamCarver.findVerticalSeam();
        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
        System.out.println();
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.printPixels();

        verticalSeam = seamCarver.findVerticalSeam();
        Arrays.stream(verticalSeam).forEach(i -> System.out.print(i + " "));
        System.out.println();
//        try {
//            seamCarver.removeHorizontalSeam(verticalSeam);
//        } catch (Exception ex) {
//            System.out.println("should throw illegal argument exception. it actually is: " + ex.getClass());
//        }
    }

    private void calculateEnergies() {
        energies = new double[picturePixels.length][picturePixels[0].length];

        for (int x = 0; x < picturePixels.length; x++) {
            for (int y = 0; y < picturePixels[0].length; y++) {
                energies[x][y] = getEnergy(picturePixels, x, y);
            }
        }
    }

    // energy of pixel at column x and row y
    private double getEnergy(int[][] pict, int x, int y) {
        if (x == 0 || y == 0 || x == pict.length - 1 || y == pict[0].length - 1) return 1000;

        Color leftPixel = new Color(pict[x - 1][y]);
        Color rightPixel = new Color(pict[x + 1][y]);
        Color topPixel = new Color(pict[x][y - 1]);
        Color bottomPixel = new Color(pict[x][y + 1]);

        double dX = Math.pow(leftPixel.getRed() - rightPixel.getRed(), 2) + Math.pow(leftPixel.getGreen() - rightPixel.getGreen(), 2) + Math.pow(leftPixel.getBlue() - rightPixel.getBlue(), 2);
        double dY = Math.pow(topPixel.getRed() - bottomPixel.getRed(), 2) + Math.pow(topPixel.getGreen() - bottomPixel.getGreen(), 2) + Math.pow(topPixel.getBlue() - bottomPixel.getBlue(), 2);

        return Math.sqrt(dX + dY);
    }

    private double[][] getEnergies() {
        if (energies.length != picturePixels.length || energies[0].length != picturePixels[0].length)
            calculateEnergies();

        return energies;
    }

    private void validatePixel(int x, int y) {
        if (x < 0 || y < 0 || x >= width() || y >= height())
            throw new IllegalArgumentException("arguments x,y don't return a valid pixel of the current image");
    }

    private void validateSeamSequence(int[] seam, int topLimit) {
        if (seam[seam.length - 1] >= topLimit || seam[seam.length - 1] < 0)
            throw new IllegalArgumentException("not a valid seam sequence");

        for (int i = 0; i < seam.length - 1; i++) {
            if (seam[i] - seam[i + 1] < -1 || seam[i] - seam[i + 1] > 1 || seam[i] >= topLimit || seam[i] < 0)
                throw new IllegalArgumentException("not a valid seam sequence");
        }
    }

    private void printPixels() {
        Arrays.stream(picturePixels).forEach(e -> {
            Arrays.stream(e).forEach(k -> System.out.printf("%10d ", k));
            System.out.println();
        });
    }

    private void printEnergies() {
        Arrays.stream(getEnergies()).forEach(e -> {
            Arrays.stream(e).forEach(k -> System.out.printf("%5.2f ", k));
            System.out.println();
        });
    }
}
