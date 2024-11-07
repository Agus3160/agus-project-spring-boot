package com.fiuni.distri.project.fiuni.controllers;

import com.fiuni.distri.project.fiuni.dto.ResponseDto;
import com.fiuni.distri.project.fiuni.dto.UserDto;
import com.fiuni.distri.project.fiuni.filter.UserFilter;
import com.fiuni.distri.project.fiuni.service.UserService;
import com.fiuni.distri.project.fiuni.utils.dto.UserUtilResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping({"/{id}"})
    public ResponseEntity<ResponseDto<UserUtilResponseDto>> getById(@PathVariable(name = "id") int id) {
        UserUtilResponseDto data = this.userService.getById(id);
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Usuario encontrado", data, null));
    }

    @GetMapping({"", "/"})
    public ResponseEntity<ResponseDto<Page<UserUtilResponseDto>>> geAll(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "start-date", required = false) LocalDateTime startDate,
            @RequestParam(name = "start-date", required = false) LocalDateTime endDate,
            @RequestParam(name = "include-deleted", required = false, defaultValue = "false") boolean includeDeleted,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Page<UserUtilResponseDto> data = userService.getAll(new UserFilter(username, email, startDate, endDate, includeDeleted, page, size));
        return ResponseEntity.status(200).body(new ResponseDto<>(200, true, "Usuarios encontrados", data, null));
    }

    @PostMapping({"","/"})
    public ResponseEntity<ResponseDto<UserUtilResponseDto>> create(
            @RequestBody() UserDto userDto
    ){
        UserUtilResponseDto data = this.userService.save(userDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Usuario creado", data, null));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ResponseDto<UserUtilResponseDto>> update(
            @PathVariable(name = "id") int id,
            @RequestBody() UserDto userDto
    ){
        UserUtilResponseDto data = this.userService.updateById(id, userDto);
        return ResponseEntity.status(201).body(new ResponseDto<>(201, true, "Usuario creado", data, null));
    }

    @DeleteMapping({"/{id}"})
    public ResponseEntity<ResponseDto> delById(@PathVariable(name = "id") int id) {
        this.userService.deleteById(id);
        return ResponseEntity.status(204).body(new ResponseDto<>(200, true, "Usuario eliminado", null, null));
    }


}
