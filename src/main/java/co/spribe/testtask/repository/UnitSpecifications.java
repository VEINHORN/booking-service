package co.spribe.testtask.repository;

import co.spribe.testtask.model.entity.AccomodationType;
import co.spribe.testtask.model.entity.Unit;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class UnitSpecifications {
    public static Specification<Unit> hasNumberOfRooms(Integer numberOfRooms) {
        return (root, query, builder) -> {
            return numberOfRooms == null ? null : builder.equal(root.get("numberOfRooms"), numberOfRooms);
        };
    }

    public static Specification<Unit> hasFloor(Integer floor) {
        return (root, query, builder) -> {
            return floor == null ? null : builder.equal(root.get("floor"), floor);
        };
    }

    public static Specification<Unit> hasCost(BigDecimal cost) {
        return (root, query, builder) -> {
            return cost == null ? null : builder.equal(root.get("cost"), cost);
        };
    }

    public static Specification<Unit> likeDescription(String description) {
        return (root, query, builder) -> {
            return description == null ? null : builder.like(root.get("description"), "%" + description + "%");
        };
    }

    public static Specification<Unit> hasAccomodationType(AccomodationType accomodationType) {
        return (root, query, builder) -> {
            return accomodationType == null ? null : builder.equal(root.get("accomodationType"), accomodationType);
        };
    }
}
