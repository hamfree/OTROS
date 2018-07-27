package name.ruiz.juanfco.importacsv.servicio;

import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author hamfree
 */
public class ValidaParametrosImpl implements ValidaParametros {

    private static final Logger LOG = Logger.getLogger(ValidaParametrosImpl.class.getName());

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
