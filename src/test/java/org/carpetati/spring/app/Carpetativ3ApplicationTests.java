package org.carpetati.spring.app;

import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.carpetati.spring.app.dao.IEmpleadoDao;
import org.carpetati.spring.app.dao.IUserDao;
import org.carpetati.spring.app.entity.Role;
import org.carpetati.spring.app.entity.User;
import org.carpetati.spring.app.services.IGenericServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Carpetativ3ApplicationTests {

	@Autowired private IUserDao userDao;
	@Autowired private IEmpleadoDao empleadoDao;
	@Autowired private IGenericServices<Role> roleServices;
	@Autowired private BCryptPasswordEncoder encoder;
	
	
	@Test
	public void crearusuarioTest() {
		User us= new User();
		us.setId(3L);
		Role rol= roleServices.findById((long) 1);
		Set<Role> roles= new HashSet<Role>();
		roles.add(rol);
		
		
		us.setEmpleado(empleadoDao.findById((long) 1).get());
		us.setUsername("test");
		us.setPassword(encoder.encode("741"));
		us.setActive(1);
		us.setRoles(roles);
		
		User retorno= userDao.save(us);
		assertTrue(retorno.getPassword().equalsIgnoreCase(us.getPassword()));
	}

}

