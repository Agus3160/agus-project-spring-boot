package com.fiuni.distri.project.fiuni.specification;

import com.fiuni.distri.project.fiuni.domain.Empleado;
import com.fiuni.distri.project.fiuni.filter.EmpleadoFilter;
import org.springframework.data.jpa.domain.Specification;

public class EmpleadoSpecification extends BaseSpecification<Empleado, EmpleadoFilter> {

    public Specification<Empleado> hasCi(String ci) {
        return (root, query, criteriaBuilder) -> {
            if (ci == null || ci.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("ci"),  ci);
        };
    }

    public Specification<Empleado> hasNombre(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nombre"), "%" + nombre + "%");
        };
    }

    public Specification<Empleado> withFilters(EmpleadoFilter filters) {
        return Specification
                .where(hasCi(filters.getCi()))
                .and(hasNombre(filters.getNombre()))
                .and(createdAtBetween(filters.getStartDate(), filters.getEndDate()))
                .and(includeDeleted(filters.isIncludeDeleted()));
    }
}
