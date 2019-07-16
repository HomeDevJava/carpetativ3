package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPuestoDao  extends JpaRepository<Puesto, Long>{

}
