package conociendo.struts.edit.service;

import conociendo.struts.edit.model.Persona;

public interface EditarService {
	
	
	Persona obtenerPersona() ;

	void salvarPersona(Persona personBean);

}
