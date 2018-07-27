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
import name.ruiz.juanfco.importacsv.modelo.Lugar;
import name.ruiz.juanfco.importacsv.modelo.Provincia;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaFicheroServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaServicio;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaFicheroServicio;

/**
 *
 * @author hamfree
 */
public class ProvinciaControladorImpl implements ProvinciaControlador {

    private static final Logger LOG = Logger.getLogger(ProvinciaControladorImpl.class.getName());

    @Override
    public List<Provincia> importarProvinciaDelCSV(Properties prop) {
        Path path = Paths.get(prop.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        ProvinciaFicheroServicio importador = new ProvinciaFicheroServicioImpl();
        List<Provincia> alProvs = importador.importa(ficheroCsv, "Windows-1252", ",", true);
        return alProvs;
    }

    @Override
    public void mostrarProvincia(List<Provincia> provincias) {
        int i = 0;
        for (Lugar prov : provincias) {
            System.out.println(String.valueOf(++i) + " : " + prov.toString());
        }
    }

    @Override
    public void consultarProvincia(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La consulta  de provincias no está soportada aún.");
    }

    @Override
    public boolean insertarProvincia(List<Provincia> provincias, Properties prop, String jdni) {
        int provinciasImportadas = 0;
        int errores = 0;
        ProvinciaServicio provServ;

        try {
            if (prop != null) {
                provServ = new ProvinciaServicioImpl(prop);
            } else {
                provServ = new ProvinciaServicioImpl(jdni);
            }
        } catch (ConfiguracionException ex) {
            Logger.getLogger(Aplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        try {
            if (provincias != null && provincias.size() > 0) {
                for (Provincia p : provincias) {
                    if (provServ.inserta(p)) {
                        provinciasImportadas++;
                    } else {
                        errores++;
                    }
                }
                System.out.println("Provincias importadas...: " + provinciasImportadas);
                System.out.println("Errores importacion.....: " + errores);
                if (errores > 0) {
                    return false;
                }
            } else {
                System.out.println("No hay provincias para importar en la BD.");
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
    public boolean modificarProvincia(List<Provincia> provincias, Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: La modificación de provincias no está soportada aún.");
    }

    @Override
    public boolean borrarProvincia(Properties prop, String jdni) {
        throw new UnsupportedOperationException(" ERROR: El borrado de provincias no está soportada aún.");
    }

}
