package hello.ReuseableBarrier;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ReuseableBarrier implements Runnable {
  private final Semaphore mutex;
  private final Semaphore turnstile1;
  private final Semaphore turnstile2;
  private final int n;
  private static int count = 0;
  private final String name;

  public static void start() {
    ArrayList<Thread> threads = new ArrayList<>();
    Semaphore mutex = new Semaphore(1);
    Semaphore turnstile1 = new Semaphore(0);
    Semaphore turnstile2 = new Semaphore(1);
    final int total_threads = 10;

    System.out.println("---Reuseable barrier start---");

    for (int i = 0; i < total_threads; i++) {
      threads.add(
          new Thread(
              new ReuseableBarrier(mutex, turnstile1, turnstile2, count, total_threads, "" + i)));
      threads.get(i).start();
    }

    try {
      for (int i = 0; i < total_threads; i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
    }
    System.out.println("---Reuseable barrier end---");
  }

  public ReuseableBarrier(
      Semaphore mutex,
      Semaphore turnstile1,
      Semaphore turnstile2,
      int count,
      int total_threads,
      String name) {
    this.mutex = mutex;
    this.turnstile1 = turnstile1;
    this.turnstile2 = turnstile2;
    this.n = total_threads;
    this.name = name;
  }

  public void run() {

    try {
      mutex.acquire();
      count = count + 1;
      if (n == count) {
        turnstile1.release(n);
        // turnstile2.acquire();
        // turnstile1.release();
      }
    } catch (InterruptedException e) {
    }
    mutex.release();

    System.out.println("thread " + name + " reached turnstile 1");
    try {
      turnstile1.acquire();
    } catch (InterruptedException e) {
    }
    // turnstile1.release();
    System.out.println("thread " + name + " exited turns tile 1");

    // Critical segment

    try {
      mutex.acquire();
      count = count - 1;
      if (0 == count) {
        turnstile2.release(n);
        // turnstile1.acquire();
        // turnstile2.release();
      }
    } catch (InterruptedException e) {
    }
    mutex.release();
    System.out.println("thread " + name + " reached turnstile 2");
    try {
      turnstile2.acquire();
    } catch (InterruptedException e) {
    }
    // turnstile2.release();
    System.out.println("thread " + name + " exited turns tile 2");
  }
}
