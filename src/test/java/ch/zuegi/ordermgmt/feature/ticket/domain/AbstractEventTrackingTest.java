package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractEventTrackingTest {


    DomainEventPublisher domainEventPublisher;
    private List<Class<? extends DomainEvent>> handledEvents;
    private Map<Class<? extends DomainEvent>, List<DomainEvent>> handeledEventsMap;

    @BeforeEach
    void setup() {
        // create instance of
        DomainEventPublisher.instance().reset();

        this.handledEvents = new ArrayList<>();
        this.handeledEventsMap = new HashMap<>();

        // subscribe zu einem neuen Event, sdoass gezählt werden kann wieviele Events und welche EventKlassen ausgelöst wurden
        DomainEventPublisher.instance().subscribe(new DomainEventSubscriber<DomainEvent>() {
                @Override
                public void handleEvent(DomainEvent aDomainEvent) {
                    handledEvents.add(aDomainEvent.getClass());

                    List<DomainEvent> domainEvents = handeledEventsMap.get(aDomainEvent.getClass());
                    if (domainEvents == null) {
                        domainEvents = new ArrayList<>();
                        domainEvents.add(aDomainEvent);
                        handeledEventsMap.put(aDomainEvent.getClass(), domainEvents);
                    } else {
                        domainEvents.add(aDomainEvent);
                    }
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

        int size = this.handeledEventsMap.entrySet()
                .size();

        // Könnte auch einfach die size zurückgeben ohne Exception und überlassen dem Test was ob die size korrekt ist
        // dan wäre auch die Methoden Signature leer also so etwas protected int expectedEvents()
        if (size != anEventCount) {
            throw new IllegalStateException("Expected " + anEventCount + " events, but handled " + this.handledEvents.size()
                    + " events: " + this.handledEvents);
        }
    }

    protected List<? extends DomainEvent> expectedEvents(Class<? extends DomainEvent> aDomainEventType, int aTotal) {
       // hier die Map durchgrasen und die Lister zurückgeben
        List<? extends DomainEvent> domainEvents = this.handeledEventsMap.get(aDomainEventType);

        // Könnte auch ohne die Exception passieren, in dem wir einfach eine leer liste zurückgeben und dem Test überlassen was damit zu tun ist.
        if (domainEvents != null && domainEvents.size() != aTotal) {
            throw new IllegalStateException("Expected " + aTotal + " " + aDomainEventType.getSimpleName() + " events, but handled "
                    + this.handledEvents.size() + " events: " + this.handledEvents);
        }

        return domainEvents;
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
