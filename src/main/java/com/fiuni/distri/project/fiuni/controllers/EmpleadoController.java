package com.fiuni.distri.project.fiuni.controllers;

import com.fiuni.distri.project.fiuni.dto.EmpleadoDto;
import com.fiuni.distri.project.fiuni.dto.ResponseDto;
import com.fiuni.distri.project.fiuni.filter.EmpleadoFilter;
import com.fiuni.distri.project.fiuni.service.EmpleadoService;
import com.fiuni.distri.project.fiuni.utils.dto.EmpleadoAndUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseDto<EmpleadoDto>> getById(@PathVariable(name = "id") int id) {
        EmpleadoDto data = this.empleadoService.getById(id);
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Empleado encontrado", data, null));
    }

    @GetMapping({"","/"})
    public ResponseEntity<ResponseDto<Page<EmpleadoDto>>> geAll(
            @RequestParam(name = "ci", required = false) String ci,
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "start-date", required = false) LocalDateTime startDate,
            @RequestParam(name = "start-date", required = false) LocalDateTime endDate,
            @RequestParam(name = "include-deleted", required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Page<EmpleadoDto> data = empleadoService.getAll(new EmpleadoFilter(ci, nombre, startDate, endDate, includeDeleted, page, size));
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Empleados encontrados", data, null));
    }

    @PostMapping({"","/"})
    public ResponseEntity<ResponseDto<EmpleadoDto>> create(
            @RequestBody() EmpleadoDto empleadoDto
    ) {
        EmpleadoDto data = this.empleadoService.save(empleadoDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Empleado creado", data, null));
    }

    @PostMapping({"/user"})
    public ResponseEntity<ResponseDto<EmpleadoDto>> createEmpleadoAndUser(
            @RequestBody() EmpleadoAndUserDto empleadoAndUserDto
    ) {
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Empleado creado", empleadoService.saveWithUser(empleadoAndUserDto), null));
    }


    @PutMapping({"/{id}"})
    public ResponseEntity<ResponseDto<EmpleadoDto>> update(
            @PathVariable(name = "id", required = true) int id,
            @RequestBody() EmpleadoDto empleadoDto
    ) {
        EmpleadoDto data = this.empleadoService.updateById(id, empleadoDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Empleado actualizado", data, null));
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseDto> delById(@PathVariable(name = "id") int id) {
        this.empleadoService.deleteById(id);
        return ResponseEntity.status(204).body(new ResponseDto<>(200, true, "Empleado eliminado", null, null));
    }


}