/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.cgi;

import java.util.List;
import java.util.Map;

/**
 *
 * @author hamfree
 */
public class MiClase {
    protected List<String> lista;
    protected Map<Number, Object> mapa;

    public MiClase(List<String> lista, Map<Number, Object> mapa) {
        this.lista = lista;
        this.mapa = mapa;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

    public Map<Number, Object> getMapa() {
        return mapa;
    }

    public void setMapa(Map<Number, Object> mapa) {
        this.mapa = mapa;
    }

}
