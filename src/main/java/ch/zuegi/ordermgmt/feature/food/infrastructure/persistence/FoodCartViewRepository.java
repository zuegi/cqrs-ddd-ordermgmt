package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FoodCartViewRepository extends CrudRepository<FoodCartView, UUID> {
}
