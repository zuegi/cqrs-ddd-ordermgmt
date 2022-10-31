package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.AggregateRoot;
import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;
import ch.zuegi.ordermgmt.shared.Command;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Author extends AggregateRoot<Author, AuthorId> {

    private AuthorEntity authorEntity;

    protected Author(AuthorId aggregateId) {
        super(aggregateId);
    }

    @Override
    public AuthorId id() {
        return this.aggregateId;
    }

    @Override
    public void initBehaviour() {
        if (behaviourMap == null) {
            behaviourMap = new HashMap<>();
        }
        behaviourMap.put(CreateAuthor.class, new AuthorDomainCommandHandler(this));
    }


    public void addEntity(AuthorEntity entity) {
        this.authorEntity = entity;
    }
}
