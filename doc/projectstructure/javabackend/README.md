#### Java Backend

##### Module layout

For build- and dependency management, we use Maven `>=3.6.2`. A corresponding wrapper is included in 
te root project.

The backend implementation can be found in the `backend` folder. Each sub-folder (sub-module) 
describes a domain, e.g `user` or `language`. Furthermore, there are three technical sub-modules:
 
- `common` as a module with common interfaces, base classes ...
- `deployments` for the actual application starters (read: [Spring Boot][springBoot], 
  [quarkus][quarkus], ...).
- `testaggregation` as module that references every other module, and aggregates all unit test 
  coverage reports.

Each domain module is then split in two sub-modules:

- [`api` (Application programming interface)][wikiApi] to define the public API of this module. 
  In particular, input- and output data types should be located here.
- `impls` holding all concrete implementations of that module, again as sub-modules.

Each concrete implementation is then again compromised of one or two sub-modules:

- `impl` for the actual implementation
- [`spi` (Service provider interface)][wikiSpi] for services that the implementation requires.

The `spi` module is optional and can be skipped if the implementation does not use externally 
implemented services.

#### Example: `language` domain

    untitled-rpg-webapp
    \- backend
       |- language
       |  |- api
       |  \- impls
       |     |- localstore
       |     |  |- impl
       |     |  \- spi
       |     \- quarkusrestclient
       |        \- impl
       . 
       .
       .

##### Creating a new module

To create a new module, we first create a new folder for the module. Then we create a `pom.xml` 
within the newly created folder. A [template `pom.xml`][templatePom] is provided with this project. 
Next, we add the new module as submodule of its direct parent by adding it to the 
`<modules>...</modules>`-block. Finally, if the module is going to contain source code, we add
it to the [pom of `testaggretagion`][testaggregationPom]. The last step is important for the 
aggregation of test results.

##### Default dependencies provided

All modules in the module hierarchy inherit the following modules and plugins:

- mapstruct and Lombok, both the API and the annotation processors
- JUnit5, Mockito and Hamcrest

##### Building, deploying and local testing

The backend can be built by running `mnvw install` form the root directory. If you have docker 
installed, you can build and deploy the application by calling

    ./localdeployment/deploy.[cmd|sh]
If you deploy for the first time without having executed `mvnw clean install`, please set the 
variable `BUILD_PROJECT` to `true`. The application will be deployed through `docker-compose`.
Within the deployment group is a postgres database server as dbms provider, as well as a keycloak 
server as oidc provider. The actual deployment consists of the following apps:

- `dbms-service`: the postgres-server, reachable at `http://localhost:15432`
- `oidc-service`: the keycloak-server, reachable at `http://localhost:8090/auth`
- `language-service`: reachable at `http://localhost:8080/languages`
- `user-service`: reachable at `http://localhost:8080/users` 

[api]: https://en.wikipedia.org/wiki/Application_programming_interface
[springBoot]: https://spring.io/projects/spring-boot
[quarkus]: https://quarkus.io/
[spi]: https://en.wikipedia.org/wiki/Service_provider_interface
[templatePom]: ../../../templates/pom.xml.template
[testaggregationPom]: ../../../backend/testaggregation/pom.xml