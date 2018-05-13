/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.modelo;

import java.util.Objects;

/**
 *
 * @author hamfree
 */
public class Provincia {
    private String idprovincias;
    private String idccaa;
    private String nombre;

    public Provincia() {
    }

    public Provincia(String idprovincias, String idccaa, String nombre) {
        this.idprovincias = idprovincias;
        this.idccaa = idccaa;
        this.nombre = nombre;
    }

    public String getIdprovincias() {
        return idprovincias;
    }

    public void setIdprovincias(String idprovincias) {
        this.idprovincias = idprovincias;
    }

    public String getIdccaa() {
        return idccaa;
    }

    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.idprovincias);
        hash = 23 * hash + Objects.hashCode(this.idccaa);
        hash = 23 * hash + Objects.hashCode(this.nombre);
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
        final Provincia other = (Provincia) obj;
        if (!Objects.equals(this.idprovincias, other.idprovincias)) {
            return false;
        }
        if (!Objects.equals(this.idccaa, other.idccaa)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Provincia{" + "idprovincias=" + idprovincias + ", idccaa=" + idccaa + ", nombre=" + nombre + '}';
    }

}
