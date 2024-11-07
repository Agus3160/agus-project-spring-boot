package com.fiuni.distri.project.fiuni.service;

import com.fiuni.distri.project.fiuni.domain.Empleado;
import com.fiuni.distri.project.fiuni.domain.User;
import com.fiuni.distri.project.fiuni.dto.UserDto;
import com.fiuni.distri.project.fiuni.exception.ApiException;
import com.fiuni.distri.project.fiuni.filter.UserFilter;
import com.fiuni.distri.project.fiuni.repositories.EmpleadoRepository;
import com.fiuni.distri.project.fiuni.repositories.UserRepository;
import com.fiuni.distri.project.fiuni.specification.UserSpecification;
import com.fiuni.distri.project.fiuni.utils.dto.UserUtilResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    public Page<UserUtilResponseDto> getAll(UserFilter userFilter) {
        UserSpecification userSpecification = new UserSpecification();
        Specification<User> spec = userSpecification.withFilters(userFilter);
        Pageable pageable = PageRequest.of(userFilter.getPage(), userFilter.getSize());
        Page<User> userPage = userRepository.findAll(spec, pageable);
        return userPage.map(u -> {
            UserUtilResponseDto mappedUser = modelMapper.map(u, UserUtilResponseDto.class);
//            cacheRedisUtil.setWithDefaultTTL("user", ""+u.getId(), mappedUser);
            return mappedUser;
        });
    }

//    @CachePut(value = "user", key = "#result.id")
    public UserUtilResponseDto save(UserDto userDto) {
        User newUser = modelMapper.map(userDto, User.class);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserUtilResponseDto.class);
    }

//    @CacheEvict(value = "user", key = "#id")
    public UserUtilResponseDto updateById(int id, UserDto userDto) {
        User updatedUser = modelMapper.map(userDto, User.class);
        updatedUser.setId(id);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        updatedUser = userRepository.save(updatedUser);
        return modelMapper.map(updatedUser, UserUtilResponseDto.class);
    }

//    @Cacheable(value = "user", key = "#id")
    public UserUtilResponseDto getById(int id) {
        Optional<User> role = userRepository.findById(id);
        return modelMapper.map(role.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No encontrado", null)), UserUtilResponseDto.class);
    }

//    @CacheEvict(value = "user", key = "#id")
    public void deleteById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Usuario no encontrado", null);
        User delUser = optionalUser.get();
        delUser.setDeletedAt(LocalDateTime.now());
        userRepository.save(delUser);
        deleteEmpleadoByUser(delUser);
    }


    private void deleteEmpleadoByUser (User user) {
        Optional<Empleado> optionalEmpleado =  empleadoRepository.findByUser(user);
        if(optionalEmpleado.isEmpty()) return;
        Empleado delEmpleado = optionalEmpleado.get();
        delEmpleado.setDeletedAt(LocalDateTime.now());
        empleadoRepository.save(delEmpleado);
    }

}
