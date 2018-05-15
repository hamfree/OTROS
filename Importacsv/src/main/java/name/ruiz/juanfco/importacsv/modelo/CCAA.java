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
public class CCAA extends Lugar {

    private static final long serialVersionUID = 4345723544931154581L;
    private String idccaa;
    //private String nombre;

    public CCAA() {
        super();
    }

    public CCAA(String idccaa, String nombre) {
        super(nombre);
        this.idccaa = idccaa;

    }

    public String getIdccaa() {
        return idccaa;
    }

    public void setIdccaa(String idccaa) {
        this.idccaa = idccaa;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idccaa);
        hash = 59 * hash + Objects.hashCode(this.getNombre());
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
        if (!Objects.equals(this.getNombre(), other.getNombre())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CCAA{" + "idccaa='" + idccaa + "', nombre='" + getNombre() + "'}";
    }


}
