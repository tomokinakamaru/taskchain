package com.github.tomokinakamaru.tasque;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

final class Executor {

  private final Queue<Task> waitingTasks = new LinkedList<>();

  private final Set<Task> activeTasks = new HashSet<>();

  private final ReentrantLock lock = new ReentrantLock(true);

  private final SleepThread sleepThread = new SleepThread();

  private final int numberOfThreads;

  private final List<Exception> thrownExceptions = new ArrayList<>();

  Executor(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
  }

  final void start(Task task) throws TaskChainException {
    sleepThread.start();

    submit(task);
    invokeNext();

    try {
      sleepThread.join();
    } catch (InterruptedException ignored) {
    }

    if (!thrownExceptions.isEmpty()) {
      throw new TaskChainException(thrownExceptions);
    }
  }

  void submit(Task task) {
    lock.lock();
    waitingTasks.add(task);
    invokeNext();
    lock.unlock();
  }

  private void invokeNext() {
    lock.lock();
    if (activeTasks.size() < numberOfThreads && 0 < waitingTasks.size()) {
      Task task = waitingTasks.remove();
      activeTasks.add(task);
      new Thread(task::run).start();
    }
    lock.unlock();
  }

  void onTaskEnd(Task task) {
    lock.lock();
    activeTasks.remove(task);
    if (task.exception != null) {
      thrownExceptions.add(task.exception);
    }

    if (waitingTasks.size() == 0) {
      if (activeTasks.size() == 0) {
        sleepThread.interrupt();
      }
    } else {
      invokeNext();
    }
    lock.unlock();
  }
}
