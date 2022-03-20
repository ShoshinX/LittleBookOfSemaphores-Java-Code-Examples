package hello.ProducerConsumer;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ProducerConsumerTester {
  public static void start() {
    final int length = 4;
    ArrayList<Integer> sharedList = new ArrayList<>(length);
    Semaphore mutex = new Semaphore(1);
    Semaphore items = new Semaphore(0);
    Semaphore spaces = new Semaphore(length);

    Thread producer = new Thread(new Producer(mutex, items, spaces, sharedList));
    Thread consumer = new Thread(new Consumer(mutex, items, spaces, sharedList));

    System.out.println("--- Producer Consumer start ---");

    producer.start();
    consumer.start();

    try {
      producer.join();
      consumer.join();
    } catch (InterruptedException e) {
    }
    System.out.println("--- Producer Consumer end ---");
  }
}
