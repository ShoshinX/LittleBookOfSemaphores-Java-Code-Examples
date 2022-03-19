package hello.ExclusiveQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Followers implements Runnable {
  private AtomicInteger followers;
  private AtomicInteger leaders;
  private final Semaphore leaderQueue;
  private final Semaphore followerQueue;
  private final Semaphore mutex;
  private final Semaphore rendezvous;
  private final String name;

  public Followers(
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
      if (leaders.get() > 0) {
        leaders.decrementAndGet();
        leaderQueue.release();
      } else {
        followers.incrementAndGet();
        mutex.release();
        followerQueue.acquire();
      }

    } catch (InterruptedException e) {
    }

    System.out.println("Follower " + name + ": I'm dancing");
    rendezvous.release();
  }
}
