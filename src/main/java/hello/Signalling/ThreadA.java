package hello.Signalling;

import java.util.concurrent.Semaphore;

public class ThreadA implements Runnable {
  private final Semaphore sem;

  public ThreadA(Semaphore sem) {
    this.sem = sem;
  }

  public void run() {
    System.out.println("Thread A must come first");
    sem.release();
  }
}
