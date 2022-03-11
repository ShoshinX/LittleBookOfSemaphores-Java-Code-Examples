package hello.Rendezvous;

import java.util.concurrent.Semaphore;

public class Rendezvous {

  public static void start() {
    Semaphore sem_A = new Semaphore(0);
    Semaphore sem_B = new Semaphore(0);
    Thread A = new Thread(new ThreadA(sem_A, sem_B, "A"));
    Thread B = new Thread(new ThreadB(sem_A, sem_B, "B"));

    System.out.println("---Rendezvous test started---");
    A.start();
    B.start();

    try {
      A.join();
      B.join();
    } catch (InterruptedException e) {

    }
    System.out.println("---Rendezvous test finished---");
  }
}
