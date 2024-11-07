package com.fiuni.distri.project.fiuni.repositories;

import com.fiuni.distri.project.fiuni.domain.Empleado;
import com.fiuni.distri.project.fiuni.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer>, JpaSpecificationExecutor<Empleado>  {

    Optional<Empleado> findByNombre(String nombre);

    Optional<Empleado> findByCi(String ci);

    Optional<Empleado> findByUser(User user);

}
