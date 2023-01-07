package ch.zuegi.ordermgmt.feature.ticket;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Entity;
import lombok.Builder;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

public class TestTest {

    @Test
    void testeMich() {

        TestEvent testEvent = new TestEvent(new TicketId());
        System.out.println(testEvent);

    }

    @ToString
    public class TestEvent extends InterfaceEvent<TestEvent, TicketId> {


        protected TestEvent(TicketId aggregateId) {
            super(aggregateId);
        }

        @Override
        public TicketId id() {
            return aggregateId;
        }
    }

    public abstract class InterfaceEvent<E, ID extends Serializable> extends Entity<ID> {
        protected InterfaceEvent(ID aggregateId) {
            super(aggregateId);
        }

    }
}

