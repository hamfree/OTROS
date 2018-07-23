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
import name.ruiz.juanfco.importacsv.controlador.CCAAControlador;
import name.ruiz.juanfco.importacsv.controlador.CCAAControladorImpl;
import name.ruiz.juanfco.importacsv.controlador.PoblacionControlador;
import name.ruiz.juanfco.importacsv.controlador.PoblacionControladorImpl;
import name.ruiz.juanfco.importacsv.controlador.ProvinciaControlador;
import name.ruiz.juanfco.importacsv.controlador.ProvinciaControladorImpl;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.CCAA;
import name.ruiz.juanfco.importacsv.modelo.Operacion;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;
import name.ruiz.juanfco.importacsv.modelo.Provincia;
import name.ruiz.juanfco.importacsv.modelo.TipoLugar;

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
        boolean esOperacionValida = false;

        p = obtenConfiguracion(rutaProperties);
        if (p != null) {
            muestraConfiguracion(p);
            String operacion = p.getProperty("operacion");

            for (Operacion op : Operacion.values()) {
                if (operacion.equalsIgnoreCase(op.toString())) {
                    esOperacionValida = true;
                    break;
                }
            }

            if (!esOperacionValida) {
                sb.append("Operacion no reconocida: '")
                        .append(operacion)
                        .append("'. Operaciones permitidas : ");
                for (Operacion op : Operacion.values()) {
                    sb.append(op)
                            .append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                throw new ConfiguracionException(sb.toString());
            }

            String tipo = p.getProperty("tipo");
            if (tipo.equalsIgnoreCase(TipoLugar.CCAA.toString())) {
                CCAAControlador ccaaCtl = new CCAAControladorImpl();
                comunidadesAutonomas = ccaaCtl.importarCCAADelCSV(p);
                if (comunidadesAutonomas != null && comunidadesAutonomas.size() > 0) {
                    sb.append("Se han importado ")
                            .append(comunidadesAutonomas.size())
                            .append(" comunidades autonomas del fichero '")
                            .append(p.getProperty("fichero"));
                    System.out.println(sb.toString());
                    ccaaCtl.mostrarCCAA(comunidadesAutonomas);

                    if (operacion.equalsIgnoreCase(Operacion.INSERTAR.toString())) {
                        if (!ccaaCtl.insertarCCAA(comunidadesAutonomas, p, null)) {
                            errores++;
                            System.out.println("Importacion en la BD: Con errores.");
                        } else {
                            System.out.println("Importacion en la BD: OK");
                        }
                    } else if (operacion.equalsIgnoreCase(Operacion.CONSULTAR.toString())) {
                        ccaaCtl.consultarCCAA(p, null);
                    } else if (operacion.equalsIgnoreCase(Operacion.BORRAR.toString())) {
                        ccaaCtl.borrarCCAA(p, null);
                    }

                } else {
                    System.out.println("*NO* se importaron comunidades autonomas del fichero: ERROR");
                    errores++;
                }
            } else if (tipo.equalsIgnoreCase(TipoLugar.POBLACION.toString())) {
                PoblacionControlador poblacionCtrl = new PoblacionControladorImpl();
                poblaciones = poblacionCtrl.importarPoblacionesDelCSV(p);
                if (poblaciones != null && poblaciones.size() > 0) {
                    sb.append("Se han importado ")
                            .append(poblaciones.size())
                            .append(" poblaciones del fichero '")
                            .append(p.getProperty("fichero"));
                    System.out.println(sb.toString());
                    poblacionCtrl.mostrarPoblaciones(poblaciones);
                    if (!poblacionCtrl.insertarPoblaciones(poblaciones, p, tipo)) {
                        errores++;
                        System.out.println("Importacion en la BD: Con errores.");
                    } else {
                        System.out.println("Importacion en la BD: OK");
                    }
                } else {
                    System.out.println("*NO* se importaron poblaciones del fichero: ERROR");
                    errores++;
                }
            } else if (tipo.equalsIgnoreCase(TipoLugar.PROVINCIA.toString())) {
                ProvinciaControlador provinciaCtrl = new ProvinciaControladorImpl();

                provincias = provinciaCtrl.importarProvinciaDelCSV(p);
                if (provincias != null && provincias.size() > 0) {
                    sb.append("Se han importado ")
                            .append(provincias.size())
                            .append(" provincias del fichero '")
                            .append(p.getProperty("fichero"));
                    System.out.println(sb.toString());
                    provinciaCtrl.mostrarProvincia(provincias);
                    if (!provinciaCtrl.insertarProvincia(provincias, p, null)) {
                        errores++;
                        System.out.println("Importacion en la BD: Con errores.");
                    } else {
                        System.out.println("Importacion en la BD: OK");
                    }
                } else {
                    System.out.println("*NO* se importaron provincias del fichero: ERROR");
                    errores++;
                }
            } else {
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
}
