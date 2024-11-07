package com.fiuni.distri.project.fiuni.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmpleadoFilter extends BaseFilter {

    private String ci;
    private String nombre;

    public EmpleadoFilter(String ci, String nombre, LocalDateTime startDate, LocalDateTime endDate, boolean includeDeleted, int page, int size) {
        super(startDate, endDate, includeDeleted, page, size);
        this.ci = ci;
        this.nombre = nombre;
    }
}
