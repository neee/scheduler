package ru.serdyuk.schedulers;

import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;

public interface Scheduler {

    void addTask(Pair<LocalDateTime, Callable> task);

    int tasksQueueSize();

    void run();
}
