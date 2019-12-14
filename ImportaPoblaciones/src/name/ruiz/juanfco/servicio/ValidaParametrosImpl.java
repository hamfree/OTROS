package name.ruiz.juanfco.servicio;

import java.util.Properties;

/**
 *
 * @author hamfree
 */
public class ValidaParametrosImpl implements ValidaParametros {

    @Override
    public boolean validaJDBC(Properties jdbc) {
        //TODO: Por hacer, por el momento devolvemos true para poder continuar
        return true;
    }

    @Override
    public boolean validaJNDI(String jndi) {
        //TODO: Por hacer, por el momento devolvemos true para poder continuar
        return true;
    }

}
