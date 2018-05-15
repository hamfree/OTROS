/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.modelo;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author hamfree
 */
public class Lugar implements Serializable {

    private static final long serialVersionUID = -1876554413054446258L;
    private static final Logger LOG = Logger.getLogger(Lugar.class.getName());

    private String nombre;

    public Lugar() {
    }

    public Lugar(String unNombre) {
        this.nombre = unNombre;
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
        hash = 79 * hash + Objects.hashCode(this.nombre);
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
        final Lugar other = (Lugar) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lugar{" + "nombre=" + nombre + '}';
    }

}
