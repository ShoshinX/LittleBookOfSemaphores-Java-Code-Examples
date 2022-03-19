package hello.ExclusiveQueue;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ExclusiveQueue {

  public static void test() {
    Semaphore mutex = new Semaphore(1);
    Semaphore leaderQueue = new Semaphore(0);
    Semaphore followerQueue = new Semaphore(0);
    Semaphore rendezvous = new Semaphore(0);

    ArrayList<Thread> leadersList = new ArrayList<>();
    ArrayList<Thread> followersList = new ArrayList<>();

    final int totalPairs = 5;
    AtomicInteger leaders = new AtomicInteger(0);
    AtomicInteger followers = new AtomicInteger(0);

    for (int i = 0; i < totalPairs; i++) {
      leadersList.add(
          new Thread(
              new Leaders(
                  leaders, followers, leaderQueue, followerQueue, mutex, rendezvous, "" + i)));
      followersList.add(
          new Thread(
              new Followers(
                  leaders, followers, leaderQueue, followerQueue, mutex, rendezvous, "" + i)));
      leadersList.get(i).start();
      followersList.get(i).start();
    }
    try {
      for (int i = 0; i < totalPairs; i++) {
        leadersList.get(i).join();
        followersList.get(i).join();
      }

    } catch (InterruptedException e) {
    }
  }
}
