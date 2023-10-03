package conociendo.struts.formxmlvalidation.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import conociendo.struts.formxmlvalidation.model.Persona;
import conociendo.struts.formxmlvalidation.model.Estado;
import conociendo.struts.formxmlvalidation.service.EditarService;
import conociendo.struts.formxmlvalidation.service.EditarServiceEnMemoria;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Actúa como un controlador para manejar las acciones relacionads para editar
 * una Persona. 
 */
public class EditarFormularioAction extends ActionSupport {
	private static final long serialVersionUID = -3770858807068916265L;

	private EditarService editarService = new EditarServiceEnMemoria();

	private Persona personaBean;

	private String[] deportes = { "futbol", "beisbol", "baloncesto" };

	private String[] generos = { "masculino", "femenino", "no lo se" };

	private List<Estado> estados;

	private String[] modelosCocheDisponibles = { "Ford", "Chrysler", "Toyota", "Nissan", "Seat", "Renault", "Opel" };

	public String execute() throws Exception {
		editarService.savePersona(getPersonaBean());
		return SUCCESS;
	}

	public String input() throws Exception {
		setPersonaBean(editarService.getPersona());
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
		estados.add(new Estado("M", "Madrid"));
		estados.add(new Estado("GU", "Guadalajara"));
		estados.add(new Estado("B", "Barcelona"));
		estados.add(new Estado("CU", "Cuenca"));
		estados.add(new Estado("SO", "Soria"));

		return estados;
	}

	public String[] getModelosCocheDisponibles() {
		return modelosCocheDisponibles;
	}

}
