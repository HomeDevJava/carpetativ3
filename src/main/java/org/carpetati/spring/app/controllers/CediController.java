package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.entity.Cedi;
import org.carpetati.spring.app.services.ICediServices;
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
@RequestMapping("/cedis")
@SessionAttributes("cedi")
public class CediController {

	@Autowired
	ICediServices cediServices;

	@RequestMapping(value = "/listado")
	public String homePage(Model m,  @RequestParam(defaultValue="0") int page,  @RequestParam(defaultValue="10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Cedis");
		m.addAttribute("lista", cediServices.findAll(pageRequest));
		return "catalogos/cedis";
	}

	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Cedis.- Formulario de Alta");
		m.addAttribute("cedi", new Cedi());
		return "catalogos/formcedi";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Cedi cedi, Model m, BindingResult result, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			m.addAttribute("titulo", "Cedis.- Fromulario de Alta");
			return "catalogos/formcedi";
		}

		String msgFlash = (cedi.getId() != null) ? "Registro de Cedi Editado con Exito"
				: "Nuevo Registro de Cedi guardado exitosamente!";
		cediServices.save(cedi);
		status.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", msgFlash);
		return "redirect:/cedis/listado";
	}

	@RequestMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if (id > 0) {
			Cedi cedi = cediServices.findById(id);
			if (cedi == null) {
				flash.addFlashAttribute("css", "danger");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/cedis/listado";
			} else {
				m.addAttribute("titulo", "Cedis.-Formulario de Edición");
				m.addAttribute("cedi", cedi);
				return "catalogos/formcedi";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/cedi/listado";
		}

	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable Long id, Model m, RedirectAttributes flash) {
		if(id>0) {
			Cedi cedi= cediServices.findById(id);
			if(cedi==null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id "+ id + ", no existe en el catalogo");
				return "redirect:/cedis/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id "+id+", se ha borrado exitosamente!");
				cediServices.delete(cedi);
				return "redirect:/cedis/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/cedis/listado";
		}
	}
}
