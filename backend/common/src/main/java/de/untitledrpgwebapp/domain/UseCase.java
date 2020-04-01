package de.untitledrpgwebapp.domain;

import java.util.function.Function;

public interface UseCase<R, S> extends Function<R, S> {

  /**
   * Executes the specific request, on the given in- and output.
   *
   * @param request
   *     the request.
   *
   * @return the output.
   */
  S execute(R request);

  @Override
  default S apply(R response) {
    return execute(response);
  }
}
