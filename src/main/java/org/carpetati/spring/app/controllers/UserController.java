package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Role;
import org.carpetati.spring.app.entity.User;
import org.carpetati.spring.app.services.IEmpleadoServices;
import org.carpetati.spring.app.services.IGenericServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
@SessionAttributes("user")
public class UserController {

	@Autowired IGenericServices<User> userServices;
	@Autowired IEmpleadoServices empleadoServices;
	@Autowired IGenericServices<Role> roleServices;
	
	@GetMapping("/listado")
	public String index(Model m, @RequestParam(defaultValue ="0") int page, @RequestParam(defaultValue ="10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);		
		m.addAttribute("titulo","Catalogo de Usuarios");
		m.addAttribute("lista", userServices.findAll(pageRequest));
		return "catalogos/users";
	}
	
	@GetMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo","Usuarios.- Formulario de Alta");
		m.addAttribute("user", new User());
		m.addAttribute("listaempleados", empleadoServices.findAll());
		m.addAttribute("listaroles", roleServices.findAll());
		return "catalogos/formuser";
	}

	@PostMapping("/form")
	public String guardar(User user, Model m, BindingResult result,RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			m.addAttribute("titulo","Usuarios.- Formulario de Alta");
			return "catalogos/formuser";
		}
		
		String msgFlash = (user.getId() != null) ? "Registro de Usuario Editado con Exito" : "Nuevo Registro de Usuario guardado exitosamente!";
		userServices.save(user);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/users/listado";
	}
	
	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			User user= userServices.findById(id);
			if(user == null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/users/listado";
			}else {
				m.addAttribute("titulo", "Usuarios.-Formulario de Edición");
				m.addAttribute("user", user);
				m.addAttribute("listaroles", roleServices.findAll());
				m.addAttribute("listaempleados", empleadoServices.findAll());
				return "catalogos/formuser";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/users/listado";
		}
	}
}
