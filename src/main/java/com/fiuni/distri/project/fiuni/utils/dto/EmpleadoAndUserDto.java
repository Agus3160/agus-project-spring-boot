package com.fiuni.distri.project.fiuni.utils.dto;

import com.fiuni.distri.project.fiuni.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoAndUserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    //Empleado data
    private String nombre;
    private String ci;
    private int puestoId;

    //User data
    private UserDto userData;

}
