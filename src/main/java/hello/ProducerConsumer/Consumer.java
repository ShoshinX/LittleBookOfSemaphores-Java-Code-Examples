package hello.ProducerConsumer;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable {

  private final Semaphore mutex;
  private final Semaphore items;
  private final Semaphore spaces;
  private final ArrayList<Integer> buffer;

  public Consumer(Semaphore mutex, Semaphore items, Semaphore spaces, ArrayList<Integer> buffer) {
    this.mutex = mutex;
    this.items = items;
    this.spaces = spaces;
    this.buffer = buffer;
  }

  public void run() {
    try {
      for (int i = 0; i < 10; i++) {
        items.acquire();
        mutex.acquire();
        System.out.println("Consumer consumes " + buffer.remove(buffer.size() - 1));
        mutex.release();
        spaces.release();
      }
    } catch (InterruptedException e) {
    }
  }
}
