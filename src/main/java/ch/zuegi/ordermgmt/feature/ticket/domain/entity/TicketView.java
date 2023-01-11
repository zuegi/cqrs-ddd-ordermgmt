package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TicketIdConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ticket_view")
public class TicketView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "ticket_id", length = 50, unique = true, columnDefinition = "varchar(50)")
    @Convert(converter = TicketIdConverter.class)
    private TicketId ticketId;

    private LocalDateTime localDateTime;

    private TicketLifeCycleState lifeCycleState;

    @OneToMany(mappedBy = "ticketView", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<TicketPositionView> ticketPositionViewSet =  new HashSet<>();
}
