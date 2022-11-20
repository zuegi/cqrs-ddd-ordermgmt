package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter.TicketNumberConverter;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
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
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ticketId;

    @Column(name = "ticket_number", length = 50, unique = true, columnDefinition = "varchar(50)")
    @Convert(converter = TicketNumberConverter.class)
    TicketNumber ticketNumber;

    LocalDateTime localDateTime;
    TicketLifeCycleState lifeCycleState;

    @OneToMany(mappedBy = "ticketEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<TicketPositionEntity> ticketPositionEntitySet = new HashSet<>();
}
