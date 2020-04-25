package de.untitledrpgwebapp.common.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for ThreadLocalContext unit.")
class ThreadLocalContextTest {

  @Test
  @DisplayName("Should get the same instance when get is called multiple times.")
  void shouldGetSameInstance() {
    // GIVEN: nothing

    // WHEN
    ThreadLocalContext contextOne = ThreadLocalContext.get();
    ThreadLocalContext contextTwo = ThreadLocalContext.get();

    // THEN
    assertThat(contextOne, sameInstance(contextTwo));
  }
}