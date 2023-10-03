package conociendo.struts.edit.model;

import java.util.Arrays;


/**
 * Modela una persona que se registra.
 * @author Juan F. Ruiz
 *
 */
public class Persona
{
    private String nombre;
    private String apellidos;
    private String deporte;
    private String genero;
    private String residencia;
    private boolean mayorde21;
    private String [] modelosCoche;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Persona [nombre=");
		builder.append(nombre);
		builder.append(", apellidos=");
		builder.append(apellidos);
		builder.append(", deporte=");
		builder.append(deporte);
		builder.append(", genero=");
		builder.append(genero);
		builder.append(", residencia=");
		builder.append(residencia);
		builder.append(", mayorde21=");
		builder.append(mayorde21);
		builder.append(", modelosCoche=");
		builder.append(Arrays.toString(modelosCoche));
		builder.append("]");
		return builder.toString();
	}
}