package ch.zuegi.ordermgmt.feature.ticket.domain.vo;

import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TicketIdUnitTest {


    @Test
    void createNewTicketAndTicketWithTicketIdIdAreTheSame() {
        TicketId ticketId = new TicketId();
        TicketId sameTicketId = new TicketId(ticketId.id);
        Assertions.assertThat(ticketId).isEqualTo(sameTicketId);
    }

    @Test
    void createTicketFromStringWithWrongAggregateIdType() {
        TicketPositionId ticketPositionId = new TicketPositionId();

        Assertions.assertThatExceptionOfType(AggregateRootValidationException.class)
                .isThrownBy(() -> new TicketId(ticketPositionId.id))
                .withMessage(AggregateRootValidationMsg.WRONG_AGGREGATE_TYPE_FOR_PREFIX);
    }
}
