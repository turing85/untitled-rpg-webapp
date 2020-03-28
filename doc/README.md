## Design principle

The application is structured with a [Domain-driven design][ddd]. In particular, we want to use
[bounded contexts][boundedContext], whereby:

- Each microservice defines its own bounded context and ubiquitous language. 
- Multiple microservices can create a common bounded context. Here, the least common denominator
  (i.e. the terms common to all microservices build the bounded context of that group). Similarly,
  a group of microservices can form a ubiquitous language over that group of microservices. Both are
  possible, but not necessary.

## [Implementation][implementation]

## [Data model][dataModel]

[ddd]: https://en.wikipedia.org/wiki/Domain-driven_design
[boundedContext]: https://en.wikipedia.org/wiki/Domain-driven_design#Bounded_context
[implementation]: implementation/README.md
[dataModel]: datamodel/README.md