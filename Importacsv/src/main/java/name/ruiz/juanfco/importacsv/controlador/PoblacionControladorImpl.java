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
import name.ruiz.juanfco.importacsv.modelo.Poblacion;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesFicheroServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesServicio;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesFicheroServicio;

/**
 *
 * @author hamfree
 */
public class PoblacionControladorImpl implements PoblacionControlador {

    private static final Logger LOG = Logger.getLogger(PoblacionControladorImpl.class.getName());

    /**
     *
     * @param poblaciones
     */
    @Override
    public void mostrarPoblaciones(List<Poblacion> poblaciones) {
        //TODO: Este método usará los métodos de la vista para mostrar las
        // poblaciones
        int i = 0;
        for (Poblacion pob : poblaciones) {
            System.out.println(String.valueOf(++i) + " : " + pob.toString());
        }
    }

    /**
     *
     * @param prop
     * @param jdni
     */
    @Override
    public void consultarPoblaciones(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La consulta  de poblaciones no está soportada aún.");
    }

    /**
     *
     * @param poblaciones
     * @param prop
     * @param jdni
     * @return
     */
    @Override
    public boolean insertarPoblaciones(List<Poblacion> poblaciones, Properties prop, String jdni) {
        int poblacionesImportadas = 0;
        int erroresImportacion = 0;
        PoblacionesServicio pobServ;
        try {
            if (prop != null) {
                pobServ = new PoblacionesServicioImpl(prop);
            } else {
                pobServ = new PoblacionesServicioImpl(jdni);
            }
        } catch (ConfiguracionException ex) {
            Logger.getLogger(Aplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            if (poblaciones != null && poblaciones.size() > 0) {
                for (Poblacion p : poblaciones) {
                    if (pobServ.inserta(p)) {
                        poblacionesImportadas++;
                    } else {
                        erroresImportacion++;
                    }
                }
                System.out.println("Poblaciones importadas...: " + poblacionesImportadas);
                System.out.println("Errores importacion......: " + erroresImportacion);
                if (erroresImportacion > 0) {
                    return false;
                }
            } else {
                System.out.println("No hay poblaciones para importar en la BD.");
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

    /**
     *
     * @param poblaciones
     * @param prop
     * @param jdni
     * @return
     */
    @Override
    public boolean modificarPoblaciones(List<Poblacion> poblaciones, Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La modificación  de poblaciones no está soportada aún.");
    }

    /**
     *
     * @param prop
     * @param jdni
     * @return
     */
    @Override
    public boolean borrarPoblaciones(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La eliminación de poblaciones no está soportada aún.");
    }

    /**
     *
     * @param prop
     * @return
     */
    @Override
    public List<Poblacion> importarPoblacionesDelCSV(Properties prop) {
        Path path = Paths.get(prop.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        PoblacionesFicheroServicio importador = new PoblacionesFicheroServicioImpl();
        List<Poblacion> alPoblaciones = importador.importa(ficheroCsv, "Windows-1252", ";", true);
        return alPoblaciones;
    }

}
