package org.carpetati.spring.app.controllers;

import org.carpetati.spring.app.services.ISiniestrosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/siniestros")
@SessionAttributes("siniestro")
public class SiniestrosController {

	@Autowired private ISiniestrosServices siniestrosServices;
	
	@RequestMapping(value = "/listado")
	public String homePage(Model m, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest= PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Siniestros");
		m.addAttribute("lista", siniestrosServices.findAll(pageRequest));
		return "catalogos/siniestros";
	}
}
