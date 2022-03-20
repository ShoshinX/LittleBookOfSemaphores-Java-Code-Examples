package hello.ProducerConsumer;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable {
  private final Semaphore mutex;
  private final Semaphore items;
  private final Semaphore spaces;
  private final ArrayList<Integer> buffer;

  public Producer(Semaphore mutex, Semaphore items, Semaphore spaces, ArrayList<Integer> buffer) {
    this.mutex = mutex;
    this.items = items;
    this.spaces = spaces;
    this.buffer = buffer;
  }

  public void run() {
    try {
      for (int i = 0; i < 5; i++) {
        spaces.acquire();
        mutex.acquire();
        System.out.println("Producer produces " + i);
        buffer.add(i);
        items.release();
        mutex.release();
      }

      for (int i = 0; i < 5; i++) {
        spaces.acquire();
        mutex.acquire();
        System.out.println("Producer produces " + i);
        buffer.add(i);
        items.release();
        mutex.release();
        Thread.sleep(250, 0);
      }
    } catch (InterruptedException e) {
    }
  }
}
