package com.fiuni.distri.project.fiuni.controllers;

import com.fiuni.distri.project.fiuni.dto.ResponseDto;
import com.fiuni.distri.project.fiuni.dto.RoleDto;
import com.fiuni.distri.project.fiuni.exception.ApiException;
import com.fiuni.distri.project.fiuni.filter.RoleFilter;
import com.fiuni.distri.project.fiuni.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseDto<RoleDto>> getById(@PathVariable(name = "id") int id) {
        RoleDto data = this.roleService.getById(id);
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Rol encontrado", data, null));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ResponseDto<Page<RoleDto>>> geAll(
            @RequestParam(name = "rol", required = false) String rol,
            @RequestParam(name = "start-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(name = "end-date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(name = "include-deleted", required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Page<RoleDto> data = roleService.getAllRoles(new RoleFilter(rol, startDate, endDate, includeDeleted, page, size));
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Roles encontrados", data, null));
    }

    @PostMapping({"", "/"})
    public ResponseEntity<ResponseDto<RoleDto>> create(
            @RequestBody() RoleDto roleDto
    ) {
        RoleDto data = this.roleService.save(roleDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Rol creado", data, null));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ResponseDto<RoleDto>> update(
            @PathVariable(name = "id") int id,
            @RequestBody() RoleDto roleDto
    ) {
        RoleDto data = this.roleService.updateById(id, roleDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Rol actualizado", data, null));
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseDto> delById(@PathVariable(name = "id") int id) {
        this.roleService.deleteById(id);
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Rol eliminado", null, null));
    }


}
