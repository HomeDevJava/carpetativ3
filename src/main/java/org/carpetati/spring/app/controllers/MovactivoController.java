package org.carpetati.spring.app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.carpetati.spring.app.entity.Equipo;
import org.carpetati.spring.app.entity.ItemMovactivo;
import org.carpetati.spring.app.entity.Movactivo;
import org.carpetati.spring.app.services.ICediServices;
import org.carpetati.spring.app.services.IEmpleadoServices;
import org.carpetati.spring.app.services.IEquipoServices;
import org.carpetati.spring.app.services.IMovactivoServices;
//import org.carpetati.spring.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
@RequestMapping("/movactivos")
@SessionAttributes("movactivo")
public class MovactivoController {

	@Autowired private IMovactivoServices movactivoServices;
	@Autowired private IEmpleadoServices empleadoServices;
	@Autowired private ICediServices cediServices;
	@Autowired private IEquipoServices equipoServices;
	//@Autowired private DataSource dataSource;

	@GetMapping("/listado")
	public String index(Model m, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Movactivo> movactivos = movactivoServices.findAll(pageRequest);
		// PageRender<Movactivo> pageRender = new PageRender<>("/movactivos/listado",
		// movactivos);

		m.addAttribute("titulo", "Catalogo de Movimientos de Activos");
		m.addAttribute("lista", movactivos);
		// m.addAttribute("page", pageRender);

		return "catalogos/movactivos";
	}

	@GetMapping("/form")
	public String form(Model m) {
		m.addAttribute("listaempleados", empleadoServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());
		m.addAttribute("movactivo", new Movactivo());
		m.addAttribute("titulo", "Movimientos de Activos.-Nuevo Registro");
		return "catalogos/formmovactivo";
	}

	@PostMapping("/form")
	public String guardar(Movactivo movactivo, Model m, BindingResult result, RedirectAttributes flash,
			SessionStatus estado, @RequestParam(name = "item_id[]", required = false) Long[] itemId) {

		if (result.hasErrors()) {
			m.addAttribute("listaempleados", empleadoServices.findAll());
			m.addAttribute("listacedis", cediServices.findAll());
			m.addAttribute("movactivo", new Movactivo());
			m.addAttribute("titulo", "Movimientos de Activos.-Nuevo Registro");
			return "catalogos/formmovactivo";
		}

		if (itemId != null) {
			for (int i = 0; i < itemId.length; i++) {
				Equipo equipo = equipoServices.findById(itemId[i]);

				ItemMovactivo linea = new ItemMovactivo();
				linea.setEquipo(equipo);

				movactivo.AddItemMovactivo(linea);

			}
		}

		movactivoServices.save(movactivo);
		estado.setComplete();
		flash.addFlashAttribute("success", "Mov. de Activo creado con exito");
		return "redirect:/movactivos/listado";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, Model m) {
		m.addAttribute("titulo", "Movimientos de Activos.-Editar Registro");
		m.addAttribute("movactivo", movactivoServices.findById(id));
		m.addAttribute("listaempleados", empleadoServices.findAll());
		m.addAttribute("listacedis", cediServices.findAll());

		return "catalogos/formmovactivo";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {

		if (id > 0) {
			Movactivo m = movactivoServices.findById(id);

			if (m == null) {
				flash.addFlashAttribute("css", "warning");
				flash.addFlashAttribute("msg", "El Id " + id + ", no existe en el catalogo");
				return "redirect:/movactivos/listado";
			} else {
				flash.addFlashAttribute("css", "success");
				flash.addFlashAttribute("msg", "el Id " + id + ", se ha borrado exitosamente!");
				movactivoServices.delete(m);
				return "redirect:/movactivos/listado";
			}
		} else {
			flash.addFlashAttribute("css", "info");
			flash.addFlashAttribute("msg", "El Id del registro no puede ser 0");
			return "redirect:/movactivos/listado";
		}
	}

	
	@GetMapping("/reporte/{id}")
	public void reporte(@PathVariable Long id, HttpServletResponse response) throws JRException, SQLException, IOException {
		InputStream file=  getClass().getResourceAsStream("/jasper/BlankA4.jrxml");
		
		JasperReport jasperReport = JasperCompileManager.compileReport(file);
		Map<String, Object> params = movactivoServices.report(id);					
		JRBeanCollectionDataSource data= new JRBeanCollectionDataSource(Collections.singletonList(movactivoServices.report(id)));
		JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport, params, data); 		
		/*
		 * HtmlExporter exporter= new HtmlExporter(DefaultJasperReportsContext.getInstance());
		 * exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		 * exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter())); 
		 * exporter.exportReport();
		*/ 	
		JRPdfExporter pdf= new JRPdfExporter(DefaultJasperReportsContext.getInstance());
		pdf.setExporterInput(new SimpleExporterInput(jasperPrint));
		pdf.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		pdf.exportReport();
				
		
		/*
		 * String path = this.getClass().getResource("/jasper/prueba.jrxml").getFile();
		 * System.out.println("el path::::::" + path.toString()); JasperReport
		 * jasperReport = JasperCompileManager.compileReport(path); Map<String, Object>
		 * params = new HashMap<String, Object>(); params.put("Folio", id); byte[]
		 * reporte = null; try { reporte = JasperRunManager.runReportToPdf(jasperReport,
		 * params,dataSource.getConnection());
		 * response.setContentType("application/pdf");
		 * response.getOutputStream().write(reporte, 0, reporte.length);
		 * response.getOutputStream().flush(); response.getOutputStream().close();
		 * 
		 * } catch (Exception e) { System.out.println(e); }
		 */
	}

	@GetMapping("/eliminaritem/{id}")
	public String borrarItem(@PathVariable Long id) {
		ItemMovactivo item = movactivoServices.findItem(id);
		movactivoServices.borrarItem(item);
		return "redirect:/movactivos/form/" + item.getMovactivo().getId();

	}
	
	@GetMapping("/anexar/{id}")
	public String formAnexar(Model m, @PathVariable long id) {
		Movactivo movactivo= movactivoServices.findById(id);
		m.addAttribute("movactivo", movactivo);
		m.addAttribute("titulo", "Anexar evidencia PDF al registro "+movactivo.getId());
		return "catalogos/formanexar";
	}
	
	@PostMapping("/anexar")
	public String anexar(Movactivo movactivo, @RequestParam("file") MultipartFile file) throws IOException {
		movactivo.setEvidencia(file.getBytes());
		movactivoServices.save(movactivo);
		return "redirect:/movactivos/listado";
	}
	
	@GetMapping("/visualizar/{id}")
	public void visualizar(@PathVariable long id, HttpServletResponse response) throws IOException {
		Movactivo movactivo= movactivoServices.findById(id);
		response.getOutputStream().write(movactivo.getEvidencia());
		
	}
}
