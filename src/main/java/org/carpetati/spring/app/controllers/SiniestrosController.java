package org.carpetati.spring.app.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.carpetati.spring.app.entity.Equipo;
import org.carpetati.spring.app.entity.ItemSiniestro;
import org.carpetati.spring.app.entity.Siniestro;
import org.carpetati.spring.app.services.ICediServices;
import org.carpetati.spring.app.services.IEquipoServices;
import org.carpetati.spring.app.services.ISiniestrosServices;
import org.carpetati.spring.app.services.ITipoSiniestroServices;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/siniestros")
@SessionAttributes("siniestro")
public class SiniestrosController {

	@Autowired private ISiniestrosServices siniestrosServices;
	@Autowired private ICediServices cediServices;
	@Autowired private ITipoSiniestroServices tipoSiniestroServices;
	@Autowired private IEquipoServices equipoServices;

	@GetMapping(value = "/listado")
	public String homePage(Model m, @RequestParam(defaultValue = "0") int page,	@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		m.addAttribute("titulo", "Catalogo de Siniestros");
		m.addAttribute("lista", siniestrosServices.findAll(pageRequest));
		return "catalogos/siniestros";
	}

	@GetMapping(value = "/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Siniestro.- Formulario de Alta");
		m.addAttribute("siniestro", new Siniestro());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("listasiniestros", tipoSiniestroServices.findAll());
		return "catalogos/formsiniestro";
	}

	@PostMapping("/form")
	public String guardar(Siniestro siniestro, Model m, BindingResult result, RedirectAttributes flash,
			SessionStatus estado, @RequestParam(name = "item_id[]", required = false) Long[] itemId) {

		if (result.hasErrors()) {
			m.addAttribute("titulo", "Siniestro.- Formulario de Alta");
			m.addAttribute("siniestro", new Siniestro());
			m.addAttribute("listacedis", cediServices.findAll());
			m.addAttribute("listasiniestros", tipoSiniestroServices.findAll());
			return "catalogos/formsiniestro";
		}

		if (itemId != null) {
			for (int i = 0; i < itemId.length; i++) {
				Equipo equipo = equipoServices.findById(itemId[i]);
				ItemSiniestro linea = new ItemSiniestro();
				linea.setEquipo(equipo);
				siniestro.AddItemSiniestro(linea);
			}
		}

		siniestrosServices.save(siniestro);
		estado.setComplete();
		flash.addFlashAttribute("css", "success");
		flash.addFlashAttribute("msg", "Registro Siniestro guardado con exito");
		return "redirect:/siniestros/listado";
	}

	@GetMapping(value = "/form/{id}")
	public String editar(@PathVariable Long id, Model m) {
		m.addAttribute("titulo", "Siniestro.- Formulario de Edición");
		m.addAttribute("siniestro", siniestrosServices.findById(id));
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("listasiniestros", tipoSiniestroServices.findAll());
		return "catalogos/formsiniestro";
	}

	@GetMapping("/anexar/{id}")
	public String formAnexar(@PathVariable Long id, Model m) {
		Siniestro siniestro = siniestrosServices.findById(id);
		m.addAttribute("siniestro", siniestro);
		m.addAttribute("titulo", "Anexar evidencia PDF al registro " + siniestro.getId());
		return "catalogos/formanexarsiniestro";

	}

	@PostMapping("/anexar")
	public String anexar(Siniestro siniestro, @RequestParam("file1") MultipartFile actah,@RequestParam("file2") MultipartFile actamp,
			@RequestParam("file3") MultipartFile ife,	@RequestParam("file4") MultipartFile ftdesc) throws IOException {
		
		if(!actah.isEmpty()) {
			siniestro.setActahechos(actah.getBytes());
		}else if(!actamp.isEmpty()) {
			siniestro.setActamp(actamp.getBytes());
		}else if(!ife.isEmpty()) {
			siniestro.setIfe(ife.getBytes());
		}else if(!ftdesc.isEmpty()) {
			siniestro.setFormatodesc(ftdesc.getBytes());			
		}
		
		siniestrosServices.save(siniestro);		
		return "redirect:/siniestros/listado";
	}
	
	@GetMapping("/visualizar/{formato}/{id}")
	public void visualizar(@PathVariable String formato, @PathVariable Long id, HttpServletResponse response) throws IOException {
		Siniestro siniestro = siniestrosServices.findById(id);
		
		if(formato.equals("actahechos")) {
			response.getOutputStream().write(siniestro.getActahechos());
		}else if(formato.equals("actamp")) {
			response.getOutputStream().write(siniestro.getActamp());
		}else if(formato.equals("ife")) {
			response.getOutputStream().write(siniestro.getIfe());
		}else if(formato.equals("ftdesc")) {
			response.getOutputStream().write(siniestro.getFormatodesc());
		}
		
	}

	@GetMapping("/eliminaritem/{id}")
	public String borrarItem(@PathVariable long id) {
		ItemSiniestro item= siniestrosServices.findItem(id);
		siniestrosServices.borrarItem(item);
		return "redirect:/siniestros/form/"+item.getSiniestro().getId();
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable long id, RedirectAttributes flash) {
		
		if(id>0) {
			Siniestro s= siniestrosServices.findById(id);
			
			if(s==null) {
				flash.addFlashAttribute("css","warning");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/sisniestros/listado";
			}else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id " + id + ", se ha borrado exitosamente!");
				siniestrosServices.delete(s);
				return "redirect:/siniestros/listado";
			}
		}else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/movactivos/listado";
		}
	}
}
