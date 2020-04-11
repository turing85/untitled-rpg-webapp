package de.untitledrpgwebapp.impl.quarkus.configuration.logging;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BalancedParenthesisParameters {
  private int numberOfUnclosedParenthesis = 0;
  private int numberOfUnclosedBrackets = 0;
  private int numberOfUnclosedBraces = 0;

  void incrementNumberOfUnclosedParenthesis() {
    numberOfUnclosedParenthesis += 1;
  }

  void decrementNumberOfUnclosedParenthesis() {
    numberOfUnclosedParenthesis -= 1;
  }

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
    return numberOfUnclosedParenthesis == 0
        && numberOfUnclosedBrackets == 0
        && numberOfUnclosedBraces == 0;
  }
}
