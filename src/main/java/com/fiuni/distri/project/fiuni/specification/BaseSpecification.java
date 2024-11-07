package com.fiuni.distri.project.fiuni.specification;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public abstract  class BaseSpecification<T, F> {

    public Specification<T> includeDeleted(boolean includeDeleted) {
        return (root, query, criteriaBuilder) -> {
            if (includeDeleted) return criteriaBuilder.conjunction();
            return criteriaBuilder.isNull(root.get("deletedAt"));
        };
    }

    public Specification<T> createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null)
                return criteriaBuilder.between(root.get("createdAt"), startDate, endDate);
            else if (startDate != null) return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startDate);
            else if (endDate != null) return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), endDate);
            return criteriaBuilder.conjunction();
        };
    }

    abstract public Specification<T> withFilters(F filters);

}
