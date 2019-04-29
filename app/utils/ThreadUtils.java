package utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static ExecutorService execService = Executors.newSingleThreadExecutor();

    public static void asynExecute(Runnable task) {
        execService.execute(task);
    }
}
