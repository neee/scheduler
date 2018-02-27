package ru.serdyuk.schedulers;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;


public class DefaultSchedulerTest {

    @Test
    public void addTask() {
        final Random random = new Random(5);
        final Scheduler scheduler = new DefaultScheduler(null);

        for (int i = 0; i < 1000; i++) {
            scheduler.addTask(new ImmutablePair<>(LocalDateTime.now().plusSeconds(random.nextInt(3)), null));
            assert scheduler.tasksQueueSize() == i + 1;
        }
    }
}