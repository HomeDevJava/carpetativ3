package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModeloDao extends JpaRepository<Modelo, Long> {

}
