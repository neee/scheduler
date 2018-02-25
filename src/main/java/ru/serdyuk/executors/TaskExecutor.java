package ru.serdyuk.executors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Optional.ofNullable;

/**
 * Created by Sergey Serdyuk on 24/02/2018.
 */
@Slf4j
@RequiredArgsConstructor
public class TaskExecutor implements Runnable {

    private static final int TIME_TO_WAIT_NEW_TASK = 10000;
    private final BlockingQueue<Pair<LocalDateTime, Callable>> queue;
    private final ExecutorService executorService;

    @Override
    @SneakyThrows
    public void run() {

        log.debug("start TaskExecutor");

        while (!Thread.interrupted()) {

            Optional<Pair<LocalDateTime, Callable>> headElement = ofNullable(queue.peek());

            if (headElement.isPresent()) {
                if (now().compareTo(headElement.get().getKey()) >= 0) {
                    executorService.submit(queue.poll().getRight());
                } else {
                    long millisToWait = now().until(headElement.get().getLeft(), MILLIS);
                    log.debug("millis to wait: {}", millisToWait);
                    Thread.sleep(millisToWait);
                }
            } else {
                log.debug("wait TaskExecutor");
                Thread.sleep(TIME_TO_WAIT_NEW_TASK);
            }
        }
    }
}
