package hello;

import hello.Rendezvous.Rendezvous;
import hello.Signalling.Signalling;

public class Main {
  public static void main(String[] args) {
    Signalling signal = new Signalling();
    signal.run();
    Rendezvous.start();
  }
}
