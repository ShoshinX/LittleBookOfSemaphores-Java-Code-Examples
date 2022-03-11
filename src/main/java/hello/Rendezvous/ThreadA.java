package hello.Rendezvous;

import java.util.concurrent.Semaphore;

public class ThreadA implements Runnable {
  private Semaphore threadA_sem;
  private Semaphore threadB_sem;
  private String thread_name;

  public ThreadA(Semaphore threadA_sem, Semaphore threadB_sem, String thread_name) {
    this.threadA_sem = threadA_sem;
    this.threadB_sem = threadB_sem;
    this.thread_name = thread_name;
  }

  public void run() {

    System.out.println(thread_name + ": I am the fst statement");
    threadA_sem.release();
    try {
      threadB_sem.acquire();
    } catch (InterruptedException e) {
      return;
    }
    System.out.println(thread_name + ": I am the snd statement");
  }
}
