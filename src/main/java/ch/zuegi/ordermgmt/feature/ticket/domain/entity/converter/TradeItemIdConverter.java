package ch.zuegi.ordermgmt.feature.ticket.domain.entity.converter;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TradeItemIdConverter implements AttributeConverter<TradeItemId, String> {

    @Override
    public String convertToDatabaseColumn(TradeItemId tradeItemId) {
        return tradeItemId.id;
    }

    @Override
    public TradeItemId convertToEntityAttribute(String s) {
        return new TradeItemId(s);
    }
}
