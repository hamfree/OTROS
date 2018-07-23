package name.ruiz.juanfco.importacsv.servicio;

import java.io.File;
import java.util.List;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public interface PoblacionesFicheroServicio {
    public List<Poblacion> importa(File fcsv, String codificacion, String delimitador, boolean entrecomillado);
}
