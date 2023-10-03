package conociendo.struts.edit.service;

import conociendo.struts.edit.model.Persona;

/**
 * Implement Services needed to edit and save
 * a Person object's state.  In this implementation
 * the Person object's state is stored in memory
 * @author brucephillips
 *
 */
public class EditarServiceEnMemoria implements EditarService {
	
	
	private static Persona persona ;
	private static String [] modelosCoche = {"Ford","Nissan"};

	
	static {
		
		persona = new Persona();
		persona.setNombre("Bruce");
		persona.setApellidos("Phillips");
		persona.setDeporte("basketball");
		persona.setGenero("not sure");
		persona.setResidencia("KS");
		persona.setMayorde21(true);		
		persona.setModelosCoche( modelosCoche);		

	}

	
	public Persona obtenerPersona() {
		
		return EditarServiceEnMemoria.persona;
	}


	public void salvarPersona(Persona personBean) {

		EditarServiceEnMemoria.persona.setNombre(personBean.getNombre());
		EditarServiceEnMemoria.persona.setApellidos(personBean.getApellidos() );
		EditarServiceEnMemoria.persona.setDeporte(personBean.getDeporte() );
		EditarServiceEnMemoria.persona.setGenero( personBean.getGenero() );
		EditarServiceEnMemoria.persona.setResidencia( personBean.getResidencia() );
		EditarServiceEnMemoria.persona.setMayorde21( personBean.isMayorde21() );
		EditarServiceEnMemoria.persona.setModelosCoche(personBean.getModelosCoche() );

	}

}
