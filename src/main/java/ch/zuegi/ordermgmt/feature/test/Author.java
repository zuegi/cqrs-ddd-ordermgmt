package ch.zuegi.ordermgmt.feature.test;

import ch.zuegi.ordermgmt.feature.test.shared.AggregateRoot;
import ch.zuegi.ordermgmt.feature.test.shared.DomainCommandHandler;
import ch.zuegi.ordermgmt.shared.Command;

import java.util.HashMap;
import java.util.Map;

public class Author extends AggregateRoot<Author, AuthorId> {

    private AuthorEntity authorEntity;

    protected Map<Class<? extends Command>, AuthorDomainCommandHandler> behaviourMap;

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
        behaviourMap.put(CreateArticle.class, new AuthorDomainCommandHandler(this));
    }

    @Override
    public Map<Class<? extends Command>, ArtikelDomainCommandHandler> getBehaviourMap() {
        return null;
    }

    public void addEntity(AuthorEntity entity) {
        this.authorEntity = entity;
    }
}
