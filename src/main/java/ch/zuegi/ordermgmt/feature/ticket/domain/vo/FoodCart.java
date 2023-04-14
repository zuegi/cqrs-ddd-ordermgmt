package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.feature.ticket.domain.FoodCartCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.FoodCartId;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketIdStartWithException;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FoodCart extends AggregateRoot<FoodCart, FoodCartId> {

    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;
    private boolean confirmed;

    public FoodCart(FoodCartId aggregateID) {
        super(aggregateID);
        validate();
        selectedProducts = new HashMap<>();
        confirmed = false;

        FoodCartCreated foodCartCreated = FoodCartCreated.eventOf(aggregateID, LocalDateTime.now());
        DomainEventPublisher
                .instance()
                .publish(foodCartCreated);
    }

    @Override
    protected void validate() {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
        if (!aggregateId.id.startsWith(aggregateId.getPrefix())) {
            throw new TicketIdStartWithException("TicketId must have a leading \"" +aggregateId.getPrefix() +"\"");
        }
    }

    @Override
    public FoodCartId id() {
        return this.aggregateId;
    }

}
