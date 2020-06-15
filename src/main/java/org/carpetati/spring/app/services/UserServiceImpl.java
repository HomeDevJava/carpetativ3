package org.carpetati.spring.app.services;

import java.util.ArrayList;
import java.util.List;
import org.carpetati.spring.app.dao.IUserDao;
import org.carpetati.spring.app.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements IGenericServices<User>, UserDetailsService{
	
	@Autowired private IUserDao usersDao;
	@Autowired private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	@Override
	public List<User> findAll() {
		return usersDao.findAll();
	}

	@Transactional
	@Override
	public void save(User entidad) {
		entidad.setPassword(encoder.encode(entidad.getPassword()));
		usersDao.save(entidad);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		usersDao.deleteById(id);
	}

	@Transactional
	@Override
	public void delete(User entidad) {
		usersDao.delete(entidad);
	}

	@Transactional
	@Override
	public User findById(Long id) {
		return usersDao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<User> findAll(Pageable p) {
		return usersDao.findAll(p);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User us= usersDao.findByUsername(username);
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		us.getRoles().forEach(r ->{
			roles.add(new SimpleGrantedAuthority(r.getRole()));
		});
		
		UserDetails userDet= new org.springframework.security.core.userdetails.User(us.getUsername(), us.getPassword(),roles);
		
		return userDet;
	} 
	
	
	
	
	
	/*
	 * 
	 * 
	 * @Override public UserDetails loadUserByUsername(String username) throws
	 * UsernameNotFoundException {
	 * 
	 * User us= userDao.findByUser(username);
	 * 
	 * List<GrantedAuthority> roles = new ArrayList<>();
	 * 
	 * us.getRoles().forEach(r ->{ roles.add(new
	 * SimpleGrantedAuthority(r.getRole())); });
	 * 
	 * UserDetails userDet= new
	 * org.springframework.security.core.userdetails.User(us.getUser(),us.getPassword(),roles); 
	 * return userDet; }
	 */

}
