package ru.serdyuk;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import ru.serdyuk.schedulers.DefaultScheduler;
import ru.serdyuk.schedulers.Scheduler;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;

@Slf4j
public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        final Random random = new Random(5);
        final Scheduler scheduler = new DefaultScheduler(Executors.newFixedThreadPool(5));
        scheduler.run();

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            int delayTimeInSeconds = random.nextInt(3);

            scheduler.addTask(new ImmutablePair<>(LocalDateTime.now().plusSeconds(delayTimeInSeconds), () -> {
                log.debug("executed taskId: {},  delayTimeInSeconds: {}", taskId, delayTimeInSeconds);
                return null;
            }));
        }

        log.debug("all task added");
        log.debug("current size {}", scheduler.tasksQueueSize());
    }
}
