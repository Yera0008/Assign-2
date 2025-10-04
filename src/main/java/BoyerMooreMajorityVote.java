import java.util.*;

public class BoyerMooreMajorityVote {

    private PerformanceTracker performanceTracker;
    private boolean collectMetrics;

    public BoyerMooreMajorityVote() {
        this(false);
    }

    public BoyerMooreMajorityVote(boolean collectMetrics) {
        this.collectMetrics = collectMetrics;
        if (collectMetrics) {
            this.performanceTracker = new PerformanceTracker();
        }
    }

    public int findMajorityElement(int[] nums) {
        if (collectMetrics) {
            performanceTracker.startTimer();
            performanceTracker.incrementArrayAccess(nums != null ? nums.length : 0);
        }

        if (nums == null || nums.length == 0) {
            if (collectMetrics) performanceTracker.stopTimer();
            return Integer.MIN_VALUE;
        }

        if (nums.length == 1) {
            if (collectMetrics) {
                performanceTracker.incrementArrayAccess(1);
                performanceTracker.stopTimer();
            }
            return nums[0];
        }

        int candidate = findCandidate(nums);

        boolean isMajority = verifyCandidate(nums, candidate);

        if (collectMetrics) {
            performanceTracker.stopTimer();
        }

        return isMajority ? candidate : Integer.MIN_VALUE;
    }

    private int findCandidate(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (collectMetrics) {
                performanceTracker.incrementComparisons(1);
                performanceTracker.incrementArrayAccess(1);
            }

            if (count == 0) {
                candidate = num;
                count = 1;

                if (collectMetrics) {
                    performanceTracker.incrementAssignments(2);
                }
            } else if (candidate == num) {
                count++;

                if (collectMetrics) {
                    performanceTracker.incrementAssignments(1);
                }
            } else {
                count--;

                if (collectMetrics) {
                    performanceTracker.incrementAssignments(1);
                }
            }
        }

        return candidate;
    }

    private boolean verifyCandidate(int[] nums, int candidate) {
        int count = 0;
        int majorityThreshold = nums.length / 2;

        for (int num : nums) {
            if (collectMetrics) {
                performanceTracker.incrementComparisons(1);
                performanceTracker.incrementArrayAccess(1);
            }

            if (num == candidate) {
                count++;

                if (collectMetrics) {
                    performanceTracker.incrementAssignments(1);
                }

                if (count > majorityThreshold) {
                    if (collectMetrics) {
                        performanceTracker.incrementComparisons(1);
                    }
                    return true;
                }
            }
        }

        if (collectMetrics) {
            performanceTracker.incrementComparisons(1);
        }

        return count > majorityThreshold;
    }

    public MajorityResult findMajorityElementDetailed(int[] nums) {
        if (collectMetrics) {
            performanceTracker.startTimer();
            performanceTracker.incrementArrayAccess(nums != null ? nums.length : 0);
        }

        if (nums == null || nums.length == 0) {
            if (collectMetrics) performanceTracker.stopTimer();
            return new MajorityResult(Integer.MIN_VALUE, 0, 0, false);
        }

        int candidate = findCandidate(nums);
        int frequency = countFrequency(nums, candidate);
        boolean isMajority = frequency > nums.length / 2;

        if (collectMetrics) {
            performanceTracker.stopTimer();
        }

        return new MajorityResult(candidate, frequency, nums.length, isMajority);
    }

    private int countFrequency(int[] nums, int candidate) {
        int count = 0;
        for (int num : nums) {
            if (collectMetrics) {
                performanceTracker.incrementComparisons(1);
                performanceTracker.incrementArrayAccess(1);
            }

            if (num == candidate) {
                count++;

                if (collectMetrics) {
                    performanceTracker.incrementAssignments(1);
                }
            }
        }
        return count;
    }


    public PerformanceTracker getPerformanceTracker() {
        if (!collectMetrics) {
            throw new IllegalStateException("Metrics collection is not enabled");
        }
        return performanceTracker;
    }


    public void resetMetrics() {
        if (collectMetrics) {
            performanceTracker.reset();
        }
    }

    public static class MajorityResult {
        public final int candidate;
        public final int frequency;
        public final int totalElements;
        public final boolean isMajority;

        public MajorityResult(int candidate, int frequency, int totalElements, boolean isMajority) {
            this.candidate = candidate;
            this.frequency = frequency;
            this.totalElements = totalElements;
            this.isMajority = isMajority;
        }

        @Override
        public String toString() {
            if (!isMajority) {
                return String.format("No majority element found. Candidate: %d (appears %d/%d times, %.1f%%)",
                        candidate, frequency, totalElements, (frequency * 100.0 / totalElements));
            }
            return String.format("Majority element: %d (appears %d/%d times, %.1f%%)",
                    candidate, frequency, totalElements, (frequency * 100.0 / totalElements));
        }
    }
}