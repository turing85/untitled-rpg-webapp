package de.untitledrpgwebapp.common.configuration;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is meant as a singleton for thread-local context information sharing.
 */
@Getter
@Setter
public class ThreadLocalContext {

  private static final ThreadLocal<ThreadLocalContext> THREAD_LOCAL_CONTEXT = new ThreadLocal<>();

  private String correlationId;

  private ThreadLocalContext() {
  }

  /**
   * Gets the {@link ThreadLocalContext}.
   *
   * <p>An instance is created if it does not yet exists. Creation is thread-safe.
   *
   * @return the context for the current threat.
   */
  public static synchronized ThreadLocalContext get() {
    if (Objects.isNull(THREAD_LOCAL_CONTEXT.get())) {
      synchronized (ThreadLocalContext.class) {
        if (Objects.isNull(THREAD_LOCAL_CONTEXT.get())) {
          THREAD_LOCAL_CONTEXT.set(new ThreadLocalContext());
        }
      }
    }
    return THREAD_LOCAL_CONTEXT.get();
  }

  public static void remove() {
    THREAD_LOCAL_CONTEXT.remove();
  }
}
