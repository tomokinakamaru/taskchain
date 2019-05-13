package com.github.tomokinakamaru.tasque;

import java.util.ArrayList;
import java.util.List;

public final class TaskChainException extends Exception {

  private final List<Exception> exceptions;

  TaskChainException(List<Exception> exceptions) {
    super(String.format("%d exceptions(s)", exceptions.size()));
    this.exceptions = exceptions;
  }

  public List<Exception> getExceptions() {
    return new ArrayList<>(exceptions);
  }
}
