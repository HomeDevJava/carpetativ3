package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.TipoProblema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoProblemaDao extends JpaRepository<TipoProblema, Long> {

}
