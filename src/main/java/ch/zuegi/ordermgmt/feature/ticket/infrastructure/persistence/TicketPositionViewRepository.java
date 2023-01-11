package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPositionViewRepository extends JpaRepository<TicketPositionView, Long> {
}
