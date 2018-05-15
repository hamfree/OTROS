package name.ruiz.juanfco.importacsv.servicio;

import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public interface CCAAServicio {
    public CCAA busca(String idCCAA);

    public boolean inserta(CCAA ccaa);

    public boolean elimina(String idCCAA);

    public boolean modifica(CCAA ccaa);
}
