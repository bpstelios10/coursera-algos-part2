import java.util.Arrays;

public final class AcyclicWeightedVertexShortestPath {

    private AcyclicWeightedVertexShortestPath() {
    }

    public static void main(String[] args) {
        double[][] energies1 = {
                {10, 10, 10, 10, 10},
                {10, 2, 4, 1, 10},
                {10, 3, 2, 5, 10},
                {10, 10, 10, 10, 10}};
        double[][] energies = {
                {10, 10, 10, 10},
                {10, 3, 4, 10},
                {10, 7, 2, 10},
                {10, 2, 5, 10},
                {10, 10, 10, 10}};
        double[][] energies2 = {
                {1000.00, 1000.00, 1000.00, 1000.00, 1000.00},
                {1000.00, 220.47, 331.86, 214.50, 1000.00},
                {1000.00, 306.99, 221.90, 147.51, 1000.00},
                {1000.00, 114.46, 260.69, 208.87, 1000.00},
                {1000.00, 1000.00, 1000.00, 1000.00, 1000.00}
        };

        Arrays.stream(computeShortestHorizontalPath(energies1)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(computeShortestHorizontalPath(energies)).forEach(System.out::print);
        System.out.println();
        System.out.println("----");
        Arrays.stream(computeShortestVerticalPath(energies1)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(computeShortestVerticalPath(energies)).forEach(System.out::print);
        System.out.println();
        System.out.println("----");
        Arrays.stream(computeShortestVerticalPath(energies2)).forEach(System.out::print);
        System.out.println();
        Arrays.stream(computeShortestHorizontalPath(energies2)).forEach(System.out::print);
        System.out.println();
    }

    public static int[] computeShortestHorizontalPath(double[][] energies) {
        double[][] distTo = new double[energies.length][energies[0].length];
        int[][] edgeTo = new int[energies.length][energies[0].length];
        int shortestPathFirstIndex = -1;
        double shortestPathValue = Double.POSITIVE_INFINITY;
        int[] result = new int[energies[0].length];

        for (int x = 0; x < energies.length; x++) {
            distTo[x][energies[0].length - 1] = 0;
            edgeTo[x][energies[0].length - 1] = x;

            distTo[x][energies[0].length - 2] = energies[x][energies[0].length - 2];
            edgeTo[x][energies[0].length - 2] = x;
        }

        for (int y = energies[0].length - 3; y > 0; y--) {
            for (int x = 0; x < energies.length; x++) {
                if (x == 0 || x == energies.length - 1) {
                    distTo[x][y] = Double.POSITIVE_INFINITY;
                    edgeTo[x][y] = x;
                } else {
                    double rightTop = distTo[x - 1][y + 1];
                    double right = distTo[x][y + 1];
                    double rightBottom = distTo[x + 1][y + 1];
                    if (rightTop < right) {
                        if (rightTop < rightBottom) {
                            distTo[x][y] = energies[x][y] + rightTop;
                            edgeTo[x][y] = x - 1;
                        } else {
                            distTo[x][y] = energies[x][y] + rightBottom;
                            edgeTo[x][y] = x + 1;
                        }
                    } else {
                        if (right < rightBottom) {
                            distTo[x][y] = energies[x][y] + right;
                            edgeTo[x][y] = x;
                        } else {
                            distTo[x][y] = energies[x][y] + rightBottom;
                            edgeTo[x][y] = x + 1;
                        }
                    }
                }

                if (y == 1 && x != 0 && x != energies.length - 1) {
                    if (distTo[x][y] < shortestPathValue) {
                        shortestPathValue = distTo[x][y];
                        shortestPathFirstIndex = x;
                    }
                }
            }
        }

        if (shortestPathFirstIndex == -1) {
            for (int x = 1; x <= energies.length - 1; x++) {
                if (distTo[x][energies[0].length - 2] <= shortestPathValue) {
                    shortestPathValue = distTo[x][energies[0].length - 2];
                    shortestPathFirstIndex = x;
                }
            }
        }

        result[0] = shortestPathFirstIndex;
        result[1] = shortestPathFirstIndex;
        for (int y = 2; y < edgeTo[0].length; y++) {
            result[y] = edgeTo[result[y - 1]][y - 1];
        }

        return result;
    }

    public static int[] computeShortestVerticalPath(double[][] energies) {
        double[][] distTo = new double[energies.length][energies[0].length];
        int[][] edgeTo = new int[energies.length][energies[0].length];
        int shortestPathFirstIndex = -1;
        double shortestPathValue = Double.POSITIVE_INFINITY;
        int[] result = new int[energies.length];

        for (int y = 0; y < energies[0].length; y++) {
            distTo[energies.length - 1][y] = 0;
            edgeTo[energies.length - 1][y] = y;

            distTo[energies.length - 2][y] = energies[energies.length - 2][y];
            edgeTo[energies.length - 2][y] = y;
        }

        for (int x = energies.length - 3; x > 0; x--) {
            for (int y = 0; y < energies[0].length; y++) {
                if (y == 0 || y == energies[0].length - 1) {
                    distTo[x][y] = Double.POSITIVE_INFINITY;
                    edgeTo[x][y] = y;
                } else {
                    double bottomLeft = distTo[x + 1][y - 1];
                    double bottom = distTo[x + 1][y];
                    double bottomRight = distTo[x + 1][y + 1];

                    if (bottomLeft < bottom) {
                        if (bottomLeft < bottomRight) {
                            distTo[x][y] = energies[x][y] + bottomLeft;
                            edgeTo[x][y] = y - 1;
                        } else {
                            distTo[x][y] = energies[x][y] + bottomRight;
                            edgeTo[x][y] = y + 1;
                        }
                    } else {
                        if (bottom < bottomRight) {
                            distTo[x][y] = energies[x][y] + bottom;
                            edgeTo[x][y] = y;
                        } else {
                            distTo[x][y] = energies[x][y] + bottomRight;
                            edgeTo[x][y] = y + 1;
                        }
                    }
                }

                if (x == 1 && y != 0 && y != energies[0].length - 1) {
                    if (distTo[x][y] < shortestPathValue) {
                        shortestPathValue = distTo[x][y];
                        shortestPathFirstIndex = y;
                    }
                }
            }
        }

        if (shortestPathFirstIndex == -1) {
            for (int y = 1; y <= energies[0].length - 1; y++) {
                if (distTo[energies.length - 2][y] <= shortestPathValue) {
                    shortestPathValue = distTo[energies.length - 2][y];
                    shortestPathFirstIndex = y;
                }
            }
        }

        result[0] = shortestPathFirstIndex;
        result[1] = shortestPathFirstIndex;
        for (int x = 2; x < edgeTo.length; x++) {
            result[x] = edgeTo[x - 1][result[x - 1]];
        }

        return result;
    }
}
