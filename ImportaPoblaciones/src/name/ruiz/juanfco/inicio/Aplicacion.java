package name.ruiz.juanfco.inicio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.ruiz.juanfco.excepciones.ConfiguracionException;
import name.ruiz.juanfco.modelo.Poblacion;
import name.ruiz.juanfco.servicio.ImportaCSV;
import name.ruiz.juanfco.servicio.ImportaCSVimpl;
import name.ruiz.juanfco.servicio.PoblacionesServicio;
import name.ruiz.juanfco.servicio.PoblacionesServicioImpl;

/**
 *
 * @author hamfree
 */
public class Aplicacion {

    private String rutaConfiguracion;

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        int codigoSalida = 0;
        if (args != null && args.length > 0) {
            Aplicacion app = new Aplicacion(args[0]);
            codigoSalida = app.ejecutaProceso(app.getRutaConfiguracion());
        } else {
            codigoSalida = 0;
            System.out.println("Necesito la ruta a un fichero de propiedades con la "
                    + "configuración de conexion a la BBDD y el fichero CSV");
        }
        System.exit(codigoSalida);
    }

    public Aplicacion() {
    }

    public Aplicacion(String rutaConfiguracion) {
        this.rutaConfiguracion = rutaConfiguracion;
    }

    /**
     *
     * @param rutaProperties
     * @return
     */
    public int ejecutaProceso(String rutaProperties) {
        StringBuilder sb = new StringBuilder();
        int errores = 0;
        Properties p = obtenConfiguracion(rutaProperties);
        if (p != null) {
            muestraConfiguracion(p);
            List<Poblacion> poblaciones = importaPoblacionesDelFichero(p);
            if (poblaciones != null && poblaciones.size() > 0) {
                sb.append("Se han importado ")
                        .append(poblaciones.size())
                        .append(" poblaciones del fichero '")
                        .append(p.getProperty("fichero"));
                System.out.println(sb.toString());
                //muestraPoblaciones(poblaciones);
                if (!importaEnLaBBDD(poblaciones, p, null)) {
                    errores++;
                    System.out.println("Importacion en la BD: Con errores.");
                } else {
                    System.out.println("Importacion en la BD: OK");
                }
            } else {
                System.out.println("*NO* se importaron poblaciones del fichero: ERROR");
                errores++;
            }
        } else {
            errores++;
        }

        return errores;
    }

    public String getRutaConfiguracion() {
        return rutaConfiguracion;
    }

    public void setRutaConfiguracion(String rutaConfiguracion) {
        this.rutaConfiguracion = rutaConfiguracion;
    }

    /**
     *
     * @param p
     */
    public void muestraConfiguracion(Properties p) {
        StringBuilder sb = new StringBuilder();
        final String SL = System.getProperty("line.separator");
        for (Map.Entry ent : p.entrySet()) {
            String clave = (String) ent.getKey();
            String valor = (String) ent.getValue();
            sb.append(clave).append(" = ").append(valor).append(SL);
        }
        System.out.println(sb.toString());
    }

    /**
     *
     * @param poblaciones
     */
    private void muestraPoblaciones(List<Poblacion> poblaciones) {
        int i = 0;
        for (Poblacion pob : poblaciones) {
            System.out.println(String.valueOf(++i) + " : " + pob.toString());
        }
    }

    private boolean importaEnLaBBDD(List<Poblacion> poblaciones, Properties prop, String jdni) {
        int poblacionesImportadas = 0;
        int erroresImportacion = 0;
        PoblacionesServicio pobServ = null;
        try {
            if (prop != null) {
                pobServ = (PoblacionesServicio) new PoblacionesServicioImpl(prop);
            } else {
                pobServ = (PoblacionesServicio) new PoblacionesServicioImpl(jdni);
            }
        } catch (ConfiguracionException ex) {
            Logger.getLogger(Aplicacion.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        if (poblaciones != null && poblaciones.size() > 0) {
            for (Poblacion p : poblaciones) {
                if (pobServ.inserta(p)) {
                    poblacionesImportadas++;
                } else {
                    erroresImportacion++;
                }
            }
            System.out.println("Poblaciones importadas...: " + poblacionesImportadas);
            System.out.println("Errores importación......: " + erroresImportacion);
            if (erroresImportacion > 0) {
                return false;
            }
        } else {
            System.out.println("No hay poblaciones para importar en la BD.");
            return false;
        }
        return true;
    }

    /**
     *
     * @param rutaProperties
     * @return
     */
    private Properties obtenConfiguracion(String rutaProperties) {
        Properties p = null;
        if (rutaProperties != null && rutaProperties.length() > 0) {
            InputStream is = null;
            try {
                Path path = Paths.get(rutaProperties);
                File f = path.toFile();
                p = new Properties();
                is = new FileInputStream(f);
                p.load(is);
                is.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Aplicacion.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(Aplicacion.class
                        .getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (is != null) {
                        is.close();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Aplicacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return p;
    }

    /**
     *
     * @param p
     * @return
     */
    private List<Poblacion> importaPoblacionesDelFichero(Properties p) {
        Path path = Paths.get(p.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        ImportaCSV importador = new ImportaCSVimpl();
        List<Poblacion> alPoblaciones = importador.importa(ficheroCsv, "Windows-1252", ";", false);
        return alPoblaciones;
    }
}
