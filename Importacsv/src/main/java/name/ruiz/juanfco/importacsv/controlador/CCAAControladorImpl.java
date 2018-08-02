/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.controlador;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.inicio.Aplicacion;
import name.ruiz.juanfco.importacsv.modelo.CCAA;
import name.ruiz.juanfco.importacsv.modelo.Lugar;
import name.ruiz.juanfco.importacsv.servicio.CCAAServicio;
import name.ruiz.juanfco.importacsv.servicio.CCAAServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.CCAAFicheroServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.CCAAFicheroServicio;

/**
 *
 * @author hamfree
 */
public class CCAAControladorImpl implements CCAAControlador {

    private static final Logger LOG = Logger.getLogger(CCAAControladorImpl.class.getName());

    @Override
    public List<CCAA> importarCCAADelCSV(Properties prop) {
        Path path = Paths.get(prop.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        CCAAFicheroServicio importador = new CCAAFicheroServicioImpl();
        List<CCAA> alCCAA = importador.importa(ficheroCsv, "Windows-1252", ",", true);
        return alCCAA;
    }

    @Override
    public void mostrarCCAA(List<CCAA> comunidadesAutonomas) {
        int i = 0;
        for (Lugar ccaa : comunidadesAutonomas) {
            System.out.println(String.valueOf(++i) + " : " + ccaa.toString());
        }
    }

    @Override
    public void consultarCCAA(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La consulta  de comunidades autónomas no está soportada aún.");
    }

    @Override
    public boolean insertarCCAA(List<CCAA> comunidadesAutonomas, Properties prop, String jdni) {
        int ccaaImportadas = 0;
        int errores = 0;
        CCAAServicio ccaaServ;

        try {
            if (prop != null) {
                ccaaServ = new CCAAServicioImpl(prop);
            } else {
                ccaaServ = new CCAAServicioImpl(jdni);
            }
        } catch (ConfiguracionException ex) {
            Logger.getLogger(Aplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            if (comunidadesAutonomas != null && comunidadesAutonomas.size() > 0) {
                for (CCAA ca : comunidadesAutonomas) {
                    if (ccaaServ.inserta(ca)) {
                        ccaaImportadas++;
                    } else {
                        errores++;
                    }
                }
                System.out.println("Comunidades Autonomas importadas...: " + ccaaImportadas);
                System.out.println("Errores importacion................: " + errores);
                if (errores > 0) {
                    return false;
                }
            } else {
                System.out.println("No hay comunidades autonomas para importar en la BD.");
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(Aplicacion.class
                    .getName()).log(Level.SEVERE, null, ex.getLocalizedMessage());
            System.out.println("ERROR: " + ex.getLocalizedMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean modificarCCAA(List<CCAA> comunidadesAutonomas, Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La modificación  de comunidades autónomas no está soportada aún.");
    }

    @Override
    public boolean borrarCCAA(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La eliminación de comunidades autónomas no está soportada aún.");
    }

}
