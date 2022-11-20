package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPositionRepository extends JpaRepository<TicketPositionEntity, Long> {

}
