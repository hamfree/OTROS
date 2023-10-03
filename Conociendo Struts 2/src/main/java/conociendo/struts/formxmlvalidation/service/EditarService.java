package conociendo.struts.formxmlvalidation.service;

import conociendo.struts.formxmlvalidation.model.Persona;

public interface EditarService {

    Persona getPersona();

    void savePersona(Persona personaBean);

}
