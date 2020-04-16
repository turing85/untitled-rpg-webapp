package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalancedParenthesisParameters {
  private int numberOfUnclosedBrackets = 0;
  private int numberOfUnclosedBraces = 0;

  void incrementNumberOfUnclosedBrackets() {
    numberOfUnclosedBrackets += 1;
  }

  void decrementNumberOfUnclosedBrackets() {
    numberOfUnclosedBrackets -= 1;
  }

  void incrementNumberOfUnclosedBraces() {
    numberOfUnclosedBraces += 1;
  }

  void decrementNumberOfUnclosedBraces() {
    numberOfUnclosedBraces -= 1;
  }

  boolean areAllClosed() {
    return numberOfUnclosedBrackets == 0 && numberOfUnclosedBraces == 0;
  }
}
