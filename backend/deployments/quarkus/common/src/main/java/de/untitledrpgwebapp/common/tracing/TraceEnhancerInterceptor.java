package de.untitledrpgwebapp.common.tracing;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.common.configuration.ThreadLocalContext;
import io.opentracing.Tracer;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.opentracing.Traced;

@Traced
@Interceptor
@AllArgsConstructor
public class TraceEnhancerInterceptor {

  private final Tracer tracer;

  /**
   * Adds the correlation id stored in {@link ThreadLocalContext} to the current span.
   *
   * @param context
   *     the invocation context of the method.
   *
   * @return the result of the invocation.
   *
   * @throws Exception
   *     if an exception occurs.
   */
  @AroundInvoke
  public Object wrap(InvocationContext context) throws Exception {
    tracer.activeSpan().setTag(
        StaticConfig.CORRELATION_ID_HEADER_KEY.toLowerCase(),
        ThreadLocalContext.get().getCorrelationId());
    return context.proceed();
  }
}
