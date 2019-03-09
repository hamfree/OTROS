/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.home;

/**
 *
 * @author hamfree
 */
public enum TipoVia {
    CALLE, AVENIDA, PASEO, CAMINO, TRAVESIA;

    @Override
    public String toString() {
        return "TipoVia{" + this.name() + '}';
    }

}
