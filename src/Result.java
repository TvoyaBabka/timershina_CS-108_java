public class Result {
    private final long sum;
    private final int anomalies;
    private final int start;
    private final int end;
    private final int detectorId;

    public Result(long sum, int anomalies, int start, int end, int detectorId) {
        this.sum = sum;
        this.anomalies = anomalies;
        this.start = start;
        this.end = end;
        this.detectorId = detectorId;
    }

    public long getSum() {
        return sum;
    }

    public int getAnomalies() {
        return anomalies;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDetectorId() {
        return detectorId;
    }

    @Override
    public String toString() {
        return String.format("Детектор-#%d: сумма = %d, аномалий = %d", detectorId, sum, anomalies);
    }
}