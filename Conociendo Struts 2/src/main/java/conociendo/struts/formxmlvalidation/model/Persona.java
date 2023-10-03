package conociendo.struts.formxmlvalidation.model;

import java.util.Arrays;

/**
 * Modela una persona que se registra.
 * 
 * @author Juan F. Ruiz
 *
 */
public class Persona {
	private String nombre;
	private String apellidos;
	private String deporte;
	private String genero;
	private String residencia;
	private boolean mayorde21;
	private String[] modelosCoche;
	private String correo;
    private String telefono;
    private Integer edad;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDeporte() {
		return deporte;
	}

	public void setDeporte(String deporte) {
		this.deporte = deporte;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getResidencia() {
		return residencia;
	}

	public void setResidencia(String residencia) {
		this.residencia = residencia;
	}

	public boolean isMayorde21() {
		return mayorde21;
	}

	public void setMayorde21(boolean mayorde21) {
		this.mayorde21 = mayorde21;
	}

	public String[] getModelosCoche() {
		return modelosCoche;
	}

	public void setModelosCoche(String[] modelosCoche) {
		this.modelosCoche = modelosCoche;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		final String SL = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("Persona [").append(SL).append("nombre='");
		builder.append(nombre);
		builder.append("',").append(SL).append(" apellidos='");
		builder.append(apellidos);
		builder.append("',").append(SL).append("deporte='");
		builder.append(deporte);
		builder.append("',").append(SL).append(" genero='");
		builder.append(genero);
		builder.append("',").append(SL).append(" residencia='");
		builder.append(residencia);
		builder.append("',").append(SL).append("mayorde21='");
		builder.append(mayorde21);
		builder.append("',").append(SL).append(" modelosCoche='");
		builder.append(Arrays.toString(modelosCoche));
		builder.append("',").append(SL).append(" correo=");
		builder.append(correo);
		builder.append("',").append(SL).append(" telefono='");
		builder.append(telefono);
		builder.append("',").append(SL).append(" edad='");
		builder.append(edad);
		builder.append("']");
		return builder.toString();
	}

	
}