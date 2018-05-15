/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.servicio;

import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public interface ProvinciaServicio {
    public Provincia busca(String idProvincia);

    public boolean inserta(Provincia provincia);

    public boolean elimina(String idProvincia);

    public boolean modifica(Provincia provincia);
}
