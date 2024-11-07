package com.fiuni.distri.project.fiuni.repositories;

import com.fiuni.distri.project.fiuni.domain.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PuestoRepository extends JpaRepository<Puesto, Integer>, JpaSpecificationExecutor<Puesto> {
}
