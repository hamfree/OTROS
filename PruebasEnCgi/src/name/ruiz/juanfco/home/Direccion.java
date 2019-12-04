package name.ruiz.juanfco.home;

import java.util.Objects;

public class Direccion {

    private TipoVia tipoVia;
    private String nombreVia;
    private Integer numeroVia;
    private String ciudad;
    private String pais;

    public Direccion() {
    }

    public Direccion(TipoVia tipoVia, String nombreVia, Integer numeroVia, String ciudad, String pais) {
        this.tipoVia = tipoVia;
        this.nombreVia = nombreVia;
        this.numeroVia = numeroVia;
        this.ciudad = ciudad;
        this.pais = pais;
    }

    public TipoVia getTipoVia() {
        return tipoVia;
    }

    public void setTipoVia(TipoVia tipoVia) {
        this.tipoVia = tipoVia;
    }

    public String getNombreVia() {
        return nombreVia;
    }

    public void setNombreVia(String nombreVia) {
        this.nombreVia = nombreVia;
    }

    public Integer getNumeroVia() {
        return numeroVia;
    }

    public void setNumeroVia(Integer numeroVia) {
        this.numeroVia = numeroVia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.tipoVia);
        hash = 23 * hash + Objects.hashCode(this.nombreVia);
        hash = 23 * hash + Objects.hashCode(this.numeroVia);
        hash = 23 * hash + Objects.hashCode(this.ciudad);
        hash = 23 * hash + Objects.hashCode(this.pais);
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
        final Direccion other = (Direccion) obj;
        if (!Objects.equals(this.nombreVia, other.nombreVia)) {
            return false;
        }
        if (!Objects.equals(this.ciudad, other.ciudad)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (this.tipoVia != other.tipoVia) {
            return false;
        }
        if (!Objects.equals(this.numeroVia, other.numeroVia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Direccion{" + "tipoVia=" + tipoVia + ", nombreVia=" + nombreVia + ", numeroVia=" + numeroVia + ", ciudad=" + ciudad + ", pais=" + pais + '}';
    }

}
