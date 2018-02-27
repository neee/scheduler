package ru.serdyuk.schedulers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import ru.serdyuk.executors.TaskExecutor;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

import static java.util.Comparator.comparing;

/**
 * Created by Sergey Serdyuk on 22/02/2018.
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultScheduler implements Scheduler {

    private final ExecutorService executorService;
    private PriorityBlockingQueue<Pair<LocalDateTime, Callable>> queue = new PriorityBlockingQueue<>(100, comparing(Pair::getKey));;
    private volatile long taskCount;

    @Override
    public void addTask(Pair<LocalDateTime, Callable> task) {
        queue.add(task);
        taskCount++;
        log.debug("total tasks: {}", taskCount);
    }

    @Override
    public int tasksQueueSize() {
        return queue.size();
    }

    @Override
    public void run() {
        final TaskExecutor taskExecutor = new TaskExecutor(queue, executorService);
        final Thread threadTaskExecutor = new Thread(taskExecutor);
        threadTaskExecutor.start();
    }
}