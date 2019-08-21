package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Siniestro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISiniestrosDao extends JpaRepository<Siniestro, Long>{

}
