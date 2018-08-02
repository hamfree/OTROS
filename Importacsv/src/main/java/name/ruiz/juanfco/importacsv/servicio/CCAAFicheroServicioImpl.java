/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.servicio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.herramientas.Util;
import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public class CCAAFicheroServicioImpl implements CCAAFicheroServicio {

    private static final Logger LOG = Logger.getLogger(CCAAFicheroServicioImpl.class.getName());

    /**
     *
     * @param fcsv
     * @param codificacion
     * @param delimitador
     * @param entrecomillado
     * @return
     */
    @Override
    public List<CCAA> importa(File fcsv, String codificacion, String delimitador, boolean entrecomillado) {
        StringBuilder sb = new StringBuilder();
        ArrayList<CCAA> alComAut = null;
        int contador = 0;
        boolean existeFichero;
        boolean sePuedeLeer;

        try {
            //Activamos el log
            Util.activaLog(LOG, "./ccaa.log", 10485760, 3, true);
        } catch (SecurityException | IOException ex) {
            LOG.severe(ex.getLocalizedMessage());
        }

        // Validaciones
        if (fcsv == null) {
            sb.append("Parametro de fichero nulo.");
            LOG.log(Level.SEVERE, sb.toString());
            Util.impsl(false, sb.toString());
            return null;
        }

        try {
            existeFichero = fcsv.exists();
            if (existeFichero) {
                sePuedeLeer = fcsv.canRead();
                LOG.log(Level.INFO, "El fichero ''{0}'' existe y se puede leer.", fcsv.getName());
                Util.impsl(false, sb.toString());
            } else {
                sb = new StringBuilder();
                sb.append("El fichero ").append(fcsv.getName())
                        .append("' no se encuentra en '")
                        .append(fcsv.getPath()).append("'.");
                LOG.log(Level.SEVERE, sb.toString());
                Util.impsl(false, sb.toString());
                return null;
            }
        } catch (SecurityException ex) {
            LOG.log(Level.SEVERE, null, ex);
            Util.impsl(false, ex.getLocalizedMessage());
        }

        //Validamos el delimitador (el delimitador ES una expresion regular)
        if (delimitador == null || delimitador.length() == 0) {
            sb.append("Parametro ''delimitador'' nulo o vacío.");
            LOG.log(Level.SEVERE, sb.toString());
            Util.impsl(false, sb.toString());
            return null;
        }

        //Validamos la codificacion indicada para el fichero
        if (codificacion == null || codificacion.length() == 0) {
            sb.append("Parametro ''codificacion'' nulo o vacío.");
            LOG.log(Level.SEVERE, sb.toString());
            Util.impsl(false, sb.toString());
            return null;
        } else {
            List<Charset> lcs = Util.getAllCharsets();
            boolean esCodificacionValida = false;
            for (Charset cs : lcs) {
                if (codificacion.equalsIgnoreCase(cs.name())) {
                    esCodificacionValida = true;
                    sb.append("Codificacion '")
                            .append(codificacion)
                            .append("' encontrada en la JVM.");
                    LOG.log(Level.INFO, sb.toString());
                    Util.impsl(false, sb.toString());
                    break;
                }
            }
            if (!esCodificacionValida) {
                sb.append("Codificacion '")
                        .append(codificacion)
                        .append("' no reconocida.");
                LOG.log(Level.SEVERE, sb.toString());
                Util.impsl(false, sb.toString());
                return null;
            }
        }

        try {
            alComAut = new ArrayList<>();
            Scanner scanner = new Scanner(fcsv, codificacion);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                contador++;
                Scanner scLinea = new Scanner(linea);
                scLinea.useDelimiter(delimitador);
                if (scLinea.hasNext()) {
                    String idCCAA = scLinea.next();
                    String nombre = scLinea.next();

                    if (entrecomillado) {
                        idCCAA = idCCAA.replace("\"", "");
                        nombre = nombre.replace("\"", "");
                    }

                    CCAA ca = new CCAA(idCCAA, nombre);
                    alComAut.add(ca);
                }
            }
        } catch (FileNotFoundException ex) {
            LOG.severe(ex.getLocalizedMessage());
            Util.impsl(false, ex.getLocalizedMessage());
        } catch (NoSuchElementException ex) {
            sb = new StringBuilder();
            LOG.severe(ex.getLocalizedMessage());
            sb.append("Excepcion en la linea número ")
                    .append(contador)
                    .append(" del fichero CSV.")
                    .append(ex.getLocalizedMessage());
            Util.impsl(false, sb.toString());
            return alComAut;
        }
        return alComAut;
    }
}
