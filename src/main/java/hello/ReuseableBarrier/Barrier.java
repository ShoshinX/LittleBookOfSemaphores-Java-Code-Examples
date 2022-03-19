package hello.ReuseableBarrier;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Barrier {
  private final int n;
  private static int count = 0;
  private final Semaphore mutex;
  private final Semaphore turnstile1;
  private final Semaphore turnstile2;

  public Barrier(int n) {
    this.n = n;
    this.mutex = new Semaphore(1);
    this.turnstile1 = new Semaphore(0);
    this.turnstile2 = new Semaphore(0);
  }

  private void phase1() {
    try {
      mutex.acquire();
      count += 1;
      if (count == n) {
        turnstile1.release(n);
      }
    } catch (InterruptedException e) {
    }
    mutex.release();
    try {
      turnstile1.acquire();
    } catch (InterruptedException e) {
    }
  }

  private void phase2() {
    try {
      mutex.acquire();
      count -= 1;
      if (count == 0) {
        turnstile2.release(n);
      }
    } catch (InterruptedException e) {
    }
    mutex.release();

    try {
      turnstile2.acquire();
    } catch (InterruptedException e) {
    }
  }

  public void acquire() {
    phase1();
    phase2();
  }

  public static void test() {
    ArrayList<Thread> threads = new ArrayList<>();
    final Barrier barrier = new Barrier(10);
    System.out.println("--- Barrier Object start ---");
    for (int i = 0; i < 10; i++) {
      threads.add(
          new Thread(
              () -> {
                System.out.println("thread has reached a barrier");
                barrier.acquire();
                System.out.println("thread has exited a barrier");
              }));
      threads.get(i).start();
    }
    try {
      for (int i = 0; i < 10; i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
    }
    threads.clear();
    for (int i = 0; i < 10; i++) {
      threads.add(
          new Thread(
              () -> {
                System.out.println("thread has reached a barrier");
                barrier.acquire();
                System.out.println("thread has exited a barrier");
              }));
      threads.get(i).start();
    }
    try {
      for (int i = 0; i < 10; i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
    }
    System.out.println("--- Barrier Object end ---");
  }
}
