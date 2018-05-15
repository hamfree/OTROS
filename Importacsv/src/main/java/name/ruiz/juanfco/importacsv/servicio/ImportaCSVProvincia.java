/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.servicio;

import java.io.File;
import java.util.List;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public interface ImportaCSVProvincia {
    public List<Provincia> importa(File fcsv, String codificacion, String delimitador, boolean entrecomillado);
}
