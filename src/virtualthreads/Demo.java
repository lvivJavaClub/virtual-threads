package virtualthreads;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Demo {
  private static final Pattern WORKER_PATTERN = Pattern.compile("(.*-)(\\d+)$");

  public static void main(String[] args) throws InterruptedException {
    Set<String> names = ConcurrentHashMap.newKeySet();
    Thread.sleep(100);

    var start = System.nanoTime();

    List<Thread> threads = IntStream.range(0, 10_000_000)
        .mapToObj(i ->
            Thread.ofVirtual()
                .unstarted(() -> {

                  String thread = String.valueOf(Thread.currentThread());
                  String worker = getWorker(thread);
                  names.add(worker);

                })).toList();

    threads.forEach(Thread::start);
    for (Thread thread : threads) {
      thread.join();
    }


    System.out.println("time: " + (System.nanoTime() - start) / 1_000_000_000 + " sec");
    System.out.println("workers: " + names.size());
    Thread.sleep(10000);
  }

  private static String getWorker(String name) {
    Matcher matcher = WORKER_PATTERN.matcher(name);
    if (matcher.find()) {
      return matcher.group(2);
    }
    return "unknown";
  }
}
