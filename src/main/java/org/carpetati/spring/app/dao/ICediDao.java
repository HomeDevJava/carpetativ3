package org.carpetati.spring.app.dao;

import org.carpetati.spring.app.entity.Cedi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICediDao extends JpaRepository<Cedi, Long>{

}
