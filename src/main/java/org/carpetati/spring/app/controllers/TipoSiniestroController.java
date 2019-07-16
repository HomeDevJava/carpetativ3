package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.TipoSiniestro;
import org.carpetati.spring.app.services.ITipoSiniestroServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tiposiniestros")
@SessionAttributes("tipoSiniestro")
public class TipoSiniestroController {

	@Autowired ITipoSiniestroServices tipoSiniestroServices;
	
	@RequestMapping(value = "/listado")
	public String homePage(Model m) {
		m.addAttribute("titulo", "Catalogo de Siniestros");
		m.addAttribute("lista", tipoSiniestroServices.findAll());
		return "catalogos/tiposiniestros";
	}
	
	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Tipo de Siniestro.- Formulario de Alta ");
		m.addAttribute("tipoSiniestro", new TipoSiniestro());
		return "catalogos/tiposiniestroform";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST)
	public String guardar(TipoSiniestro tipo, Model m, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			m.addAttribute("titulo", "Tipo de Siniestro.- Formulario de Alta ");
			return "catalogos/tiposiniestroform";
		}
		
		String msgFlash=(tipo.getId() !=null) ? "Registro de Tipo Siniestro Editado con Exito" :
			"Nuevo Registro de Tipo Siniestro guardado exitosamente!";
		
		tipoSiniestroServices.save(tipo);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/tiposiniestros/listado";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			TipoSiniestro tipo= tipoSiniestroServices.findById(id);
			if(tipo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/tiposiniestros/listado";
			}else {
				m.addAttribute("titulo", "Tipo Siniestro.-Formulario de Edición");
				m.addAttribute("tipoSiniestro", tipo);
				return "catalogos/tiposiniestroform";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/tiposiniestros/listado";
		}
		
	}
	
	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			TipoSiniestro tipo= tipoSiniestroServices.findById(id);
			if(tipo==null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/tiposiniestros/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				tipoSiniestroServices.delete(tipo);
				return "redirect:/tiposiniestros/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/tiposiniestros/listado";
		}
		
	}
	
}
