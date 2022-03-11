package hello.Signalling;

import java.util.concurrent.Semaphore;

public class Signalling {
  public void run() {
    final int maxNums = 0;
    Semaphore waitSem = new Semaphore(maxNums);
    System.out.println("---Running Signalling example---");

    Thread threadA = (new Thread(new ThreadB(waitSem)));
    Thread threadB = (new Thread(new ThreadA(waitSem)));

    threadB.start();
    threadA.start();

    try {
      threadA.join();
      threadB.join();
    } catch (InterruptedException e) {
      System.out.println("Joining threadA & threadB is interrupted");
    }

    System.out.println("---Finishing Signalling example---");
  }
}
