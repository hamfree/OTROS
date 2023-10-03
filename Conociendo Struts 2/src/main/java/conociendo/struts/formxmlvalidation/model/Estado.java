package conociendo.struts.formxmlvalidation.model;

public class Estado {

	private String abreviaturaEstado;
	private String nombreEstado;

	public Estado(String abrevEstado, String nombreEstado) {
		this.abreviaturaEstado = abrevEstado;
		this.nombreEstado = nombreEstado;
	}

	public void setAbreviaturaEstado(String stateAbbr) {
		this.abreviaturaEstado = stateAbbr;
	}

	public String getAbreviaturaEstado() {
		return abreviaturaEstado;
	}

	public void setNombreEstado(String stateName) {
		this.nombreEstado = stateName;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Estado [abreviaturaEstado=");
		builder.append(abreviaturaEstado);
		builder.append(", nombreEstado=");
		builder.append(nombreEstado);
		builder.append("]");
		return builder.toString();
	}

}
