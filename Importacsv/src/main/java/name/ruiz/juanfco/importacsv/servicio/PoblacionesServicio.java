package name.ruiz.juanfco.importacsv.servicio;

import name.ruiz.juanfco.importacsv.modelo.Poblacion;
/**
 *
 * @author hamfree
 */

public interface PoblacionesServicio {

    public Poblacion busca(String idPoblacion);

    public boolean inserta(Poblacion poblacion);

    public boolean elimina(String idPoblacion);

    public boolean modifica(Poblacion poblacion);
}
