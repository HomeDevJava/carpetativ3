package org.carpetati.spring.app.dao;

import java.util.List;
import org.carpetati.spring.app.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDeviceDao extends JpaRepository<Device, Long>{
	@Query("select e.id, e.nombre from Device e where e.nombre like %?1%")
	public List<Device> findByNombre(String n);
}
