package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketPositionNumberConverter implements AttributeConverter<TicketPositionNumber, String> {
    @Override
    public String convertToDatabaseColumn(TicketPositionNumber ticketPositionId) {
        return ticketPositionId.id;
    }

    @Override
    public TicketPositionNumber convertToEntityAttribute(String s) {
        return new TicketPositionNumber(s);
    }
}
