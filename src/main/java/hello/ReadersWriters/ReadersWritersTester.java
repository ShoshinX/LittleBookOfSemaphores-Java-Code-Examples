package hello.ReadersWriters;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadersWritersTester {
  static AtomicInteger sharedItem = new AtomicInteger(0);

  public static void start() {
    AtomicInteger readers = new AtomicInteger(0);
    Semaphore mutex = new Semaphore(1);
    Semaphore isEmpty = new Semaphore(1);

    ArrayList<Thread> writersList = new ArrayList<>();
    ArrayList<Thread> readersList = new ArrayList<>();

    System.out.println("--- Readers Writers start ---");
    for (int i = 0; i < 5; i++) {
      readersList.add(new Thread(new Reader(readers, mutex, isEmpty, "" + i, sharedItem)));
      readersList.get(i).start();
    }

    for (int i = 0; i < 2; i++) {
      writersList.add(new Thread(new Writer(readers, mutex, isEmpty, i, sharedItem)));
      writersList.get(i).start();
    }
    for (int i = 5; i < 10; i++) {
      readersList.add(new Thread(new Reader(readers, mutex, isEmpty, "" + i, sharedItem)));
      readersList.get(i).start();
    }

    try {
      for (int i = 0; i < 10; i++) {
        readersList.get(i).join();
      }

      for (int i = 0; i < 2; i++) {
        writersList.get(i).join();
      }
    } catch (InterruptedException e) {
    }
    System.out.println("--- Readers Writers end ---");
  }
}
