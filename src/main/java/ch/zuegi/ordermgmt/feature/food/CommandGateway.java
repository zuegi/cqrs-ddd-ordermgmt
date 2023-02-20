package ch.zuegi.ordermgmt.feature.food;

import org.springframework.stereotype.Component;

@Component
public class CommandGateway {
    public void send(Object object) {

        // wie mache ich hier aus diesem Object ein spezifiesches Command
        // und wie erkenne ich, dass dieses Command zu einem spezfischen Aggregate
        // annotiert mit @Aggregate gehört
    }
}
