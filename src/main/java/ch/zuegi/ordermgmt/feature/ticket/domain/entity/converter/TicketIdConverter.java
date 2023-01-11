package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketIdConverter implements AttributeConverter<TicketId, String> {

    @Override
    public String convertToDatabaseColumn(TicketId ticketId) {
        return ticketId.id;
    }

    @Override
    public TicketId convertToEntityAttribute(String s) {
        return new TicketId(s);
    }
}
