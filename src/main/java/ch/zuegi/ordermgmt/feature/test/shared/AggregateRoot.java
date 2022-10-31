package ch.zuegi.ordermgmt.feature.test.shared;

import ch.zuegi.ordermgmt.feature.test.ArtikelDomainCommandHandler;
import ch.zuegi.ordermgmt.feature.test.CreateArticle;
import ch.zuegi.ordermgmt.shared.Command;

import java.util.Map;

public abstract class AggregateRoot<E, ID> extends AggregateId<ID> {
    protected AggregateRoot(ID aggregateId) {
        super(aggregateId);
        initBehaviour();
    }

    public abstract void initBehaviour();

    public abstract Map<Class<? extends Command>, ArtikelDomainCommandHandler> getBehaviourMap();


    public void handle(CreateArticle createArticle) {
        DomainCommandHandler commandHandler = this.getCommandHandler(createArticle);
        commandHandler.handle(createArticle);
    }

    private DomainCommandHandler getCommandHandler(CreateArticle createArticle) {
       return getBehaviourMap().get(createArticle.getClass());
    }
}
