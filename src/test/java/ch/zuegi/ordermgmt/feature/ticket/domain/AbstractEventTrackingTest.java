package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

public class AbstractEventTrackingTest {


    DomainEventPublisher domainEventPublisher;
    private List<Class<? extends DomainEvent>> handledEvents;

    @BeforeEach
    void setup() {
        // create instance of
        DomainEventPublisher.instance().reset();

        this.handledEvents = new ArrayList<Class<? extends DomainEvent>>();

        // subscribe zu einem neuen Event, sdoass gezählt werden kann wieviele Events und welche EventKlassen ausgelöst wurden
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
                @Override
                public void handleEvent(DomainEvent aDomainEvent) {
                    handledEvents.add(aDomainEvent.getClass());
                }

                @Override
                public Class<DomainEvent> subscribedToEventType() {
                    return DomainEvent.class;
                }
            });
    }

    protected void expectedEvents(int anEventCount) {
        if (this.handledEvents.size() != anEventCount) {
            throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
                    + " events: " + this.handledEvents);
        }
    }

    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType) {
        this.expectedEvent(aDomainEventType, 1);
    }

    protected void expectedEvent(Class<? extends DomainEvent> aDomainEventType, int aTotal) {
        int count = 0;

        for (Class<? extends DomainEvent> type : this.handledEvents) {
            if (type == aDomainEventType) {
                ++count;
            }
        }

        if (count != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aDomainEventType.getSimpleName() + " events, but handled "
                    + this.handledEvents.size() + " events: " + this.handledEvents);
        }
    }

}
