package hello.ExclusiveQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Leaders implements Runnable {
  private AtomicInteger followers;
  private AtomicInteger leaders;
  private final Semaphore leaderQueue;
  private final Semaphore followerQueue;
  private final Semaphore mutex;
  private final Semaphore rendezvous;
  private final String name;

  public Leaders(
      AtomicInteger followers,
      AtomicInteger leaders,
      Semaphore leaderQueue,
      Semaphore followerQueue,
      Semaphore mutex,
      Semaphore rendezvous,
      String name) {
    this.followers = followers;
    this.leaders = leaders;
    this.leaderQueue = leaderQueue;
    this.followerQueue = followerQueue;
    this.mutex = mutex;
    this.rendezvous = rendezvous;
    this.name = name;
  }

  public void run() {
    try {
      mutex.acquire();
      if (followers.get() > 0) {
        followers.decrementAndGet();
        followerQueue.release();
      } else {
        leaders.incrementAndGet();
        mutex.release();
        leaderQueue.acquire();
      }
    } catch (InterruptedException e) {
    }
    System.out.println("Leader " + name + ": I'm dancing");

    try {
      rendezvous.acquire();
    } catch (InterruptedException e) {
    }

    mutex.release();
  }
}
