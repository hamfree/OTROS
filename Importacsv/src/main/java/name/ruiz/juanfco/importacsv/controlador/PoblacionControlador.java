/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.controlador;

import java.util.List;
import java.util.Properties;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public interface PoblacionControlador {

    public List<Poblacion> importarPoblacionesDelCSV(Properties prop);

    public void mostrarPoblaciones(List<Poblacion> poblaciones);

    public void consultarPoblaciones(Properties prop, String jdni);

    public boolean insertarPoblaciones(List<Poblacion> poblaciones, Properties prop, String jdni);

    public boolean modificarPoblaciones(List<Poblacion> poblaciones, Properties prop, String jdni);

    public boolean borrarPoblaciones(Properties prop, String jdni);
}
