/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.controlador;

import java.util.List;
import java.util.Properties;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public interface ProvinciaControlador {

    public List<Provincia> importarProvinciaDelCSV(Properties prop);

    public void mostrarProvincia(List<Provincia> provincias);

    public void consultarProvincia(Properties prop, String jdni);

    public boolean insertarProvincia(List<Provincia> provincias, Properties prop, String jdni);

    public boolean modificarProvincia(List<Provincia> provincias, Properties prop, String jdni);

    public boolean borrarProvincia(Properties prop, String jdni);
}
