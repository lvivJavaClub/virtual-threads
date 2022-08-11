package virtualthreads;

import jdk.internal.vm.Continuation;
import jdk.internal.vm.ContinuationScope;

public class ContinuationMain {

  public static void main(String[] args) {
    ContinuationScope scope = new ContinuationScope("my");

    Continuation continuation = new Continuation(scope, () -> {
      System.out.println("step 1");
      Continuation.yield(scope);
      System.out.println("step 2");
    });

    System.out.println("Start 1: " + continuation.isDone());
    continuation.run();
    System.out.println("Start 2: " + continuation.isDone());
    continuation.run();
    System.out.println("End: " + continuation.isDone());
  }
}
