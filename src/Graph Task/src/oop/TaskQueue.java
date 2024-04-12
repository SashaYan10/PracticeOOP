package oop;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface Task {
    void execute();
}

class Worker extends Thread {
    private final TaskQueue taskQueue;

    public Worker(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (true) {
            Task task = taskQueue.getNextTask();
            task.execute();
        }
    }
}

public class TaskQueue {
    private final BlockingQueue<Task> queue;

    public TaskQueue() {
        queue = new LinkedBlockingQueue<>();
        startWorkers();
    }

    private void startWorkers() {
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            Worker worker = new Worker(this);
            worker.start();
        }
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public Task getNextTask() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}