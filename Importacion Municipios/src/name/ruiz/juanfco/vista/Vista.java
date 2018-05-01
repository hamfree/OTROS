/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.vista;

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
