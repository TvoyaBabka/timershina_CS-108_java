import java.util.concurrent.CyclicBarrier;

public class PhasedDetector implements Runnable {
    private final int[] data;
    private final int start;
    private final int end;
    private final int detectorId;
    private final CyclicBarrier barrier;

    public PhasedDetector(int[] data, int start, int end, int detectorId, CyclicBarrier barrier) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.detectorId = detectorId;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        int phase1End = start + (end - start) / 2;
        long sum1 = calculateSum(start, phase1End);

        System.out.printf("[Детектор-%d] Фаза 1 завершена. Сумма %d%n", detectorId, sum1);

        try {
            barrier.await();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return;
        }

        long sum2 = calculateSum(phase1End, end);

        System.out.printf("[Детектор-%d] Фаза 2 завершена. Сумма %d%n", detectorId, sum2);
    }

    private long calculateSum(int s, int e) {
        long sum = 0;
        for (int i = s; i < e; i++) {
            sum += data[i];
            try { Thread.sleep(1); } catch (InterruptedException ignore) { Thread.currentThread().interrupt(); }
        }
        return sum;
    }
}