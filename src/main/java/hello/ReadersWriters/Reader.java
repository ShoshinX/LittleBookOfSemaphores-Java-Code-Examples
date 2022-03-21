package hello.ReadersWriters;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Reader implements Runnable {

  private final AtomicInteger readers;
  private final Semaphore mutex;
  private final Semaphore isEmpty;
  private final String name;
  private final AtomicInteger sharedItem;

  public Reader(
      AtomicInteger readers,
      Semaphore mutex,
      Semaphore isEmpty,
      String name,
      AtomicInteger sharedItem) {
    this.readers = readers;
    this.mutex = mutex;
    this.isEmpty = isEmpty;
    this.name = name;
    this.sharedItem = sharedItem;
  }

  public void run() {
    try {
      mutex.acquire();
      readers.incrementAndGet();
      if (readers.get() == 1) {
        isEmpty.acquire();
      }
      mutex.release();
      System.out.println("Reader " + name + ": reading " + sharedItem);

      mutex.acquire();
      readers.decrementAndGet();
      if (readers.get() == 0) {
        isEmpty.release();
      }
      mutex.release();
    } catch (InterruptedException e) {
    }
  }
}
