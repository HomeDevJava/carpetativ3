package org.carpetati.spring.app.dao;

import java.util.List;

import org.carpetati.spring.app.entity.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEquipoDao extends JpaRepository<Equipo, Long>{

	@Query("select e from Equipo e join fetch e.modelo m join fetch m.device d join fetch m.marca ma where e.serie like %?1%")
	public List<Equipo> findBySerie(String serie);
	
	public List<Equipo> findBySerieLikeIgnoreCase(String serie);
	
	public Page<Equipo> findBySerieContainingOrAfijoContaining(String serie, String afijo, Pageable p);
	
	

}
