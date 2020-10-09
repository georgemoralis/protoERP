/*
 * copyright 2013-2020
 * codebb.gr
 * ProtoERP - Open source invocing program
 * info@codebb.gr
 */
/*
 * Changelog
 * =========
 * 09/10/2020 (georgemoralis) - Initial
 */
package gr.codebb.lib.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;

/**
 * General JavaFX utilities
 *
 * @author hendrikebbers http://www.guigarage.com/2013/01/invokeandwait-for-javafx/
 */
public class ThreadUtil {

  /**
   * Simple helper class.
   *
   * @author hendrikebbers
   */
  private static class ThrowableWrapper {
    Throwable t;
  }

  /**
   * Invokes a Runnable in JFX Thread and waits while it's finished. Like
   * SwingUtilities.invokeAndWait does for EDT.
   *
   * @param run The Runnable that has to be called on JFX thread.
   * @throws InterruptedException f the execution is interrupted.
   * @throws ExecutionException If a exception is occurred in the run method of the Runnable
   */
  public static void runAndWait(final Runnable run)
      throws InterruptedException, ExecutionException {
    if (Platform.isFxApplicationThread()) {
      try {
        run.run();
      } catch (Exception e) {
        throw new ExecutionException(e);
      }
    } else {
      final Lock lock = new ReentrantLock();
      final Condition condition = lock.newCondition();
      final ThrowableWrapper throwableWrapper = new ThrowableWrapper();
      lock.lock();
      try {
        Platform.runLater(
            () -> {
              lock.lock();
              try {
                run.run();
              } catch (Throwable e) {
                throwableWrapper.t = e;
              } finally {
                try {
                  condition.signal();
                } finally {
                  lock.unlock();
                }
              }
            });
        condition.await();
        if (throwableWrapper.t != null) {
          throw new ExecutionException(throwableWrapper.t);
        }
      } finally {
        lock.unlock();
      }
    }
  }
}
