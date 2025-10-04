import java.util.HashMap;
import java.util.Map;

public class PerformanceTracker {
    private long startTime;
    private long endTime;
    private Map<String, Long> metrics;

    public PerformanceTracker() {
        this.metrics = new HashMap<>();
        reset();
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void incrementComparisons(long count) {
        metrics.merge("comparisons", count, Long::sum);
    }

    public void incrementArrayAccess(long count) {
        metrics.merge("arrayAccess", count, Long::sum);
    }

    public void incrementAssignments(long count) {
        metrics.merge("assignments", count, Long::sum);
    }

    public void incrementOperations(String operation, long count) {
        metrics.merge(operation, count, Long::sum);
    }

    public long getMetric(String metric) {
        return metrics.getOrDefault(metric, 0L);
    }

    public Map<String, Long> getAllMetrics() {
        return new HashMap<>(metrics);
    }

    public void reset() {
        metrics.clear();
        metrics.put("comparisons", 0L);
        metrics.put("arrayAccess", 0L);
        metrics.put("assignments", 0L);
        startTime = 0;
        endTime = 0;
    }

    public void printMetrics() {
        System.out.println("=== Performance Metrics ===");
        System.out.printf("Time elapsed: %,d ns%n", getElapsedTime());
        System.out.printf("Comparisons: %,d%n", getMetric("comparisons"));
        System.out.printf("Array accesses: %,d%n", getMetric("arrayAccess"));
        System.out.printf("Assignments: %,d%n", getMetric("assignments"));
    }

    public String getMetricsCSV() {
        return String.format("%d,%d,%d,%d",
                getElapsedTime(),
                getMetric("comparisons"),
                getMetric("arrayAccess"),
                getMetric("assignments"));
    }

    public static String getCSVHeader() {
        return "time_ns,comparisons,array_access,assignments";
    }
}
