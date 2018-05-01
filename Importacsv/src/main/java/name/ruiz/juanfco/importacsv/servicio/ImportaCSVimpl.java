package name.ruiz.juanfco.importacsv.servicio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import name.ruiz.juanfco.importacsv.herramientas.Util;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class ImportaCSVimpl implements ImportaCSV {

    static final Logger LOG = Logger.getLogger(ImportaCSVimpl.class.getName());

    @Override
    public List<Poblacion> importa(File fcsv, String codificacion, String delimitador, boolean entrecomillado) {
        ArrayList<Poblacion> alPoblaciones = null;
        int contador = 0;
        boolean existeFichero;
        boolean sePuedeLeer;

        // Validaciones
        if (fcsv == null) {
            LOG.log(Level.SEVERE, "Parametro de fichero nulo.");
            return null;
        }

        try {
            existeFichero = fcsv.exists();
            if (existeFichero) {
                sePuedeLeer = fcsv.canRead();
                LOG.log(Level.INFO, "El fichero ''{0}'' existe y se puede leer.", fcsv.getName());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("El fichero ").append(fcsv.getName())
                        .append("' no se encuentra en '")
                        .append(fcsv.getPath()).append("'.");
                LOG.log(Level.SEVERE, sb.toString());
                return null;
            }
        } catch (SecurityException ex) {
            Logger.getLogger(ImportaCSVimpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Validamos el delimitador (el delimitador ES una expresion regular)
        if (delimitador == null || delimitador.length() == 0) {
            LOG.log(Level.SEVERE, "Parametro ''delimitador'' nulo o vacío.");
            return null;
        }

        //Validamos la codificacion indicada para el fichero
        if (codificacion == null || codificacion.length() == 0) {
            LOG.log(Level.SEVERE, "Parametro ''codificacion'' nulo o vacío.");
            return null;
        } else {
            List<Charset> lcs = Util.getAllCharsets();
            boolean esCodificacionValida = false;
            for (Charset cs : lcs) {
                if (codificacion.equalsIgnoreCase(cs.name())) {
                    esCodificacionValida = true;
                    LOG.log(Level.INFO, "Codificacion ''{0}'' encontrada en la JVM.", codificacion);
                    break;
                }
            }
            if (!esCodificacionValida) {
                LOG.log(Level.SEVERE, "Codificacion ''{0}'' no reconocida.", codificacion);
                return null;
            }
        }

        try {

            alPoblaciones = new ArrayList<>();
            Scanner scanner = new Scanner(fcsv, codificacion);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                contador++;
                Scanner scLinea = new Scanner(linea);
                scLinea.useDelimiter(delimitador);
                if (scLinea.hasNext()) {
                    String idCCAA = scLinea.next();
                    String idProv = scLinea.next();
                    String idPobl = scLinea.next();

                    // El campo es un DC que no nos interesa...
                    String tmp = scLinea.next();

                    String poblacion = scLinea.next();

                    if (entrecomillado) {
                        idCCAA = idCCAA.replace("\"", "");
                        idProv = idProv.replace("\"", "");
                        idPobl = idPobl.replace("\"", "");
                        poblacion = poblacion.replace("\"", "");
                    }

                    Poblacion pob = new Poblacion(idPobl, idProv, idCCAA, poblacion);
                    alPoblaciones.add(pob);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportaCSVimpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchElementException ex) {
            Logger.getLogger(ImportaCSVimpl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Excepcion en la linea número " + contador + "del fichero CSV.");
            System.out.println(ex.getLocalizedMessage());
            return alPoblaciones;
        }

        return alPoblaciones;
    }

    private void debug()
            throws SecurityException, IOException {
        // creando manejador de archivo
        FileHandler fh = new FileHandler("./municipios.log", //pattern
                10485760, //limit
                3, // count
                true); //append
        fh.setLevel(Level.ALL); // level
        fh.setFormatter(new SimpleFormatter()); //formatter

        // agregar el manejador de archivo al LOG
        LOG.addHandler(fh);

        // el manejador de consola se agrega automaticamente, solo
        // cambiamos el nivel de detalle a desplegar
        LOG.getHandlers()[0].setLevel(Level.SEVERE);

        // se establece el nivel predeterminado global
        LOG.setLevel(Level.INFO);
    }
}
