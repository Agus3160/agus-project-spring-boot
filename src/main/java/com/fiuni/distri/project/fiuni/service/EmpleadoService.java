package com.fiuni.distri.project.fiuni.service;

import com.fiuni.distri.project.fiuni.domain.Empleado;
import com.fiuni.distri.project.fiuni.domain.Puesto;
import com.fiuni.distri.project.fiuni.domain.User;
import com.fiuni.distri.project.fiuni.dto.EmpleadoDto;
import com.fiuni.distri.project.fiuni.exception.ApiException;
import com.fiuni.distri.project.fiuni.filter.EmpleadoFilter;
import com.fiuni.distri.project.fiuni.repositories.EmpleadoRepository;
import com.fiuni.distri.project.fiuni.repositories.PuestoRepository;
import com.fiuni.distri.project.fiuni.repositories.UserRepository;
import com.fiuni.distri.project.fiuni.specification.EmpleadoSpecification;
import com.fiuni.distri.project.fiuni.utils.dto.EmpleadoAndUserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PuestoRepository puestoRepository;

    @Autowired
    ModelMapper modelMapper;

    public Page<EmpleadoDto> getAll(EmpleadoFilter empleadoFilter) {
        EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification();
        Specification<Empleado> spec = empleadoSpecification.withFilters(empleadoFilter);
        Pageable pageable = PageRequest.of(empleadoFilter.getPage(), empleadoFilter.getSize());
        Page<Empleado> empleadoPage = empleadoRepository.findAll(spec, pageable);
        return empleadoPage.map(r -> {
            EmpleadoDto empleadoDto = modelMapper.map(r, EmpleadoDto.class);
//            redisUtil.setWithDefaultTTL("empleado", "" + empleadoDto.getId(), empleadoDto);
            return empleadoDto;
        });
    }

    @Cacheable()
    public EmpleadoDto save(EmpleadoDto empleadoDto) {
        Optional<Puesto> puestoOpt = puestoRepository.findById(empleadoDto.getPuesto_id());
        Optional<User> userOpt = userRepository.findById(empleadoDto.getUser_id());

        if (userOpt.isEmpty() || puestoOpt.isEmpty())
            throw new ApiException(HttpStatus.NOT_FOUND, "Puesto o Usuario no encontrado", null);

        Empleado newEmpleado = modelMapper.map(empleadoDto, Empleado.class);
        newEmpleado.setPuesto(puestoOpt.get());
        newEmpleado.setUser(userOpt.get());
        newEmpleado = empleadoRepository.save(newEmpleado);
        return modelMapper.map(newEmpleado, EmpleadoDto.class);
    }

//    @CacheEvict(value = "empleado", key = "#id")
    public EmpleadoDto updateById(int id, EmpleadoDto empleadoDto) {
        empleadoDto.setId(id);
        return save(empleadoDto);
    }

    public EmpleadoDto saveWithUser(EmpleadoAndUserDto data) {
        // FIND THE PUESTO
        Optional<Puesto> puestoOptional = puestoRepository.findById(data.getPuestoId());
        if (puestoOptional.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Puesto no encontrado", null);
        Puesto puesto = puestoOptional.get();

        // GENERATE THE USER
        User newUser = userRepository.save(modelMapper.map(data.getUserData(), User.class));

        // CREATION OF THE EMPLOYEE
        EmpleadoDto newEmpleadoDto = new EmpleadoDto();
        newEmpleadoDto.setUser_id(newUser.getId());
        newEmpleadoDto.setPuesto_id(puesto.getId());
        newEmpleadoDto.setCi(data.getCi());
        newEmpleadoDto.setNombre(data.getNombre());
        return save(newEmpleadoDto);
    }

    @Cacheable(value = "empleado", key = "#id")
    public EmpleadoDto getById(int id) {

        // GET AND VERIFY IF THE ENTITY EXISTS
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);
        if (empleadoOptional.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Empleado no encontrado", null);
        Empleado empleado = empleadoOptional.get();

        // MAP TO EMPLEADODTO
        EmpleadoDto empleadoDto = modelMapper.map(empleado, EmpleadoDto.class);
        empleadoDto.setUser_id(empleado.getUser().getId());
        empleadoDto.setPuesto_id(empleado.getPuesto().getId());


        System.out.println(empleadoDto);
        return empleadoDto;
    }

    @CacheEvict(value = "empleado", key = "#id")
    public void deleteById(int id) {
        Empleado empleado = empleadoRepository.getReferenceById(id);

        // DEL THE USER
        empleado.getUser().setDeletedAt(LocalDateTime.now());
//        redisUtil.removeFromCache("user", ""+empleado.getUser().getId());

        // DEL THE EMPLOYEE
        empleado.setDeletedAt(LocalDateTime.now());

        empleadoRepository.save(empleado);
    }

}
