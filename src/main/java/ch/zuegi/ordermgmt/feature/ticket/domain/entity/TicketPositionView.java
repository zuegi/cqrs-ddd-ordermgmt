package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TicketPositionIdConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TradeItemIdConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket_position_view")
public class TicketPositionView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_view_ticket_id")
    private TicketView ticketView;

    @Column(name = "ticket_position_id", length = 50, unique = true, columnDefinition = "varchar(50)")
    @Convert(converter = TicketPositionIdConverter.class)
    private TicketPositionId ticketPositionId;

    @Column(name = "trade_item_id", length = 50, unique = true, columnDefinition = "varchar(50)")
    @Convert(converter = TradeItemIdConverter.class)
    private TradeItemId tradeItemId;

    private BigDecimal menge;
}
