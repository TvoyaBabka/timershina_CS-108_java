import java.util.concurrent.Callable;

public class DetectorTask implements Callable<Result> {
    private final int[] data;
    private final int start;
    private final int end;
    private final int detectorId;
    private static final int ANOMALY_THRESHOLD = 9000;

    public DetectorTask(int[] data, int start, int end, int detectorId) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.detectorId = detectorId;
    }

    @Override
    public Result call() {
        long currentSum = 0;
        int currentAnomalies = 0;

        for (int i = start; i < end; i++) {
            currentSum += data[i];
            if (data[i] > ANOMALY_THRESHOLD) {
                currentAnomalies++;
            }
        }

        try {
            Thread.sleep((long) (Math.random() * 50));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return new Result(currentSum, currentAnomalies, start, end, detectorId);
    }
}