package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Role;
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
@RequestMapping("/roles")
@SessionAttributes("role")
public class RoleController {

	@Autowired private IGenericServices<Role> roleServices;
	
	@GetMapping("/listado")
	public String index(Model m, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Roles");
		m.addAttribute("page", roleServices.findAll(pageRequest));
		return "catalogos/roles";
	}
	
	@GetMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Roles.- Formulario de Alta");
		m.addAttribute("role", new Role());
		return "catalogos/formrole";
	}
	
	@PostMapping("/form")
	public String guardar(Role role, Model m, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			m.addAttribute("titulo", "Roles.- Formulario de Alta");
			return "catalogos/formrole";
		}
		
		String msgFlash= (role.getId()!=null) ? "Registro del Role Actualizado" : "Nuevo Registro Role Guardado";
		roleServices.save(role);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/roles/listado";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id,Model m, RedirectAttributes flash) {
		if(id>0) {
			Role role= roleServices.findById(id);
			if(role==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/roles/listado";
			}else {
				m.addAttribute("titulo", "Roles.-Formulario de Edición");
				m.addAttribute("role", role);
				return "catalogos/formrole";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/roles/listado";
		}
	}

	@GetMapping("/eliminar({id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id>0) {
			Role role= roleServices.findById(id);
			if(role==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/roles/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				roleServices.delete(role);
				return "redirect:/roles/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/roles/listado";
		}
	}
}
