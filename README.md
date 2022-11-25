# Getting Started


## Brain fucked notes

Request -> ApplicationService -(> Validator) -> Command -> CommandHandler (->Validator)  -> DomainService -> DommainCommand -> DomainCommandHandler (-> Validator) -> Aggregate (-> Validator)


## TODO
[] Repository erstellen, was mit AggregateRoot umgehen kann
    * [] Ticket und TicketPosition werden immer als AggregateRoot behandelt
    * [] AggregateRoots können mit einem Dto/AggregateCommand oder einfach einem Daten Objekt umgehen
    * [] Repository erzeugt die nächste Identity -> nextIdentity()?

### Reference Documentation
Meine persönlichen Referenzen
[Beispiel cqrs und ddd von Michael Schnell](https://github.com/fuinorg/ddd-cqrs-4-java-example/tree/master/spring-boot)

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#using.devtools)

