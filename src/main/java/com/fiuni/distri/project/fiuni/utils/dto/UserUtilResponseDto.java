package com.fiuni.distri.project.fiuni.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUtilResponseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String email;

    private String[] roles;

    private LocalDateTime createdAt;

}
