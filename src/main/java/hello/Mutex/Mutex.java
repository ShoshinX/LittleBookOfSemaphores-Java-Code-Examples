package hello.Mutex;

import java.util.concurrent.Semaphore;

public class Mutex {
  // This variable has to be static because Integer is an immutable class. In addition, static
  // allows us to ehsure that this field variable belongs to the class instead of the object
  // instance.
  private static Integer count = 1;

  public static void start() {

    Semaphore countMutex = new Semaphore(1);

    Thread threadA = new Thread(new ThreadA(count, countMutex, "A"));
    Thread threadB = new Thread(new ThreadA(count, countMutex, "B"));

    System.out.println("---Mutex example start---");

    threadA.start();
    threadB.start();

    try {
      threadA.join();
      threadB.join();
    } catch (InterruptedException e) {
      System.out.println("Received Interrupted Exception on thread A or thread B");
    }

    System.out.println("---Mutex example finish---");
  }
}
