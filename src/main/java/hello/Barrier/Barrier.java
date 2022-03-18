package hello.Barrier;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Barrier implements Runnable {
  private final int total_threads;
  private static int count = 0;
  private final Semaphore mutex;
  private final Semaphore barrier;
  private final String name;

  public static void start() {
    ArrayList<Thread> threads = new ArrayList<>();
    Semaphore mutex = new Semaphore(1);
    Semaphore barrier = new Semaphore(0);
    final int total_threads = 10;

    System.out.println("---Barrier example start---");
    for (int i = 0; i < total_threads; i++) {
      threads.add(new Thread(new Barrier(barrier, mutex, "" + i, total_threads)));
      threads.get(i).start();
    }

    try {
      for (int i = 0; i < total_threads; i++) {
        threads.get(i).join();
      }

    } catch (InterruptedException e) {
    }
    System.out.println("---Barrier example finish---");
  }

  public Barrier(Semaphore barrier, Semaphore mutex, String name, int total_threads) {
    this.barrier = barrier;
    this.name = name;
    this.mutex = mutex;
    this.total_threads = total_threads;
  }

  public void run() {

    try {
      mutex.acquire();
      count = count + 1;
    } catch (InterruptedException e) {
    }
    mutex.release();

    if (count == total_threads) {
      barrier.release();
    }

    System.out.println("thread " + name + " reached barrier");
    try {
      barrier.acquire();
    } catch (InterruptedException e) {
    }
    System.out.println("thread " + name + " exited barrier");
    barrier.release();
  }
}
