package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TicketPositionNumberConverter;
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
@Table(name = "ticket_position")
public class TicketPositionEntity {

    @Id
    @Column(name = "ticket_position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ticketPositionId;

    @Column(name ="ticket_position_number", length = 50, unique = true, columnDefinition = "varchar(50)")
    @Convert(converter = TicketPositionNumberConverter.class)
    private TicketPositionId ticketPositionNumber;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private TicketEntity ticketEntity;

    @Convert(converter = TradeItemIdConverter.class)
    private TradeItemId tradeItemId;
    private BigDecimal menge;

}
