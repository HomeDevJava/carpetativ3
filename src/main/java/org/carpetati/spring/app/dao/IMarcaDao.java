package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMarcaDao extends JpaRepository<Marca, Long>{

}
