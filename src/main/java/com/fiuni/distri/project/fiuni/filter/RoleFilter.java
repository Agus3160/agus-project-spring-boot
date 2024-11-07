package com.fiuni.distri.project.fiuni.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RoleFilter extends BaseFilter{
    private String rol;
    public RoleFilter(String rol, LocalDateTime startDate, LocalDateTime endDate, boolean includeDeleted, int page, int size) {
        super(startDate, endDate, includeDeleted, page, size);
        this.rol = rol;
    }
}
