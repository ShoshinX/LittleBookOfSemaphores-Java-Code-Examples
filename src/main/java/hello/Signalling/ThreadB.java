package hello.Signalling;

import java.util.concurrent.Semaphore;

public class ThreadB implements Runnable {
  private final Semaphore sem;

  public ThreadB(Semaphore sem) {
    this.sem = sem;
  }

  public void run() {
    try {
      sem.acquire();
    } catch (InterruptedException e) {
      System.out.println("Exception occured while waiting");
    }
    System.out.println("Thread B must come second");
  }
}
