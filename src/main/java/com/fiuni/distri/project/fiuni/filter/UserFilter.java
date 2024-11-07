package com.fiuni.distri.project.fiuni.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserFilter extends BaseFilter {

    private String username;
    private String email;

    public UserFilter(String username, String email, LocalDateTime startDate, LocalDateTime endDate, boolean includeDeleted, int page, int size) {
        super(startDate, endDate, includeDeleted, page, size);
        this.email = email;
        this.username = username;
    }

}
