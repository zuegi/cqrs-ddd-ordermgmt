package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketPositionNumberConverter implements AttributeConverter<TicketPositionId, String> {
    @Override
    public String convertToDatabaseColumn(TicketPositionId ticketPositionId) {
        return ticketPositionId.id;
    }

    @Override
    public TicketPositionId convertToEntityAttribute(String s) {
        return new TicketPositionId(s);
    }
}
