package hello.Mutex;

import java.util.concurrent.Semaphore;

public class ThreadA implements Runnable {

  private static Integer count;
  private final Semaphore countMutex;
  private final String name;

  public ThreadA(Integer count, Semaphore countMutex, String name) {
    this.count = count;
    this.countMutex = countMutex;
    this.name = name;
  }

  public synchronized void run() {
    try {
      countMutex.acquire();
      this.count = this.count + 1;
    } catch (InterruptedException e) {
    }
    countMutex.release();

    System.out.println(name + ": " + count);
  }
}
