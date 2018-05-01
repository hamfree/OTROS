package name.ruiz.juanfco.modelo;

import java.util.Objects;

/**
 *
 * @author hamfree
 */
public class Poblacion {

    private String idPoblacion;
    private String idProvincia;
    private String idCCAA;
    private String poblacion;

    public Poblacion() {
    }

    public Poblacion(String idPoblacion, String idProvincia, String idCCAA, String poblacion) {
        this.idPoblacion = idPoblacion;
        this.idProvincia = idProvincia;
        this.idCCAA = idCCAA;
        this.poblacion = poblacion;
    }

    public String getIdPoblacion() {
        return idPoblacion;
    }

    public void setIdPoblacion(String idPoblacion) {
        this.idPoblacion = idPoblacion;
    }

    public String getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getIdCCAA() {
        return idCCAA;
    }

    public void setIdCCAA(String idCCAA) {
        this.idCCAA = idCCAA;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idPoblacion);
        hash = 53 * hash + Objects.hashCode(this.idProvincia);
        hash = 53 * hash + Objects.hashCode(this.idCCAA);
        hash = 53 * hash + Objects.hashCode(this.poblacion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Poblacion other = (Poblacion) obj;
        if (!Objects.equals(this.idPoblacion, other.idPoblacion)) {
            return false;
        }
        if (!Objects.equals(this.idProvincia, other.idProvincia)) {
            return false;
        }
        if (!Objects.equals(this.idCCAA, other.idCCAA)) {
            return false;
        }
        return Objects.equals(this.poblacion, other.poblacion);
    }

    @Override
    public String toString() {
        return "Poblacion{" + "idPoblacion=" + idPoblacion + ", idProvincia=" + idProvincia + ", idCCAA=" + idCCAA + ", poblacion=" + poblacion + '}';
    }

}
