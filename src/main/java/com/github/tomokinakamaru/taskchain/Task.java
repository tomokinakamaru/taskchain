package com.github.tomokinakamaru.taskchain;

import java.util.Date;

public abstract class Task {

  Exception exception;

  Executor executor;

  protected abstract void main() throws Exception;

  public long getMinDuration() {
    return 0;
  }

  protected final void submit(Task task) {
    task.executor = executor;
    executor.submit(task);
  }

  void run() {
    runMainWithTimeConstraint();
    executor.onTaskEnd(this);
  }

  private void runMainWithTimeConstraint() {
    long startTime = timeNow();
    runMain();
    long duration = timeNow() - startTime;

    if (duration < getMinDuration()) {
      try {
        Thread.sleep(getMinDuration() - duration);
      } catch (InterruptedException ignored) {
      }
    }
  }

  private void runMain() {
    try {
      main();
    } catch (Exception e) {
      exception = e;
    }
  }

  private static long timeNow() {
    return new Date().getTime();
  }
}
