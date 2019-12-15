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
import java.util.Map.Entry;
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
import name.ruiz.juanfco.importacsv.herramientas.Constantes;
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
    private static final Logger LOG = Logger.getLogger(Aplicacion.class.getName());

    public static void main(String[] args) throws ConfiguracionException {
        int codigoSalida;
        StringBuilder sb = new StringBuilder();
        if (args != null && args.length > 0) {
            Aplicacion app = new Aplicacion(args[0]);
            codigoSalida = app.ejecutaProceso(app.getRutaConfiguracion());
        } else {
            codigoSalida = 1;
            sb.append("Error: se requiere la ruta a un fichero de propiedades ")
                    .append("con la ")
                    .append("configuracion de conexion a la BBDD y el fichero CSV");
            System.out.println(sb.toString());
        }
        String error = codigoSalida > 1 ? " errores" : " error";
        sb.setLength(0);
        sb.append("La aplicación termina con ")
                .append(codigoSalida)
                .append(error)
                .append(".");
        System.out.println(sb.toString());
        System.exit(codigoSalida);
    }

    public Aplicacion() {
    }

    public Aplicacion(String rutaConfiguracion) {
        this.rutaConfiguracion = rutaConfiguracion;
    }

    public String getRutaConfiguracion() {
        return rutaConfiguracion;
    }

    public void setRutaConfiguracion(String rutaConfiguracion) {
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

        List<Provincia> provincias;
        Properties p;
        boolean esOperacionValida = false;
        boolean esTipoValido = false;

        p = obtenConfiguracion(rutaProperties);
        if (p != null) {
            muestraConfiguracion(p);
            String operacion = p.getProperty(Constantes.OPERACION);

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

            for (TipoLugar tp : TipoLugar.values()) {
                if (tipo.equalsIgnoreCase(tp.toString())) {
                    esTipoValido = true;
                    break;
                }
            }

            if (!esTipoValido) {
                sb.append("Tipo no reconocido: '")
                        .append(p.getProperty(Constantes.TIPO))
                        .append("'. Tipos permitidos : ");
                for (TipoLugar tp : TipoLugar.values()) {
                    sb.append(tp)
                            .append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                throw new ConfiguracionException(sb.toString());
            }

            if (tipo.equalsIgnoreCase(TipoLugar.CCAA.toString())) {
                errores = procesaComunidadesAutonomas(p);
            } else if (tipo.equalsIgnoreCase(TipoLugar.POBLACION.toString())) {
                errores = procesaPoblaciones(p);
            } else if (tipo.equalsIgnoreCase(TipoLugar.PROVINCIA.toString())) {
                errores = procesaProvincias(p);
            }
        }
        return errores;
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
        for (Entry<Object, Object> ent : p.entrySet()) {
            String clave = (String) ent.getKey();
            String valor = (String) ent.getValue();
            sb.append(clave).append(" = ").append(valor).append(SL);
        }
        System.out.println(sb.toString());
    }

    /**
     *
     * @param p
     * @return
     */
    public int procesaComunidadesAutonomas(Properties p) {
        int errores = 0;
        StringBuilder sb = new StringBuilder();
        String operacion = p.getProperty("operacion");
        CCAAControlador ccaaCtl = new CCAAControladorImpl();
        List<CCAA> comunidadesAutonomas;

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
                if (!ccaaCtl.borrarCCAA(p, null)) {
                    errores++;
                }
            } else if (operacion.equalsIgnoreCase(Operacion.MODIFICAR.toString())) {
                if (!ccaaCtl.modificarCCAA(comunidadesAutonomas, p, null)) {
                    errores++;
                }
            }

        } else {
            System.out.println("*NO* se importaron comunidades autonomas del fichero: ERROR");
            errores++;
        }
        return errores;
    }

    /**
     *
     * @param p
     * @return
     */
    public int procesaProvincias(Properties p) {
        int errores = 0;
        StringBuilder sb = new StringBuilder();
        String operacion = p.getProperty("operacion");
        List<Provincia> provincias;
        ProvinciaControlador provinciaCtrl = new ProvinciaControladorImpl();

        // Importamos las provincias del fichero.
        provincias = provinciaCtrl.importarProvinciaDelCSV(p);

        if (provincias != null && provincias.size() > 0) {
            sb.append("Se han importado ")
                    .append(provincias.size())
                    .append(" provincias del fichero '")
                    .append(p.getProperty("fichero"));
            System.out.println(sb.toString());

            // Mostramos las provincias importadas del fichero.
            provinciaCtrl.mostrarProvincia(provincias);

            // Vemos que operación debemos realizar.
            if (operacion.equalsIgnoreCase(Operacion.INSERTAR.toString())) {
                if (!provinciaCtrl.insertarProvincia(provincias, p, null)) {
                    errores++;
                    System.out.println("Importacion en la BD: Con errores.");
                } else {
                    System.out.println("Importacion en la BD: OK");
                }
            } else if (operacion.equalsIgnoreCase(Operacion.CONSULTAR.toString())) {
                provinciaCtrl.consultarProvincia(p, null);
            } else if (operacion.equalsIgnoreCase(Operacion.BORRAR.toString())) {
                provinciaCtrl.borrarProvincia(p, null);
            } else if (operacion.equalsIgnoreCase(Operacion.MODIFICAR.toString())) {
                provinciaCtrl.modificarProvincia(provincias, p, null);
            }

        } else {
            System.out.println("*NO* se importaron provincias del fichero: ERROR");
            errores++;
        }
        return 0;

    }

    /**
     *
     * @param p
     * @return
     */
    public int procesaPoblaciones(Properties p) {
        int errores = 0;
        StringBuilder sb = new StringBuilder();
        String operacion = p.getProperty("operacion");
        String tipo = p.getProperty("tipo");
        List<Poblacion> poblaciones;
        PoblacionControlador poblacionCtrl = new PoblacionControladorImpl();

        poblaciones = poblacionCtrl.importarPoblacionesDelCSV(p);
        if (poblaciones != null && poblaciones.size() > 0) {
            sb.append("Se han importado ")
                    .append(poblaciones.size())
                    .append(" poblaciones del fichero '")
                    .append(p.getProperty("fichero"));
            System.out.println(sb.toString());

            poblacionCtrl.mostrarPoblaciones(poblaciones);

            // Vemos que operación debemos realizar.
            if (operacion.equalsIgnoreCase(Operacion.INSERTAR.toString())) {
                if (!poblacionCtrl.insertarPoblaciones(poblaciones, p, null)) {
                    errores++;
                    System.out.println("Importacion en la BD: Con errores.");
                } else {
                    System.out.println("Importacion en la BD: OK");
                }
            } else if (operacion.equalsIgnoreCase(Operacion.CONSULTAR.toString())) {
                poblacionCtrl.consultarPoblaciones(p, null);
            } else if (operacion.equalsIgnoreCase(Operacion.BORRAR.toString())) {
                poblacionCtrl.borrarPoblaciones(p, null);
            } else if (operacion.equalsIgnoreCase(Operacion.MODIFICAR.toString())) {
                poblacionCtrl.modificarPoblaciones(poblaciones, p, null);
            }

        } else {
            System.out.println("*NO* se importaron poblaciones del fichero: ERROR");
            errores++;
        }
        return errores;
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
