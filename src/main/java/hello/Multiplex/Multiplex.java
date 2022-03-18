package hello.Multiplex;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Multiplex implements Runnable {
  private final Semaphore multiSem;
  private final String name;

  public static void start() {
    ArrayList<Thread> threads = new ArrayList<>();
    Semaphore multiSem = new Semaphore(7);

    System.out.println("---Multiplex example start---");
    for (int i = 0; i < 10; i++) {
      threads.add(new Thread(new Multiplex(multiSem, "" + i)));
      threads.get(i).start();
    }
    try {
      for (int i = 0; i < 10; i++) {
        threads.get(i).join();
      }
    } catch (InterruptedException e) {
    }
    System.out.println("---Multiplex example finish---");
  }

  public Multiplex(Semaphore multiSem, String name) {
    this.multiSem = multiSem;
    this.name = name;
  }

  public void run() {
    try {
      multiSem.acquire();
      System.out.println(
          "Thread " + name + ": Available permits(" + multiSem.availablePermits() + ")");
    } catch (InterruptedException e) {
    }
    multiSem.release();
  }
}
