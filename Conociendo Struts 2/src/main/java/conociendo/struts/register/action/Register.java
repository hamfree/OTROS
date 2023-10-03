package conociendo.struts.register.action;

import com.opensymphony.xwork2.ActionSupport;

import conociendo.struts.register.model.Person;

public class Register extends ActionSupport {
    private static final long serialVersionUID = 1L;

    private Person personBean;

    public String execute() throws Exception {
        // llama a una clase de Servicio que almacena el estado de personBean
        // en una base de datos

        return SUCCESS;
    }

    public Person getPersonBean() {
        return personBean;
    }

    public void setPersonBean(Person persona) {
        personBean = persona;
    }

    public void validate() {
        if (personBean.getFirstName().length() == 0) {
            addFieldError("personBean.firstName", "Se requiere el primer nombre.");
        }

        if (personBean.getEmail().length() == 0) {
            addFieldError("personBean.email", "El correo electrónico es requerido.");
        }

        if (personBean.getAge() < 18) {
            addFieldError("personBean.age", "Se requiere la edad y debe tener 18 años o más.");
        }
    }

}
