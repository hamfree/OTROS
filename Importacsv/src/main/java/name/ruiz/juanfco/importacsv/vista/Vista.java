package name.ruiz.juanfco.importacsv.vista;

import java.util.List;
import java.util.Map;

/**
 *
 * @author hamfree
 */
public interface Vista {

    public StringBuffer exploraObjeto(Object obj);

    public StringBuffer exploraLista(List<?> lista, int nivel);

    public StringBuffer exploraMapa(Map<Object, Object> mapa, int nivel);

    public StringBuffer exploraObjetos(Object... obj);

    public StringBuffer exploraVector(Object[] vector, int nivel);
}
