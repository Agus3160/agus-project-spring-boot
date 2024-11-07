package com.fiuni.distri.project.fiuni.specification;

import com.fiuni.distri.project.fiuni.domain.User;
import com.fiuni.distri.project.fiuni.filter.UserFilter;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification extends BaseSpecification<User, UserFilter> {

    public Specification<User> hasUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (username == null || username.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("username"), "%" + username + "%");
        };
    }

    public Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) -> {
            if (email == null || email.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("email"), "%" + email + "%");
        };
    }

    public Specification<User> withFilters(UserFilter filter) {
        return Specification.
                where(hasEmail(filter.getEmail()))
                .and(hasUsername(filter.getUsername()))
                .and(includeDeleted(filter.isIncludeDeleted()))
                .and(createdAtBetween(filter.getStartDate(), filter.getEndDate()));
    }


}
