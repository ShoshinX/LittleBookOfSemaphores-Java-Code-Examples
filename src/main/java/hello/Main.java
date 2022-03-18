package hello;

import hello.Barrier.Barrier;
import hello.Multiplex.Multiplex;
import hello.Mutex.Mutex;
import hello.Rendezvous.Rendezvous;
import hello.ReuseableBarrier.ReuseableBarrier;
import hello.Signalling.Signalling;

public class Main {
  public static void main(String[] args) {
    Signalling signal = new Signalling();
    signal.run();
    Rendezvous.start();
    Mutex.start();
    Multiplex.start();
    Barrier.start();
    ReuseableBarrier.start();
  }
}
