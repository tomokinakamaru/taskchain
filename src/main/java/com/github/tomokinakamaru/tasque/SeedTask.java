package com.github.tomokinakamaru.tasque;

public abstract class SeedTask extends Task {

  public final void start() throws TaskChainException {
    executor = new Executor(getNumberOfThreads());
    executor.start(this);
  }

  public int getNumberOfThreads() {
    return Runtime.getRuntime().availableProcessors() - 1;
  }
}
