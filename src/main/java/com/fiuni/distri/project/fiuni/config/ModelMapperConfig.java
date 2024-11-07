package com.fiuni.distri.project.fiuni.config;

import com.fiuni.distri.project.fiuni.domain.Role;
import com.fiuni.distri.project.fiuni.domain.User;
import com.fiuni.distri.project.fiuni.dto.UserDto;
import com.fiuni.distri.project.fiuni.exceptions.ApiException;
import com.fiuni.distri.project.fiuni.repositories.RoleRepository;
import com.fiuni.distri.project.fiuni.utils.dto.UserUtilResponseDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Autowired
    RoleRepository roleRepository;

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();
        Converter<Set<Role>, String[]> rolesToStringArray = context ->
                context.getSource() != null ? context.getSource().stream()
                        .map(Role::getRol)
                        .toArray(String[]::new) : null;

        Converter<String[], Set<Role>> stringArrayToRoles = context -> {
            if (context.getSource() == null) {
                return null;
            }
            return Set.of(context.getSource()).stream()
                    .map(roleName -> roleRepository.findByRol(roleName)
                            .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Role not found: " + roleName)))
                    .collect(Collectors.toSet());
        };

        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                using(rolesToStringArray).map(source.getRoles()).setRoles(null);
            }
        });

        modelMapper.addMappings(new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                using(stringArrayToRoles).map(source.getRoles()).setRoles(null);
            }
        });


        modelMapper.addMappings(new PropertyMap<User, UserUtilResponseDto>() {

            @Override
            protected void configure() {
                using(rolesToStringArray).map(source.getRoles()).setRoles(null);
            }
        });

        return modelMapper;
    }
}
