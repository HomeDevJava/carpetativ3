package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Reparacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReparacionDao extends JpaRepository<Reparacion, Long> {

}
