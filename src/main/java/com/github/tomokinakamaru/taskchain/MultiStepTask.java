package com.github.tomokinakamaru.taskchain;

import java.lang.reflect.Method;

public abstract class MultiStepTask extends Task {

  private int step = 0;

  @Override
  protected final void main() throws Exception {
    step += 1;
    Method method = findMainN();
    if (method != null) {
      method.invoke(this);
      submit(this);
    }
  }

  private Method findMainN() {
    try {
      return getClass().getMethod("main" + step);
    } catch (NoSuchMethodException e) {
      return null;
    }
  }
}
