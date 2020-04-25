package de.untitledrpgwebapp.common.tracing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.untitledrpgwebapp.common.configuration.StaticConfig;
import de.untitledrpgwebapp.common.configuration.ThreadLocalContext;
import io.opentracing.Span;
import io.opentracing.Tracer;
import javax.interceptor.InvocationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for TraceEnhancerInterceptor unit.")
class TraceEnhancerInterceptorTest {

  @Test
  @DisplayName("Should add the correlation id from the ThreadLocalContext to the current span and "
      + "return the invocation result when wrap is called.")
  void shouldAddCorrelationIdToActiveSpanAndProceed() throws Exception{
    // GIVEN
    String correlationId = "correlationId";
    Span activeSpan = mock(Span.class);
    Tracer tracer = mock(Tracer.class);
    when(tracer.activeSpan()).thenReturn(activeSpan);
    ThreadLocalContext.get().setCorrelationId(correlationId);

    Object result = new Object();
    InvocationContext context = mock(InvocationContext.class);
    when(context.proceed()).thenReturn(result);

    // WHEN
    Object actual = new TraceEnhancerInterceptor(tracer).wrap(context);

    // THEN
    assertThat(actual, sameInstance(result));

    verify(tracer).activeSpan();
    verify(activeSpan).setTag(StaticConfig.CORRELATION_ID_HEADER_KEY.toLowerCase(), correlationId);
  }

}