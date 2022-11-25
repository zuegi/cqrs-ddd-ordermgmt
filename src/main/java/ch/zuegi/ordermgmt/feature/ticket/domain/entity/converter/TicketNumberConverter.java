package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketNumberConverter implements AttributeConverter<TicketId, String> {

    @Override
    public String convertToDatabaseColumn(TicketId ticketNumber) {
        return ticketNumber.id;
    }

    @Override
    public TicketId convertToEntityAttribute(String s) {
        return new TicketId(s);
    }
}
