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
public class CCAA {
    private String idccaa;
    private String nombre;

    public CCAA() {
    }

    public CCAA(String idccaa, String nombre) {
        this.idccaa = idccaa;
        this.nombre = nombre;
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
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idccaa);
        hash = 89 * hash + Objects.hashCode(this.nombre);
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
        final CCAA other = (CCAA) obj;
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
        return "CCAA{" + "idccaa=" + idccaa + ", nombre=" + nombre + '}';
    }

}
