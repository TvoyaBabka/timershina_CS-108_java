import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final int ARRAY_SIZE = 100000;
    private static final int NUM_SEGMENTS = 20;
    private static final int SEGMENT_SIZE = ARRAY_SIZE / NUM_SEGMENTS;

    private static int[] DATA_ARRAY;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        DATA_ARRAY = generateDataArray(ARRAY_SIZE);

        System.out.println("--- Практическая работа №8: Сеть кибер-детекторов (Этапы 1-7) ---");

        long startTime;
        long timeManual, timeExecutor, timeCompletion;

        System.out.println("\n=== Этап 1 & 2: Ручные потоки и синхронизация (AtomicLong) ===");
        startTime = System.currentTimeMillis();
        long globalSumManual = runManualThreads();
        timeManual = System.currentTimeMillis() - startTime;

        System.out.printf("[Центральный ИИ] Общая сумма данных = %d%n", globalSumManual);
        System.out.printf("[ИИ-Аналитик] Время (ручные потоки): %d мс%n", timeManual);

        System.out.println("\n=== Этап 3 & 4: ExecutorService и Callable (Future.get()) ===");
        startTime = System.currentTimeMillis();
        Result totalResultExecutor = runExecutorService();
        timeExecutor = System.currentTimeMillis() - startTime;

        System.out.printf("[Центральный ИИ] Общая сумма = %d%n", totalResultExecutor.getSum());
        System.out.printf("[Центральный ИИ] Найдено аномалий = %d%n", totalResultExecutor.getAnomalies());
        System.out.printf("[ИИ-Аналитик] Время (ExecutorService): %d мс%n", timeExecutor);

        System.out.println("\n=== Этап 5: Completion Service (Динамическая обработка) ===");
        startTime = System.currentTimeMillis();
        Result totalResultCompletion = runCompletionService();
        timeCompletion = System.currentTimeMillis() - startTime;

        System.out.printf("[Центральный ИИ] Итоговая сумма = %d%n", totalResultCompletion.getSum());
        System.out.printf("[Центральный ИИ] Общие аномалии = %d%n", totalResultCompletion.getAnomalies());
        System.out.printf("[ИИ-Аналитик] Время (Completion Service): %d мс%n", timeCompletion);

        System.out.println("\n=== Этап 6: Координация фаз (CyclicBarrier) ===");
        runCyclicBarrier();

        System.out.println("\n=== Итоговый отчёт (Этап 7) ===");
        System.out.println("[ИИ-Аналитик] Время выполнения:");
        System.out.printf("- Ручные потоки: %d мс%n", timeManual);
        System.out.printf("- ExecutorService: %d мс%n", timeExecutor);
        System.out.printf("- CompletionService: %d мс%n", timeCompletion);
    }

    private static int[] generateDataArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = 1 + random.nextInt(10000);
        }
        return array;
    }

    private static long runManualThreads() throws InterruptedException {
        AtomicLong globalSum = new AtomicLong(0);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            final int start = i * SEGMENT_SIZE;
            final int end = start + SEGMENT_SIZE;
            final int detectorId = i + 1;

            Thread t = new Thread(() -> {
                long segmentSum = 0;
                for (int j = start; j < end; j++) {
                    segmentSum += DATA_ARRAY[j];
                }

                globalSum.addAndGet(segmentSum);

                System.out.printf("[Детектор-#%d] Сектор %d-%d просканирован. Сумма = %d%n",
                        detectorId, start, end - 1, segmentSum);
            });

            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return globalSum.get();
    }

    private static Result runExecutorService() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_SEGMENTS);
        List<Future<Result>> futures = new ArrayList<>();

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            int start = i * SEGMENT_SIZE;
            int end = start + SEGMENT_SIZE;
            int detectorId = i + 1;

            Callable<Result> task = new DetectorTask(DATA_ARRAY, start, end, detectorId);
            Future<Result> future = executor.submit(task);
            futures.add(future);
        }

        long totalSum = 0;
        int totalAnomalies = 0;

        for (Future<Result> future : futures) {
            Result result = future.get();
            totalSum += result.getSum();
            totalAnomalies += result.getAnomalies();

            System.out.printf("[Детектор-#%d] Сектор %d-%d завершён. Сумма = %d, Аномалий = %d%n",
                    result.getDetectorId(), result.getStart(), result.getEnd() - 1,
                    result.getSum(), result.getAnomalies());
        }

        executor.shutdown();
        return new Result(totalSum, totalAnomalies, 0, 0, 0);
    }

    private static Result runCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_SEGMENTS);
        CompletionService<Result> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            int start = i * SEGMENT_SIZE;
            int end = start + SEGMENT_SIZE;
            int detectorId = i + 1;

            completionService.submit(new DetectorTask(DATA_ARRAY, start, end, detectorId));
        }

        long totalSum = 0;
        int totalAnomalies = 0;

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            Future<Result> future = completionService.take();
            Result result = future.get();

            totalSum += result.getSum();
            totalAnomalies += result.getAnomalies();

            System.out.printf("[Центральный ИИ] Получен отчёт от %s%n",
                    result.toString());
        }

        executor.shutdown();
        return new Result(totalSum, totalAnomalies, 0, 0, 0);
    }

    private static void runCyclicBarrier() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_SEGMENTS);

        CyclicBarrier barrier = new CyclicBarrier(NUM_SEGMENTS, () -> {
            System.out.println("[ИИ-координатор] Все детекторы завершили фазу 1. Переход к фазе 2.");
        });

        for (int i = 0; i < NUM_SEGMENTS; i++) {
            int start = i * SEGMENT_SIZE;
            int end = start + SEGMENT_SIZE;
            int detectorId = i + 1;

            executor.submit(new PhasedDetector(DATA_ARRAY, start, end, detectorId, barrier));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}