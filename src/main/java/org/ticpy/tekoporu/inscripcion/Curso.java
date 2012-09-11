package org.ticpy.tekoporu.inscripcion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.ticpy.tekoporu.exception.ExceptionHandler;
import org.ticpy.tekoporu.inscripcion.config.InscripcionConfig;
import org.ticpy.tekoporu.inscripcion.domain.Alumno;
import org.ticpy.tekoporu.inscripcion.exception.CursoException;
import org.ticpy.tekoporu.stereotype.Controller;
import org.ticpy.tekoporu.util.ResourceBundle;

@Controller
public class Curso {

	private List<Alumno> alumnosMatriculados = new ArrayList<Alumno>();

	@Inject
	private Logger logger;
	
	@Inject
	private ResourceBundle bundle;
	
	@Inject 
	private InscripcionConfig config;

	public void matricular(Alumno alumno) {
		if (estaMatriculado(alumno) || alumnosMatriculados.size() == 
				config.getCapacidadCurso()) {
			throw new CursoException();
		}
		alumnosMatriculados.add(alumno);
		logger.info(bundle.getString("matricula.exito", alumno));

	}

	public boolean estaMatriculado(Alumno alumno) {
		return alumnosMatriculados.contains(alumno);
	}

	@ExceptionHandler
	public void tratar(CursoException e) {
		logger.warn(bundle.getString("matricula.error"));
		
		throw e;
	}

}