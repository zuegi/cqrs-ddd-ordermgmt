package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;

public class AuthorDomainCommandHandler implements DomainCommandHandler<Author, CreateAuthor> {

    Author author;
    public AuthorDomainCommandHandler(Author author) {
        this.author = author;
    }

    @Override
    public void handle(CreateAuthor command) {
        AuthorEntity entity = new AuthorEntity(author.id());

        entity.setVorname(command.getVorname());
        entity.setNachname(command.getNachname());
        this.author.addEntity(entity);

    }
}
