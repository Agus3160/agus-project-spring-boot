package com.fiuni.distri.project.fiuni.service;

import com.fiuni.distri.project.fiuni.domain.Role;
import com.fiuni.distri.project.fiuni.domain.User;
import com.fiuni.distri.project.fiuni.dto.RoleDto;
import com.fiuni.distri.project.fiuni.exception.ApiException;
import com.fiuni.distri.project.fiuni.filter.RoleFilter;
import com.fiuni.distri.project.fiuni.repositories.RoleRepository;
import com.fiuni.distri.project.fiuni.repositories.UserRepository;
import com.fiuni.distri.project.fiuni.specification.RoleSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public Page<RoleDto> getAllRoles(RoleFilter roleFilter) {
        RoleSpecification roleSpec = new RoleSpecification();
        Specification<Role> spec = roleSpec.withFilters(roleFilter);
        Pageable pageable = PageRequest.of(roleFilter.getPage(), roleFilter.getSize());
        Page<Role> rolePage = roleRepository.findAll(spec, pageable);
        return rolePage.map(r -> {
            RoleDto rol = modelMapper.map(r, RoleDto.class);
//            redisUtil.setWithNoLimitTTL("role", ""+rol.getId(), rol);
            return rol;
        });
    }

//    @CachePut(value = "role", key = "#result.id")
    public RoleDto save(RoleDto roleDto) {
        Role newRole = modelMapper.map(roleDto, Role.class);
        newRole = roleRepository.save(newRole);
        return modelMapper.map(newRole, RoleDto.class);
    }

//    @CacheEvict(value = "role", key = "#id")
    public RoleDto updateById(int id, RoleDto roleDto){
        roleDto.setId(id);
        return save(roleDto);
    }

//    @Cacheable(value = "role", key = "#id")
    public RoleDto getById(int id) {
        Optional<Role> role = roleRepository.findById(id);
        return modelMapper.map(role.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "No encontrado", null)), RoleDto.class);
    }

//    @CacheEvict(value = "role", key = "#id")
    public void deleteById(int id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) throw new ApiException(HttpStatus.NOT_FOUND, "Rol no encontrado", null);
        Role delRole = optionalRole.get();

        //Eliminar el rol actualizando el campo "deletedAt"
        delRole.setDeletedAt(LocalDateTime.now());
        roleRepository.save(optionalRole.get());

        //Remover el rol eliminado de cualquier usuario
        removeRolesFromUsers(delRole);
    }

    private void removeRolesFromUsers(Role rol) {
        List<User> userList = userRepository.findAllByRolesContaining(rol);
        userList.forEach(user -> {
            user.getRoles().remove(rol);
            userRepository.save(user);
        });
    }

}
