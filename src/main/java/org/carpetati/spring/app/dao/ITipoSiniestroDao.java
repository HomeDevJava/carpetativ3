package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.TipoSiniestro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoSiniestroDao extends JpaRepository<TipoSiniestro, Long> {

}
