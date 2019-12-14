/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.servicio;

import java.util.Properties;

/**
 *
 * @author hamfree
 */
public interface ValidaParametros {

    /**
     *
     * @param jdbc
     * @return
     */
    public boolean validaJDBC(Properties jdbc);

    public boolean validaJNDI(String jndi);
}
