package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Puesto;
import org.carpetati.spring.app.services.IPuestoServices;
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
@RequestMapping("/puestos")
@SessionAttributes("puesto")
public class PuestoController {

	@Autowired IPuestoServices puestoServices;
	
	@RequestMapping(value = "/listado")
	public String homePage(Model m, @RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Puestos");
		m.addAttribute("lista", puestoServices.findAll(pageRequest));
		return "catalogos/puestos";
	}
	
	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Puestos.- Fromulario de Alta");
		m.addAttribute("puesto", new Puesto());
		return "catalogos/formpuesto";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Puesto puesto, Model m, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Puestos.- Fromulario de Alta");
			return "catalogos/formpuesto";
		}

		String msgFlash = (puesto.getId() != null) ? "Registro de puesto Editado con Exito"
				: "Nuevo Registro de puesto guardado exitosamente!";
		puestoServices.save(puesto);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/puestos/listado";
	}
	
	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if (id > 0) {
			Puesto puesto = puestoServices.findById(id);
			if (puesto == null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/puestos/listado";
			} else {
				m.addAttribute("titulo", "Puestos.-Formulario de Edición");
				m.addAttribute("puesto", puesto);
				return "catalogos/formpuesto";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/puesto/listado";
		}

	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Puesto puesto= puestoServices.findById(id);
			if(puesto==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/puestos/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				puestoServices.delete(puesto);
				return "redirect:/puestos/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/puestos/listado";
		}
	}
}
