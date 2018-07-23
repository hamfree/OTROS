/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.controlador;

import java.util.List;
import java.util.Properties;
import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public interface CCAAControlador {

    public List<CCAA> importarCCAADelCSV(Properties prop);

    public void mostrarCCAA(List<CCAA> comunidadesAutonomas);

    public void consultarCCAA(Properties prop, String jdni);

    public boolean insertarCCAA(List<CCAA> comunidadesAutonomas, Properties prop, String jdni);

    public boolean modificarCCAA(List<CCAA> comunidadesAutonomas, Properties prop, String jdni);

    public boolean borrarCCAA(Properties prop, String jdni);
}
