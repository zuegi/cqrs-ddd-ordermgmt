# Getting Started

## TODO
OrderPosition als AggregateRoot erstellen 
Order als AggregateRoot erstellen
Ticket einbinden der Order und OrderPosition

## Brain fucked notes

Request -> ApplicationService -(> Validator) -> Command -> CommandHandler (->Validator)  -> DomainService -> DommainCommand -> DomainCommandHandler (-> Validator) -> Aggregate (-> Validator)


### AggregateRoot
[x] Ein Aggregate ist eine Kompositionseinheit, die transaktionale Konsistenz aufweist.
[x] Ein Aggregate enhält keine direkten Objekte, sondern die AggregateId als Referenz.
[x] Wenn die Ausführung eines Befehls auf einer Aggregatinstanz die Ausführung zusätzlicher Geschäftsregeln auf einem oder mehreren anderen Aggregaten erfordert, verwende eventually consistency.
[ ] Ein AggregateRoot wird immer über einen DomainCommand aufgerufen um Operationen wie Create, Read, Update, Delete auszulösen.
[ ] Ein AggregateRoot hat DomainHandler, welche die Operationen am AggregateRoot durchführt.
[ ] Ein AggregateRoot liefert Entities zurück, welche über den DomainService persistiert werden können.
[ ] Ein AggregateRoot kann auch weitere AggregateRoots enthalten. Operationen in den Child AggregateRoots werden über deren Methoden ausgeführt.


### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.5/maven-plugin/reference/html/#build-image)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.5/reference/htmlsingle/#using.devtools)

