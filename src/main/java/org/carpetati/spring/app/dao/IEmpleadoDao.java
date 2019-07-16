package org.carpetati.spring.app.dao;

import java.util.List;

import org.carpetati.spring.app.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEmpleadoDao extends JpaRepository<Empleado, Long>{
	
	@Query("select e from Empleado e join fetch e.cedi as cedi join fetch e.puesto as puesto")
	public List<Empleado> selectAll();

}
