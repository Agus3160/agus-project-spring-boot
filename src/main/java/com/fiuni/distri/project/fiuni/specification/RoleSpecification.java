package com.fiuni.distri.project.fiuni.specification;

import com.fiuni.distri.project.fiuni.domain.Role;
import com.fiuni.distri.project.fiuni.filter.RoleFilter;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification extends BaseSpecification<Role, RoleFilter> {

    public Specification<Role> hasRol(String rol) {
        return (root, query, criteriaBuilder) -> {
            if (rol == null || rol.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("rol"), "%" + rol + "%");
        };
    }

    public Specification<Role> withFilters(RoleFilter filter) {
        return Specification.
                where(hasRol(filter.getRol()))
                .and(includeDeleted(filter.isIncludeDeleted()))
                .and(createdAtBetween(filter.getStartDate(), filter.getEndDate()));
    }

}
