package name.ruiz.juanfco.importacsv.inicio;

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
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.CCAA;
import name.ruiz.juanfco.importacsv.modelo.Lugar;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;
import name.ruiz.juanfco.importacsv.modelo.Provincia;
import name.ruiz.juanfco.importacsv.modelo.TipoLugar;
import name.ruiz.juanfco.importacsv.servicio.CCAAServicio;
import name.ruiz.juanfco.importacsv.servicio.CCAAServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.ImportaCSVCCAA;
import name.ruiz.juanfco.importacsv.servicio.ImportaCSVPoblacion;
import name.ruiz.juanfco.importacsv.servicio.ImportaCSVProvincia;
import name.ruiz.juanfco.importacsv.servicio.ImportaComAutImpl;
import name.ruiz.juanfco.importacsv.servicio.ImportaPobImpl;
import name.ruiz.juanfco.importacsv.servicio.ImportaProImpl;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesServicio;
import name.ruiz.juanfco.importacsv.servicio.PoblacionesServicioImpl;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaServicio;
import name.ruiz.juanfco.importacsv.servicio.ProvinciaServicioImpl;

/**
 *
 * @author hamfree
 */
public class Aplicacion {

    private String rutaConfiguracion;

    public static void main(String[] args) throws ConfiguracionException {
        int codigoSalida;
        if (args != null && args.length > 0) {
            Aplicacion app = new Aplicacion(args[0]);
            codigoSalida = app.ejecutaProceso(app.getRutaConfiguracion());
        } else {
            codigoSalida = 0;
            System.out.println("Necesito la ruta a un fichero de propiedades con la "
                    + "configuracion de conexion a la BBDD y el fichero CSV");
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
     * @throws name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException
     */
    public int ejecutaProceso(String rutaProperties) throws ConfiguracionException {
        int errores = 0;
        StringBuilder sb = new StringBuilder();
        List<Poblacion> poblaciones;
        List<CCAA> comunidadesAutonomas;
        List<Provincia> provincias;
        Properties p;

        p = obtenConfiguracion(rutaProperties);
        if (p != null) {
            muestraConfiguracion(p);
            String tipo = p.getProperty("tipo");

            switch (tipo) {
                case "CCAA":
                    comunidadesAutonomas = importaComunidadesAutonomasDelFichero(p);
                    if (comunidadesAutonomas != null && comunidadesAutonomas.size() > 0) {
                        sb.append("Se han importado ")
                                .append(comunidadesAutonomas.size())
                                .append(" comunidades autonomas del fichero '")
                                .append(p.getProperty("fichero"));
                        System.out.println(sb.toString());
                        muestraComunidadesAutonomas(comunidadesAutonomas);
                        if (!importaComunidadesAutonomasEnLaBBDD(comunidadesAutonomas, p, null)) {
                            errores++;
                            System.out.println("Importacion en la BD: Con errores.");
                        } else {
                            System.out.println("Importacion en la BD: OK");
                        }
                    } else {
                        System.out.println("*NO* se importaron comunidades autonomas del fichero: ERROR");
                        errores++;
                    }
                    break;
                case "PROVINCIA":
                    provincias = importaProvinciasDelFichero(p);
                    if (provincias != null && provincias.size() > 0) {
                        sb.append("Se han importado ")
                                .append(provincias.size())
                                .append(" provincias del fichero '")
                                .append(p.getProperty("fichero"));
                        System.out.println(sb.toString());
                        muestraProvincias(provincias);
                        if (!importaProvinciasEnLaBBDD(provincias, p, null)) {
                            errores++;
                            System.out.println("Importacion en la BD: Con errores.");
                        } else {
                            System.out.println("Importacion en la BD: OK");
                        }
                    } else {
                        System.out.println("*NO* se importaron provincias del fichero: ERROR");
                        errores++;
                    }
                    break;
                case "POBLACION":
                    poblaciones = importaPoblacionesDelFichero(p);
                    if (poblaciones != null && poblaciones.size() > 0) {
                        sb.append("Se han importado ")
                                .append(poblaciones.size())
                                .append(" poblaciones del fichero '")
                                .append(p.getProperty("fichero"));
                        System.out.println(sb.toString());
                        muestraPoblaciones(poblaciones);
                        if (!importaPoblacionesEnLaBBDD(poblaciones, p, null)) {
                            errores++;
                            System.out.println("Importacion en la BD: Con errores.");
                        } else {
                            System.out.println("Importacion en la BD: OK");
                        }
                    } else {
                        System.out.println("*NO* se importaron poblaciones del fichero: ERROR");
                        errores++;
                    }
                    break;

                default:
                    sb.append("Tipo no reconocido: '")
                            .append(p.getProperty("tipo"))
                            .append("'. Tipos permitidos : ");
                    for (TipoLugar tp : TipoLugar.values()) {
                        sb.append(tp)
                                .append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    throw new ConfiguracionException(sb.toString());
            }
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
        sb.append("Configuracion que se aplicara en la importacion").append(SL)
                .append("-----------------------------------------------").append(SL);
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

    private void muestraComunidadesAutonomas(List<CCAA> comunidadesAutonomas) {
        int i = 0;
        for (Lugar ccaa : comunidadesAutonomas) {
            System.out.println(String.valueOf(++i) + " : " + ccaa.toString());
        }
    }

    private void muestraProvincias(List<Provincia> provincias) {
        int i = 0;
        for (Lugar prov : provincias) {
            System.out.println(String.valueOf(++i) + " : " + prov.toString());
        }
    }

    private boolean importaProvinciasEnLaBBDD(List<Provincia> provincias, Properties prop, String jdni) {
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

    private boolean importaComunidadesAutonomasEnLaBBDD(List<CCAA> comunidades, Properties prop, String jdni) {
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
            if (comunidades != null && comunidades.size() > 0) {
                for (CCAA ca : comunidades) {
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

    private boolean importaPoblacionesEnLaBBDD(List<Poblacion> poblaciones, Properties prop, String jdni) {
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
        ImportaCSVPoblacion importador = new ImportaPobImpl();
        List<Poblacion> alPoblaciones = importador.importa(ficheroCsv, "Windows-1252", ";", false);
        return alPoblaciones;
    }

    /**
     *
     * @param p
     * @return
     */
    private List<CCAA> importaComunidadesAutonomasDelFichero(Properties p) {
        Path path = Paths.get(p.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        ImportaCSVCCAA importador = new ImportaComAutImpl();
        List<CCAA> alCCAA = importador.importa(ficheroCsv, "Windows-1252", ",", true);
        return alCCAA;
    }

    private List<Provincia> importaProvinciasDelFichero(Properties p) {
        Path path = Paths.get(p.getProperty("fichero"));
        File ficheroCsv = path.toFile();
        ImportaCSVProvincia importador = new ImportaProImpl();
        List<Provincia> alProvs = importador.importa(ficheroCsv, "Windows-1252", ",", true);
        return alProvs;
    }
}
