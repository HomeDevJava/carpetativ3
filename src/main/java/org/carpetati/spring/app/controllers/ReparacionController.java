package org.carpetati.spring.app.controllers;

import java.util.List;

import org.carpetati.spring.app.entity.Cedi;
import org.carpetati.spring.app.entity.Empleado;
import org.carpetati.spring.app.entity.Equipo;
import org.carpetati.spring.app.entity.ItemReparacion;
import org.carpetati.spring.app.entity.Reparacion;
import org.carpetati.spring.app.entity.Status;
import org.carpetati.spring.app.entity.TipoProblema;
import org.carpetati.spring.app.services.IEmpleadoServices;
import org.carpetati.spring.app.services.IEquipoServices;
import org.carpetati.spring.app.services.IGenericServices;
import org.carpetati.spring.app.services.IReparacionServices;
import org.carpetati.spring.app.services.IStatusServices;
import org.carpetati.spring.app.services.ITipoProblemaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reparaciones")
@SessionAttributes({"reparacion"})
public class ReparacionController {

	@Autowired	private IReparacionServices reparacionServices;
	@Autowired	private IEquipoServices equipoServices;
	@Autowired	private IStatusServices statusServices;
	@Autowired	private IGenericServices<Cedi> cediServices;
	@Autowired	private ITipoProblemaServices tipoProblemaServices;
	@Autowired	private IEmpleadoServices empleadoServices;

	@RequestMapping("/listado")
	public String homePage(Model m, @RequestParam(defaultValue = "0") int page) {
		PageRequest pageRequest = PageRequest.of(page, 5);
		m.addAttribute("titulo", "Bitacora de Reparaciones");
		m.addAttribute("lista", reparacionServices.findAll(pageRequest));		
		return "catalogos/reparaciones";
	}

	@RequestMapping("/form")
	public String crear(Model m) {
		m.addAttribute("titulo", "Reparaciones.- Nueva Solicitud");
		m.addAttribute("reparacion", new Reparacion());
		m.addAttribute("listastatus", statusServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("listaproblemas", tipoProblemaServices.findAll());
		m.addAttribute("listaempleados", empleadoServices.findAll());
		return "catalogos/formreparacion";
	}

	@GetMapping(value = "/cargar-equipos/{term}", produces = { "application/json" })
	public @ResponseBody List<Equipo> cargarEquipo(@RequestParam String term) {
		return equipoServices.findBySerie(term);
	}

	@GetMapping(value = "/cargar-equipos2/{term}", produces = { "application/json" })
	@ResponseBody
	public List<Equipo> cargarEquipo2(@RequestParam String term) {
		return equipoServices.findBySerieLikeIgnoreCase("%" + term + "%");
	}

	@PostMapping("/form")
	public String guardar(Reparacion reparacion, Model m, BindingResult result, RedirectAttributes flash,
			SessionStatus estado, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cedi_id[]", required = false) Long[] cediId,
			@RequestParam(name = "status_id[]", required = false) Long[] statusId,
			@RequestParam(name = "problema_id[]", required = false) Long[] problemaId,
			@RequestParam(name = "empleado_id[]", required = false) Long[] empleadoId,
			@RequestParam(name = "falla[]", required = false) String[] falla) {

		if (result.hasErrors()) {
			m.addAttribute("titulo", "Reparaciones.- Nueva Solicitud");
			m.addAttribute("reparacion", new Reparacion());
			m.addAttribute("listastatus", statusServices.findAll());
			m.addAttribute("listacedis", cediServices.findAll());
			m.addAttribute("listaproblemas", tipoProblemaServices.findAll());
			m.addAttribute("listaempleados", empleadoServices.findAll());
			return "catalogos/formreparacion";
		}

		if (itemId != null) {
			for (int i = 0; i < itemId.length; i++) {
				Equipo equipo = equipoServices.findById(itemId[i]);
				Cedi cedi = cediServices.findById(cediId[i]);
				Status status = statusServices.findById(statusId[i]);
				TipoProblema problema = tipoProblemaServices.findById(problemaId[i]);
				Empleado empleado = empleadoServices.findById(empleadoId[i]);

				ItemReparacion linea = new ItemReparacion();
				linea.setEquipo(equipo);
				linea.setCedi(cedi);
				linea.setStatus(status);
				linea.setTipoproblema(problema);
				linea.setEmpleado(empleado);
				linea.setFalla(falla[i]);

				reparacion.AddItemReparacion(linea);
			}
		}

		reparacionServices.save(reparacion);
		estado.setComplete();// se cierra el sessionAtribute
		flash.addFlashAttribute("success", "RO/RMA creado con exito");
		return "redirect:/reparaciones/listado";
	}

	@RequestMapping("/form/{id}")
	public String editar(Model m, @PathVariable Long id, RedirectAttributes flash) {
		m.addAttribute("reparacion", reparacionServices.findById(id));
		m.addAttribute("titulo", "Reparaciones.- Editar solicitud");
		m.addAttribute("listastatus", statusServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("listaproblemas", tipoProblemaServices.findAll());
		m.addAttribute("listaempleados", empleadoServices.findAll());
		return "catalogos/formreparacion";
	}

	@RequestMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		if (id > 0) {
			Reparacion r = reparacionServices.findById(id);

			if (r == null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/reparaciones/listado";
			} else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id " + id + ", se ha borrado exitosamente!");
				reparacionServices.delete(r);
				return "redirect:/reparaciones/listado";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/reparaciones/listado";
		}
	}

	@RequestMapping("/eliminaritem/{id}")
	public String borrarItem(@PathVariable Long id) {
		ItemReparacion item = reparacionServices.finditem(id);
		reparacionServices.eliminaritem(item);
		return "redirect:/reparaciones/form/" + item.getReparacion().getId();
	}

	@RequestMapping(value = "/editaritem/{id}", method = RequestMethod.GET)
	public String editarItem(@PathVariable Long id, Model m) {
		ItemReparacion item = reparacionServices.finditem(id);
		m.addAttribute("item", item);
		m.addAttribute("listastatus", statusServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("listaproblemas", tipoProblemaServices.findAll());
		m.addAttribute("listaempleados", empleadoServices.findAll());
		return "catalogos/formitemreparacion";
	}
	
	@RequestMapping(value="/guardaritem" , method=RequestMethod.POST)
	public String guardaritem(Reparacion reparacion,ItemReparacion item, BindingResult result, SessionStatus estado) {	
		System.out.println("FALLA:  -----"+item.getId());
		reparacionServices.guardaritem(item);
		//estado.setComplete();
		return "redirect:/reparaciones/form/"+item.getReparacion().getId();
	}
}
