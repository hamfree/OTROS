package name.ruiz.juanfco.servicio;

import java.io.File;
import java.util.List;
import name.ruiz.juanfco.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public interface ImportaCSV {
    public List<Poblacion> importa(File fcsv, String codificacion, String delimitador, boolean entrecomillado);
}
