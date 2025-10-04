public class BoyerMooreMajorityVoteTest {

    private static int testCount = 0;
    private static int passCount = 0;

    public static void main(String[] args) {
        System.out.println("Running Boyer-Moore Majority Vote Tests...\n");

        testSimpleMajority();
        testMajorityAtBeginning();
        testMajorityAtEnd();
        testNoMajority();
        testExactlyHalf();
        testSingleElement();
        testEmptyArray();
        testNullArray();
        testAllSameElements();
        testNegativeNumbers();
        testMixedPositiveNegative();
        testDetailedResultWithMajority();
        testDetailedResultWithoutMajority();
        testLargeArray();
        testPerformanceMetrics();
        testMetricsReset();
        testEarlyTermination();

        System.out.println("\n=== Test Results ===");
        System.out.printf("Passed: %d/%d tests%n", passCount, testCount);

        if (passCount == testCount) {
            System.out.println("ALL TESTS PASSED! ✅");
        } else {
            System.out.println("SOME TESTS FAILED! ❌");
        }
    }

    private static void assertEquals(Object expected, Object actual, String testName) {
        testCount++;
        boolean passed = expected.equals(actual);
        if (passed) {
            passCount++;
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName);
            System.out.println("  Expected: " + expected);
            System.out.println("  Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String testName) {
        testCount++;
        if (condition) {
            passCount++;
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName);
            System.out.println("  Expected: true");
            System.out.println("  Actual: false");
        }
    }

    private static void assertFalse(boolean condition, String testName) {
        testCount++;
        if (!condition) {
            passCount++;
            System.out.println("✅ PASS: " + testName);
        } else {
            System.out.println("❌ FAIL: " + testName);
            System.out.println("  Expected: false");
            System.out.println("  Actual: true");
        }
    }


    private static void testSimpleMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {2, 2, 1, 1, 1, 2, 2};
        assertEquals(2, bm.findMajorityElement(nums), "testSimpleMajority");
    }

    private static void testMajorityAtBeginning() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {3, 3, 2, 1, 3};
        assertEquals(3, bm.findMajorityElement(nums), "testMajorityAtBeginning");
    }

    private static void testMajorityAtEnd() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {1, 2, 3, 4, 5, 5, 5, 5, 5};
        assertEquals(5, bm.findMajorityElement(nums), "testMajorityAtEnd");
    }

    private static void testNoMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {1, 2, 3, 4, 5};
        assertEquals(Integer.MIN_VALUE, bm.findMajorityElement(nums), "testNoMajority");
    }

    private static void testExactlyHalf() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {1, 1, 2, 2};
        assertEquals(Integer.MIN_VALUE, bm.findMajorityElement(nums), "testExactlyHalf");
    }

    private static void testSingleElement() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {42};
        assertEquals(42, bm.findMajorityElement(nums), "testSingleElement");
    }

    private static void testEmptyArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {};
        assertEquals(Integer.MIN_VALUE, bm.findMajorityElement(nums), "testEmptyArray");
    }

    private static void testNullArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        assertEquals(Integer.MIN_VALUE, bm.findMajorityElement(null), "testNullArray");
    }

    private static void testAllSameElements() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {7, 7, 7, 7, 7, 7, 7};
        assertEquals(7, bm.findMajorityElement(nums), "testAllSameElements");
    }

    private static void testNegativeNumbers() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {-1, -1, -2, -1, -3, -1, -1};
        assertEquals(-1, bm.findMajorityElement(nums), "testNegativeNumbers");
    }

    private static void testMixedPositiveNegative() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {1, -1, 1, -1, 1, 1, 1};
        assertEquals(1, bm.findMajorityElement(nums), "testMixedPositiveNegative");
    }

    private static void testDetailedResultWithMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {3, 2, 3};
        BoyerMooreMajorityVote.MajorityResult result = bm.findMajorityElementDetailed(nums);

        assertTrue(result.isMajority, "testDetailedResultWithMajority - isMajority");
        assertEquals(3, result.candidate, "testDetailedResultWithMajority - candidate");
        assertEquals(2, result.frequency, "testDetailedResultWithMajority - frequency");
        assertEquals(3, result.totalElements, "testDetailedResultWithMajority - totalElements");
    }

    private static void testDetailedResultWithoutMajority() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int[] nums = {1, 2, 3};
        BoyerMooreMajorityVote.MajorityResult result = bm.findMajorityElementDetailed(nums);

        assertFalse(result.isMajority, "testDetailedResultWithoutMajority - isMajority");
        assertEquals(3, result.totalElements, "testDetailedResultWithoutMajority - totalElements");
    }

    private static void testLargeArray() {
        BoyerMooreMajorityVote bm = new BoyerMooreMajorityVote();
        int size = 100000;
        int[] nums = new int[size];
        int majorityElement = 999;

        for (int i = 0; i < size / 2 + 1; i++) {
            nums[i] = majorityElement;
        }

        java.util.Random random = new java.util.Random(42);
        for (int i = size / 2 + 1; i < size; i++) {
            nums[i] = random.nextInt(1000);
        }

        assertEquals(majorityElement, bm.findMajorityElement(nums), "testLargeArray");
    }

    private static void testPerformanceMetrics() {
        BoyerMooreMajorityVote bmWithMetrics = new BoyerMooreMajorityVote(true);
        int[] nums = {1, 2, 1, 2, 1, 1, 1};

        bmWithMetrics.findMajorityElement(nums);
        PerformanceTracker metrics = bmWithMetrics.getPerformanceTracker();

        assertTrue(metrics.getElapsedTime() > 0, "testPerformanceMetrics - elapsedTime");
        assertTrue(metrics.getMetric("comparisons") > 0, "testPerformanceMetrics - comparisons");
        assertTrue(metrics.getMetric("arrayAccess") > 0, "testPerformanceMetrics - arrayAccess");
    }

    private static void testMetricsReset() {
        BoyerMooreMajorityVote bmWithMetrics = new BoyerMooreMajorityVote(true);
        int[] nums = {1, 2, 1};

        bmWithMetrics.findMajorityElement(nums);
        long initialComparisons = bmWithMetrics.getPerformanceTracker().getMetric("comparisons");

        bmWithMetrics.resetMetrics();
        long resetComparisons = bmWithMetrics.getPerformanceTracker().getMetric("comparisons");

        assertEquals(0L, resetComparisons, "testMetricsReset - resetComparisons");
        assertTrue(initialComparisons > 0, "testMetricsReset - initialComparisons");
    }

    private static void testEarlyTermination() {
        int[] nums = {5, 5, 5, 5, 5, 1, 2, 3};
        BoyerMooreMajorityVote bmWithMetrics = new BoyerMooreMajorityVote(true);

        bmWithMetrics.findMajorityElement(nums);
        PerformanceTracker metrics = bmWithMetrics.getPerformanceTracker();

        assertTrue(metrics.getMetric("comparisons") > 0, "testEarlyTermination");
    }
}
