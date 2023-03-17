package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;
import java.util.UUID;

@Entity
public class FoodCartView {

    public FoodCartView() {
    }

    public FoodCartView(UUID foodCartId, Map<UUID, String> products) {
        this.foodCartId = foodCartId;
        this.products = products;
    }

    @Id
    @Column(name = "foodCartId", nullable = false)
    private UUID foodCartId;

    @ElementCollection
    private Map<UUID, String> products;

}
