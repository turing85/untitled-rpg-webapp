### Implementation

This document serves as a guideline for implementing features for the application.

#### Java

##### Code style

TBA.

##### Java 11 features
We want to be null-safe without much overhead. Thus, we want to return [`Optional`s][optionalApi]
instead of `null` when feasible. Furthermore, for stateless processing, we want to use the 
[Java `stream` api][streamApi].

##### Lombok and mapstruct

We do not want to write boilerplate code. Thus, we want to use [Lombok][lombok] and 
[mapstruct][mapstruct] whenever possible.

##### Clean code and testability

We want to write clean, testable code. While [Test-driven development (TDD)][tdd] is encouraged, it
is not strictly necessary. Testability, however, should be guaranteed. Thus, we do not want to use
static dependencies and we want to use constructor injection instead of field injection.

For tests, we do not want to use mechanisms like [Mockito][mockito]'s `@Mock` and `@InjectMocks`
annotations since those:
- make unit tests slow, and
- hides dependencies.

To guarantee type safety during mocking, we want to use Mockito's 
`when(...).then[Return|Throws](...)` syntax. `doReturn(...).when(...)` is not allowed. 
`doThrow(...).when(...)` is only allowed on methods with `void` return type.

While a unit test coverage of 100% line coverage is achievable, it is not desirable. We want to test
important behavior and continue implementing business logic rather than writing tests to achieve 
100% coverage. 

##### Plugable architecture and separation

While we want to write a web application with a relational database as persistence provider, we do 
not want to limit ourselves to these technologies. Thus, we want to separate those concerns and make
the I/O-channels (web and persistence) plugable.

[lombok]: https://projectlombok.org/
[mapstruct]: https://mapstruct.org/
[optionalApi]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Optional.html
[streamApi]: https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/stream/Stream.html
[tdd]: https://en.wikipedia.org/wiki/Test-driven_development
[mockito]: https://site.mockito.org/