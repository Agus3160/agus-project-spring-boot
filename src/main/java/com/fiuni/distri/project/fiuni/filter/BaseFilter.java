package com.fiuni.distri.project.fiuni.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BaseFilter {
    private LocalDateTime startDate = null;
    private LocalDateTime endDate = null;
    boolean includeDeleted = false;
    private int page = 0;
    private int size = 10;
}
