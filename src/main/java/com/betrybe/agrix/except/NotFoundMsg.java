package com.betrybe.agrix.except;

/**
 * Javadoc.
 */
public class NotFoundMsg extends RuntimeException {
  public NotFoundMsg(String message) {
    super(message);
  }
}
