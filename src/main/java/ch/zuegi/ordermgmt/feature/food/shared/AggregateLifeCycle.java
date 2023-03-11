package ch.zuegi.ordermgmt.feature.food.shared;


import ch.zuegi.ordermgmt.feature.food.shared.eventsourcing.EventRepository;

/**
 * NOTE: This class should never be made a spring bean as its method are static for using in pojos
 */
public class AggregateLifeCycle {

    // mit dieser Methode wollen wir ein existierendes Bean aufrufen, dazu hilft uns die Klasse SpringContext
    public static void apply(Object event) {

        EventRepository bean = SpringContext.getBean(EventRepository.class);
        bean.on(event);
    }

}
