package conociendo.struts.formxmlvalidation.service;

import conociendo.struts.formxmlvalidation.model.Persona;

/**
 * Implementar los Servicios necesitados para editar y salvar 
 * el estado de un objeto Persona. En esta implemmentación
 * el estado del objeto Persona es almacenado en memoria
 */

public class EditarServiceEnMemoria implements EditarService {

    private static Persona persona;
    private static String[] modelosCoche = {"Ford", "Nissan"};

    static {

        persona = new Persona();
        persona.setNombre("Juan Francisco");
        persona.setApellidos("Ruiz Fernández");
        persona.setCorreo("juanfco.ruiz@gmail.com");
        persona.setDeporte("ciclismo");
        persona.setGenero("no lo se");
        persona.setResidencia("M");
        persona.setMayorde21(true);
        persona.setModelosCoche(modelosCoche);
        persona.setTelefono("0034-646-194713");

    }

    public Persona getPersona() {
        return EditarServiceEnMemoria.persona;
    }

    public void savePersona(Persona personaBean) {

    	EditarServiceEnMemoria.persona.setNombre(personaBean.getNombre());
    	EditarServiceEnMemoria.persona.setApellidos(personaBean.getApellidos());
    	EditarServiceEnMemoria.persona.setDeporte(personaBean.getDeporte());
    	EditarServiceEnMemoria.persona.setGenero(personaBean.getGenero());
    	EditarServiceEnMemoria.persona.setResidencia(personaBean.getResidencia());
    	EditarServiceEnMemoria.persona.setMayorde21(personaBean.isMayorde21());
    	EditarServiceEnMemoria.persona.setModelosCoche(personaBean.getModelosCoche());
    	EditarServiceEnMemoria.persona.setCorreo(personaBean.getCorreo());
    	EditarServiceEnMemoria.persona.setTelefono(personaBean.getTelefono());

    }

}
