package ch.zuegi.ordermgmt.feature.ticket.domain.response;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketView;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TicketPositionIdConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TradeItemIdConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Convert;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@ToString
public class TicketPositionViewResponse {

    private TicketId ticketId;
    private TicketPositionId ticketPositionId;

    private TradeItemId tradeItemId;

    private BigDecimal menge;
}
