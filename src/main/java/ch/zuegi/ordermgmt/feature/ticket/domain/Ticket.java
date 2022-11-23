package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAdded;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;


public class Ticket extends AggregateRoot<Ticket, TicketNumber> {

    private final TicketEntity ticketEntity;

    public Ticket(TicketNumber aggregateId) {
        super(aggregateId);

        LocalDateTime now = LocalDateTime.now();
        ticketEntity = new TicketEntity();
        ticketEntity.setTicketNumber(aggregateId);
        ticketEntity.setLocalDateTime(now);
        ticketEntity.setLifeCycleState(TicketLifeCycleState.TICKET_CREATED);

        TicketCreated ticketCreated = TicketCreated.eventOf(aggregateId, now, TicketLifeCycleState.TICKET_CREATED);
        this.validate();
        DomainEventPublisher
                .instance()
                .publish(ticketCreated);

    }


    public Ticket(TicketEntity ticketEntity) {
        super(ticketEntity.getTicketNumber());
        this.ticketEntity = ticketEntity;
//        this.validateEntity();
    }



    @Override
    protected void validate() {
        if (!aggregateId.id.startsWith(aggregateId.getPrefix())) {
            throw new TicketIdStartWithException("TicketId must have a leading \"" +aggregateId.getPrefix() +"\"");
        }

        // TODO Exception
        assert this.ticketEntity != null;
    }

    @Override
    public TicketNumber id() {
        return this.aggregateId;
    }


    public void addTicketPosition(TicketPositionNumber ticketPositionNumber, TradeItemId tradeItemId, BigDecimal menge) {

        TicketPositionEntity ticketPositionEntity = new TicketPositionEntity();
        ticketPositionEntity.setTicketEntity(this.ticketEntity);
        ticketPositionEntity.setTradeItemId(tradeItemId); // FIXME TradeItem als Objekt einfügen oder doch als Ref?
        ticketPositionEntity.setTicketPositionNumber(ticketPositionNumber);
        ticketPositionEntity.setMenge(menge);

        if (CollectionUtils.isEmpty(ticketEntity.getTicketPositionEntitySet())) {
            ticketEntity.setTicketPositionEntitySet(new HashSet<>());
        }
        this.ticketEntity.getTicketPositionEntitySet().add(ticketPositionEntity);

        //add TicketPositionAdded in DomainPublisher
        TicketPositionAdded ticketPositionAdded = TicketPositionAdded.eventOf(ticketPositionEntity.getTicketPositionNumber(), ticketPositionEntity.getTicketEntity().getTicketNumber() ,ticketPositionEntity.getTradeItemId(), ticketPositionEntity.getMenge());
        DomainEventPublisher
                .instance()
                .publish(ticketPositionAdded);

    }

    public void updateStatus(TicketLifeCycleState ticketLifeCycleState) {
        this.ticketEntity.setLifeCycleState(ticketLifeCycleState);
        this.validate();

        TicketLifecycleUpdated ticketUpdated = TicketLifecycleUpdated.eventOf(aggregateId, LocalDateTime.now(), this.ticketEntity.getLifeCycleState());
        DomainEventPublisher
                .instance()
                .publish(ticketUpdated);
    }

    public TicketEntity getTicketEntity() {
        return this.ticketEntity;
    }
}

