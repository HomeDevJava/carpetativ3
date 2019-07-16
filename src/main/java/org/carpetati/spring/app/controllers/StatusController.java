package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Status;
import org.carpetati.spring.app.services.IStatusServices;
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
@RequestMapping("/status")
@SessionAttributes("status")
public class StatusController {

	@Autowired IStatusServices statusServices;
	
	@RequestMapping(value = "/listado")
	public String homePage(Model m) {
		m.addAttribute("titulo", "Catalogo de status");
		m.addAttribute("lista", statusServices.findAll());
		return "catalogos/status";
	}
	
	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Status.- Fromulario de Alta");
		m.addAttribute("status", new Status());
		return "catalogos/formstatus";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Status status, Model m, BindingResult result, RedirectAttributes flash, SessionStatus state) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Status.- Fromulario de Alta");
			return "catalogos/formstatus";
		}

		String msgFlash = (status.getId() != null) ? "Registro de status Editado con Exito"
				: "Nuevo Registro de status guardado exitosamente!";
		statusServices.save(status);
		state.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/status/listado";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if (id > 0) {
			Status status = statusServices.findById(id);
			if (status == null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/status/listado";
			} else {
				m.addAttribute("titulo", "Status.-Formulario de Edición");
				m.addAttribute("status", status);
				return "catalogos/formstatus";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/status/listado";
		}

	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Status status= statusServices.findById(id);
			if(status==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/status/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				statusServices.delete(status);
				return "redirect:/statuss/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/status/listado";
		}
	}
}
