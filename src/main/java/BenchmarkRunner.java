import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--benchmark")) {
            runComprehensiveBenchmark();
        } else {
            runInteractiveMode();
        }
    }

    private static void runInteractiveMode() {
        System.out.println("=== Boyer-Moore Majority Vote Algorithm ===");
        System.out.println("Testing with sample arrays...");

        int[][] testCases = {
                {2, 2, 1, 1, 1, 2, 2},
                {3, 2, 3},
                {1, 2, 3, 4, 5},
                {1},
                {1, 1, 2, 2},
                {5, 5, 5, 2, 2, 5, 5},
                {}
        };

        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(true);

        for (int i = 0; i < testCases.length; i++) {
            System.out.printf("\nTest Case %d: %s%n", i + 1, Arrays.toString(testCases[i]));

            BoyerMooreMajorityVote.MajorityResult result = bm.findMajorityElementDetailed(testCases[i]);
            System.out.println("Result: " + result);

            bm.getPerformanceTracker().printMetrics();
            bm.resetMetrics();
        }

        System.out.println("\n=== Running Scalability Benchmark ===");
        runScalabilityBenchmark();
    }

    private static void runComprehensiveBenchmark() {
        try {
            FileWriter csvWriter = new FileWriter("benchmark_results.csv");
            csvWriter.write("input_size,scenario,time_ns,comparisons,array_access,assignments\n");

            int[] sizes = {100, 1000, 10000, 100000, 1000000};
            Random random = new Random(42);

            for (int size : sizes) {
                System.out.printf("Benchmarking size: %,d%n", size);

                benchmarkScenario(size, "random_with_majority", true, false, random, csvWriter);
                benchmarkScenario(size, "random_no_majority", false, false, random, csvWriter);
                benchmarkScenario(size, "sorted_with_majority", true, true, random, csvWriter);
            }

            csvWriter.close();
            System.out.println("Benchmark results saved to benchmark_results.csv");

        } catch (IOException e) {
            System.err.println("Error writing benchmark results: " + e.getMessage());
        }
    }

    private static void benchmarkScenario(int size, String scenario,
                                          boolean hasMajority, boolean isSorted,
                                          Random random, FileWriter csvWriter) throws IOException {
        int[] array = generateTestArray(size, hasMajority, isSorted, random);
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(true);

        for (int i = 0; i < 10; i++) {
            bm.findMajorityElement(array);
            bm.resetMetrics();
        }

        long totalTime = 0;
        int runs = 100;

        for (int i = 0; i < runs; i++) {
            bm.findMajorityElement(array);
            PerformanceTracker metrics = bm.getPerformanceTracker();
            totalTime += metrics.getElapsedTime();
            bm.resetMetrics();
        }

        bm.findMajorityElement(array);
        PerformanceTracker metrics = bm.getPerformanceTracker();

        String line = String.format("%,d,%s,%,d,%d,%d,%d%n",
                size, scenario, totalTime / runs,
                metrics.getMetric("comparisons"),
                metrics.getMetric("arrayAccess"),
                metrics.getMetric("assignments"));

        csvWriter.write(line);
        System.out.printf("  %s: avg time = %,d ns%n", scenario, totalTime / runs);
    }

    private static int[] generateTestArray(int size, boolean hasMajority, boolean isSorted, Random random) {
        int[] array = new int[size];

        if (hasMajority) {
            int majorityElement = random.nextInt(100);
            int majorityCount = size / 2 + 1;

            for (int i = 0; i < majorityCount; i++) {
                array[i] = majorityElement;
            }

            for (int i = majorityCount; i < size; i++) {
                array[i] = random.nextInt(100);
            }

            if (!isSorted) {
                shuffleArray(array, random);
            }
        } else {
            for (int i = 0; i < size; i++) {
                array[i] = random.nextInt(size / 2 + 1);
            }

            if (isSorted) {
                Arrays.sort(array);
            }
        }

        return array;
    }

    private static void shuffleArray(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static void runScalabilityBenchmark() {
        int[] sizes = {100, 500, 1000, 5000, 10000};
        Random random = new Random(42);

        System.out.printf("%-12s | %-8s | %-12s | %-12s | %-12s%n",
                "Size", "Time(ns)", "Comparisons", "Array Access", "Assignments");
        System.out.println("------------|----------|--------------|--------------|--------------");

        for (int size : sizes) {
            int[] array = generateTestArray(size, true, false, random);
            BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote(true);

            bm.findMajorityElement(array);
            PerformanceTracker metrics = bm.getPerformanceTracker();

            System.out.printf("%,10d | %,8d | %,12d | %,12d | %,12d%n",
                    size, metrics.getElapsedTime(),
                    metrics.getMetric("comparisons"),
                    metrics.getMetric("arrayAccess"),
                    metrics.getMetric("assignments"));
        }
    }
}