package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketViewRepository extends JpaRepository<TicketView, Long> {
    TicketView findByTicketId(TicketId ticketId);
}
