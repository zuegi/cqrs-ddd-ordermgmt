package ch.zuegi.ordermgmt.shared;


import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Slf4j
public abstract class RandomUUID implements ValueObject<RandomUUID> {
    public static final String FORMATTER = "-%s";
    @NotNull
    @Size(min = 16, max = 50)
    public final String id;

    public RandomUUID() {
        this.id = String.format(getPrefix()+ FORMATTER, UUID.randomUUID().toString());
    }

    public RandomUUID(String id) {
        this.id = id;
        this.validate();
    }

    private void validate() {
        if (!this.id.startsWith(getPrefix()) ) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.WRONG_AGGREGATE_TYPE_FOR_PREFIX);
        }
    }

    @Override
    public boolean sameValueAs(RandomUUID other) {
        return other != null && this.id.equals(other.id);
    }

    protected abstract String getPrefix();


    @Override
    public String toString() {
        String str = "";
        //Converts object to json string using Jaxson
        ObjectMapper mapper = new ObjectMapper();

        try {
            str = mapper.writeValueAsString(this);
        } catch (Exception exception) {
            log.error(String.valueOf(exception));
        }
        return str;
    }

}
