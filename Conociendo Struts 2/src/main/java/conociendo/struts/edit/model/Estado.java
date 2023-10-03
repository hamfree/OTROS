package conociendo.struts.edit.model;

public class Estado {

	private String abrevEstado;

	private String nombreEstado;

	public Estado(String abrevEstado, String nombreEstado) {

		this.abrevEstado = abrevEstado;
		this.nombreEstado = nombreEstado;

	}

	public String getAbrevEstado() {
		return abrevEstado;
	}

	public void setAbrevEstado(String abrevEstado) {
		this.abrevEstado = abrevEstado;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Estado [abrevEstado=");
		builder.append(abrevEstado);
		builder.append(", nombreEstado=");
		builder.append(nombreEstado);
		builder.append("]");
		return builder.toString();
	}

}
