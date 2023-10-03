package conociendo.struts.edit.action;

import com.opensymphony.xwork2.ActionSupport;
import conociendo.struts.edit.model.Persona;
import conociendo.struts.edit.model.Estado;
import conociendo.struts.edit.service.EditarService;
import conociendo.struts.edit.service.EditarServiceEnMemoria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Actua como un controlador para manejar las acciones
 * relacionadas para editar una Persona.
 * @author Juan F. Ruiz
 *
 */
public class EditarAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	private EditarService editarService = new EditarServiceEnMemoria();
	
	private Persona personaBean;
	
	private String [] deportes = {"futbol", "beisbol", "baloncesto", "tenis", "voleibol", "ciclismo" };
	
	private String [] generos = {"masculino", "femenino", "no lo se" };
	
	private List<Estado> estados;

	private String [] modelosCocheDisponibles = {"Ford","Chrysler","Toyota","Nissan", "Seat", "Renault","Opel"};

	public String execute() throws Exception {
		
	    editarService.salvarPersona( getPersonaBean() );
		
		return SUCCESS;
		
	}
	
	
	public String input() throws Exception {
		
		setPersonaBean( editarService.obtenerPersona() );
		
		return INPUT;
	}
	
	public Persona getPersonaBean() {
		
		
		return personaBean;
		
	}
	
	public void setPersonaBean(Persona persona) {
		
		personaBean = persona;
		
	}


	public List<String> getDeportes() {
		return Arrays.asList(deportes);
	}
	
	public List<String> getGeneros() {
		
		return Arrays.asList(generos);
		
	}



	public List<Estado> getEstados() {
		
		estados = new ArrayList<Estado>();
		estados.add( new Estado("", "Arizona") );
		estados.add( new Estado("CA", "California") );
		estados.add( new Estado("FL", "Florida") );
		estados.add( new Estado("KS", "Kansas") );
		estados.add( new Estado("NY", "New York") );
		
		return estados;
	}



	public String [] getModelosCocheDisponibles() {
		return modelosCocheDisponibles;
	}

}
