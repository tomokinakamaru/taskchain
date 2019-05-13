package com.github.tomokinakamaru.taskchain;

final class SleepThread extends Thread {

  @Override
  public void run() {
    try {
      Thread.sleep(Long.MAX_VALUE);
    } catch (InterruptedException ignored) {
    }
  }
}
