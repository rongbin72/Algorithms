import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] thresholds;
    private int t;

    /**
     * perform trials independent experiments on an n-by-n grid
     *
     * @param n      size of gird
     * @param trials number of independent experiments
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.thresholds = new double[trials];
        this.t = trials;

        for (int i = 0; i < trials; i++) {
            thresholds[i] = calcThrshold(n);
        }
    }

    private double calcThrshold(int n) {
        int cnt = 0;
        int row, col;
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            row = StdRandom.uniform(1, n + 1);
            col = StdRandom.uniform(1, n + 1);
            if (!perc.isOpen(row, col)) {
                perc.open(row, col);
                cnt++;
            }
        }

        return (double) cnt / (n * n);
    }


    /**
     * sample mean of percolation threshold
     *
     * @return mean
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return std
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * low  endpoint of 95% confidence interval
     *
     * @return low
     */
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(t);
    }

    /**
     * high endpoint of 95% confidence interval
     *
     * @return high
     */
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(t);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean\t" + "=\t" + ps.mean());
        System.out.println("stddev\t" + "=\t" + ps.stddev());
        System.out.printf("95%% confidence interval\t=\t[%f, %f]", ps.confidenceLo(),
                          ps.confidenceHi());
    }
}
