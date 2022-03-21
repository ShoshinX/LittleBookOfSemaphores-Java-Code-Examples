package hello.ReadersWriters;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Writer implements Runnable {
  private final AtomicInteger readers;
  private final Semaphore mutex;
  private final Semaphore isEmpty;
  private final int name;
  private final AtomicInteger sharedItem;

  public Writer(
      AtomicInteger readers,
      Semaphore mutex,
      Semaphore isEmpty,
      int name,
      AtomicInteger sharedItem) {
    this.readers = readers;
    this.mutex = mutex;
    this.isEmpty = isEmpty;
    this.name = name;
    this.sharedItem = sharedItem;
  }

  public void run() {
    try {
      isEmpty.acquire();
      sharedItem.set(10 * name);
      isEmpty.release();
    } catch (InterruptedException e) {
    }
  }
}
