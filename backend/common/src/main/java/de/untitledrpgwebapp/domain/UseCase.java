package de.untitledrpgwebapp.domain;

public interface UseCase<I, O> {

  /**
   * Executes the specific input, on the given in- and output.
   *
   * @param input
   *     the input.
   *
   * @return the output.
   */
  O execute(I input);
}
