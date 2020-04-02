package de.untitledrpgwebapp.boundary.user.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import de.untitledrpgwebapp.boundary.user.response.UserBuilder;
import de.untitledrpgwebapp.boundary.user.response.UserResponseBuilder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  @Test
  @DisplayName("Should call requestToRequest(...) for each UserBuilder instance")
  void shouldCallRequestToRequestForEachUserBuilder() {
    // GIVEN
    UserBuilder userBuilderOne = mock(UserBuilder.class);
    UserBuilder userBuilderTwo = mock(UserBuilder.class);
    List<UserBuilder> userBuilders = List.of(userBuilderOne, userBuilderTwo);

    UserResponseBuilder userResponseBuilderOne = mock(UserResponseBuilder.class);
    UserResponseBuilder userResponseBuilderTwo = mock(UserResponseBuilder.class);
    List<UserResponseBuilder> expected = List.of(userResponseBuilderOne, userResponseBuilderTwo);

    HashMap<UserBuilder, UserResponseBuilder> mapping = new HashMap<>();
    mapping.put(userBuilderOne, userResponseBuilderOne);
    mapping.put(userBuilderTwo, userResponseBuilderTwo);

    UserMapper sut = mock(UserMapper.class);
    when(sut.requestsToRequests(anyCollection(), any())).thenCallRealMethod();
    when(sut.requestToRequest(any(UserBuilder.class), any()))
        .thenAnswer(invocation -> mapping.get(invocation.getArgument(0, UserBuilder.class)));

    // WHEN
    Collection<UserResponseBuilder> actual =
        sut.requestsToRequests(userBuilders, expected.iterator()::next);

    // THEN
    assertThat(actual, hasSize(2));
    assertThat(actual, containsInAnyOrder(expected.toArray()));

    verify(sut).requestToRequest(same(userBuilderOne), argThat(Objects::nonNull));
    verify(sut).requestToRequest(same(userBuilderTwo), argThat(Objects::nonNull));


  }
}