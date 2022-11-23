package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketNumberConverter implements AttributeConverter<TicketNumber, String> {

    @Override
    public String convertToDatabaseColumn(TicketNumber ticketNumber) {
        return ticketNumber.id;
    }

    @Override
    public TicketNumber convertToEntityAttribute(String s) {
        return new TicketNumber(s);
    }
}
