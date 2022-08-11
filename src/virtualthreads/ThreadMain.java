package virtualthreads;

import java.util.List;
import java.util.stream.IntStream;

public class ThreadMain {

  public static void main(String[] args) throws InterruptedException {
    List<Thread> threads = IntStream.range(0, 10)
        .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
          Object o = i;
          if (i == 0) {
            System.out.println("name: " + Thread.currentThread());
          }

          synchronized (o) {
            try {
              Thread.sleep(100);
            } catch (InterruptedException e) {
              throw new RuntimeException(e);
            }
          }


          if (i == 0) {
            System.out.println("name: " + Thread.currentThread());
          }
        })).toList();

    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }
  }
}
