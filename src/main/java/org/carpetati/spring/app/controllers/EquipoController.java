package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Equipo;
import org.carpetati.spring.app.services.IEquipoServices;
import org.carpetati.spring.app.services.IModeloServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/equipos")
@SessionAttributes("equipo")
public class EquipoController {

	@Autowired IEquipoServices equipoServices;
	@Autowired IModeloServices modeloServices;
	
	@RequestMapping("/listado")
	public String homePage(Model m, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size, @RequestParam(defaultValue = "") String search) {
		if(search.isEmpty()) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Equipos");
		m.addAttribute("lista", equipoServices.findAll(pageRequest));
		return "catalogos/equipos";
		}
		else {
			PageRequest pageRequest= PageRequest.of(page, size);			
			m.addAttribute("titulo", "Catalogo de Equipos.-Resultado de la Busqueda");
			m.addAttribute("lista", equipoServices.findBySerieLike(search, search, pageRequest));
			return "catalogos/equipos";
		}
	}
	
	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Equipos.- Formulario de Alta");
		m.addAttribute("equipo", new Equipo());
		m.addAttribute("listamodelos", modeloServices.findAll());
		return "catalogos/formequipo";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(Equipo equipo, Model m, RedirectAttributes flash, BindingResult result, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Equipos.- Formulario de Alta");
			m.addAttribute("listamodelos", modeloServices.findAll());
			return "catalogos/formequipo";
		}
		
		String msgFlash=(equipo.getId()!=null)? "Registro de equipos Actualizado existosamente!" : "Nuevo Registro Equipo Guardado";
		equipoServices.save(equipo);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/equipos/listado";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Equipo equipo=equipoServices.findById(id);
			if(equipo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/equipos/listado";
			}else {
				m.addAttribute("titulo", "equipos.-Formulario de Edición");
				m.addAttribute("equipo", equipo);
				m.addAttribute("listamodelos", modeloServices.findAll());
				return "catalogos/formequipo";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/equipos/listado";
		}
		
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if(id>0) {
			Equipo equipo= equipoServices.findById(id);
			if(equipo==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/equipos/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				equipoServices.delete(equipo);
				return "redirect:/equipos/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/equipos/listado";
		}
	}
	
	@RequestMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Equipo equipo= equipoServices.findById(id);
			if(equipo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/modelos/listado";
			}else {
				m.addAttribute("titulo", "Equipos.- Vista previa");
				m.addAttribute("equipo", equipo);
				return "catalogos/verequipo";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/modelos/listado";
		}	
	}
}
