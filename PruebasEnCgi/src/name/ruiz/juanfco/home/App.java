package name.ruiz.juanfco.home;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;
import name.ruiz.juanfco.cgi.Utl;

/**
 *
 * @author operador
 *
 */
public class App {
    private static Logger log;

    public static void main(String[] args) {
        App miApp = new App();
        if (args.length == 1) {
            if (miApp.procesar(args[0])) {
                System.exit(0);
            } else {
                System.exit(-1);
            }
        } else {
            JFR.imp(true, 1, "Este programa necesita un argumento para ejecutarse.");
            miApp.muestraAyuda();
        }
    }

    /**
     *
     * @param argumento
     * @return
     */
    public boolean procesar(String argumento) {
        boolean terminaBien = false;
        try {

            if (argumento != null && !argumento.isEmpty()) {
                switch (argumento) {
                    case Constantes.AYUDA:
                        muestraAyuda();
                        break;
                    case Constantes.SELCOM:
                        muestraComandos(2);
                        String comando = recibeComando();
                        terminaBien = procesaComando(comando);
                        break;
                    default:
                        terminaBien = procesaComando(argumento);
                }

            } else {
                muestraError(argumento);
            }

        } catch (Exception ex) {
            log.severe(ex.getLocalizedMessage());
        }
        return terminaBien;
    }

    private boolean procesaComando(String argumento) {
        boolean terminaBien = false;
        if (argumento != null && !argumento.isEmpty()) {
            switch (argumento) {
                case Constantes.COLECCION2LISTA:
                    if (testColeccion2Lista()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.COMPARACIONES:
                    if (testComparaciones()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.EXTRAEDIGITOS:
                    if (testExtractDigits()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.EXTRAEDOBLEDECADENA:
                    if (testExtractDoubleFromString()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.EXTRAELARGODECADENA:
                    if (testExtractLongFromString()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.MAPA2LISTA:
                    if (testMapa2Lista()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.PROCESADUMMIES:
                    if (testProcesaDummies()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                case Constantes.REEMPLAZA:
                    if (testReplace()) {
                        acaboBien(argumento);
                        terminaBien = true;
                    } else {
                        acaboMal(argumento);
                    }
                    break;
                default:
                    muestraError(argumento);
                    terminaBien = false;
            }
        }
        return terminaBien;
    }

    private void acaboMal(String argumento) {
        JFR.imp(true, 0, "");
        JFR.imp(true, 1, "El test ", argumento, " acabo mal.");
    }

    private void muestraAyuda() {
        JFR.imp(true, 0, "App es una aplicación de Java que se usa para realizar test.");
        JFR.imp(true, 1, " Los comandos disponibles de APP son los siguientes:");
        for (int i = 0; i < Constantes.CONSTANTES.length; i++) {
            if (i < Constantes.CONSTANTES.length - 1) {
                JFR.imp(false, 0, Constantes.CONSTANTES[i], ",");
            } else {
                JFR.imp(false, 0, Constantes.CONSTANTES[i]);
            }
        }
    }

    private void muestraError(String argumento) {
        JFR.imp(true, 0, "No reconozco el comando '", argumento, "'. ");
        JFR.imp(true, 1, "Si quiere saber que tests puede ejecutar indique 'App ayuda'");
    }

    private void acaboBien(String argumento) {
        JFR.imp(true, 1, "");
        JFR.imp(true, 1, "El test ", argumento, " terminó bien.");
    }

    private void muestraComandos(int indiceComandoInicial) {
        JFR.imp(true, 1, " Los comandos disponibles de APP son los siguientes:");
        for (int i = indiceComandoInicial; i < Constantes.CONSTANTES.length; i++) {
            if (i < Constantes.CONSTANTES.length - 1) {
                JFR.imp(false, 0, Constantes.CONSTANTES[i], ",");
            } else {
                JFR.imp(false, 0, Constantes.CONSTANTES[i]);
            }
        }
    }

    private String recibeComando() throws Exception {
        JFR.imp(true, 1, "");
        JFR.imp(false, 1, "Indique su comando : ");
        return (Utl.read(true));
    }

    /*
     * A partir de aquí van los métodos que implementan los 'comandos' que
     * ejecutan los test
     */
    /**
     *
     * @param d
     */
    public boolean testReplace() {
        JFR.cabecera("testReplace", true);
        boolean esCorrecto = false;
        Calendar gc = GregorianCalendar.getInstance();
        gc.set(1969, 11, 22);

        Date fecha = gc.getTime();
        Integer edad = Integer.parseInt("48");

        Dummy d = new Dummy("Juan Francisco Ruiz", edad, fecha);
        JFR.imp(true, 1, "");
        JFR.imp(true, 1, "ANTES");

        String nombre = d.getNombre();
        System.out.println("nombre = " + nombre);
        nombre.replace('u', 'X');
        System.out.println("DESPUES SIN ASIGNACION");
        System.out.println("nombre = " + nombre);
        System.out.println("DESPUES CON ASIGNACION");
        nombre = nombre.replace('u', 'X');
        System.out.println("nombre = " + nombre);
        JFR.cabecera("testReplace", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean holaMundo() {
        JFR.cabecera("holaMundo", true);
        boolean esCorrecto = true;
        JFR.imp(true, 1, "");
        System.out.println("¡Hola mundo!");
        JFR.cabecera("holaMundo", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean testColeccion2Lista() {
        JFR.cabecera("testColeccion2Lista", true);
        boolean esCorrecto = true;

        JFR.cabecera("testColeccion2Lista", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean testMapa2Lista() {
        JFR.cabecera("testMapa2Lista", true);
        boolean esCorrecto = true;
        HashMap<String, Object> map = new HashMap();
        Direccion d1 = new Direccion();
        Direccion d2 = new Direccion(TipoVia.PASEO, "Paseo de la Castellana", 123, "Madrid", "España");
        d1.setCiudad("Madrid");
        d1.setNombreVia("De las Fraguas");
        d1.setNumeroVia(36);
        d1.setTipoVia(TipoVia.CALLE);
        d1.setPais("España");

        map.put("1", d1);
        map.put("2", d2);

        ArrayList<Direccion> lista = new ArrayList<>();

        lista = (ArrayList<Direccion>) JFR.mapa2Lista(Direccion.class, map);

        JFR.imp(true, 1, "");
        JFR.imp(true, 1, "mapa -> ", map.toString());
        JFR.imp(true, 1, "lista -> ", lista.toString());

        JFR.cabecera("testMapa2Lista", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean testExtractDigits() {
        JFR.cabecera("testExtractDigits", true);
        boolean esCorrecto = true;

        JFR.cabecera("testExtractDigits", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean testExtractDoubleFromString() {
        JFR.cabecera("testExtractDoubleFromString", true);
        boolean esCorrecto = true;

        JFR.cabecera("testExtractDoubleFromString", false);
        return esCorrecto;
    }

    /**
     *
     */
    public boolean testExtractLongFromString() {
        JFR.cabecera("testExtractLongFromString", true);
        boolean esCorrecto = true;

        JFR.cabecera("testExtractLongFromString", false);
        return esCorrecto;
    }

    /**
     *
     */
    @SuppressWarnings("null")
    public boolean testComparaciones() {
        JFR.cabecera("testComparaciones", true);
        boolean esCorrecto = true;
        JFR.imp(true, 1, "");
        try {
            String a = "con contenido";
            String b = null;
            String c = "con contenido";
            String e = "";
            String f = null;

            if (!a.equalsIgnoreCase(b)) {
                System.out.println("a y b no son iguales");
            } else {
                System.out.println("a y b son iguales");
            }

            if (!a.equalsIgnoreCase(c)) {
                System.out.println("a y c no son iguales");
            } else {
                System.out.println("a y c son iguales");
            }

            if (e != null) {
                if (!e.equalsIgnoreCase(f)) {
                    System.out.println("e y f no son iguales");
                } else {
                    System.out.println("e y f no son iguales");
                }
            }

            // Lo hago adrede
            if (!b.equalsIgnoreCase(a)) {
                System.out.println("b y a no son iguales");
            }

        } catch (Exception ex) {
            System.out.println(ex.getClass().getCanonicalName());
        }
        JFR.cabecera("testComparaciones", false);
        return esCorrecto;
    }

    public boolean testProcesaDummies() {
        JFR.cabecera("testProcesaDummies", true);
        boolean esCorrecto = true;
        Calendar gc = GregorianCalendar.getInstance();
        gc.set(1969, 11, 22);

        Date fecha1 = gc.getTime();
        Integer edad1 = Integer.parseInt("48");

        gc.set(1965, 7, 5);
        Date fecha2 = gc.getTime();
        Integer edad2 = Integer.parseInt("52");

        Dummy d1 = new Dummy("Juan Francisco Ruiz", edad1, fecha1);
        Dummy d2 = new Dummy("Jose Antonio Sevillano", edad2, fecha2);

        JFR.imp(true, 1, "");
        JFR.imp(true, 1, "ANTES");
        JFR.imp(true, 1, "d1=", d1.toString());
        JFR.imp(true, 2, "d2=", d2.toString());

        if (d1 != null && d2 != null) {
            if (!d1.equals(d2)) {
                String nombre1 = d1.getNombre();
                edad1 = d1.getEdad();
                Date nacimiento1 = d1.getNacimiento();

                String nombre2 = d2.getNombre();
                edad2 = d2.getEdad();
                Date nacimiento2 = d2.getNacimiento();

                nombre1 = (nombre1 == null) ? "" : nombre1;
                edad1 = (edad1 == null) ? new Integer(0) : edad1;
                nacimiento1 = (nacimiento1 == null) ? new Date(System.currentTimeMillis()) : nacimiento1;

                nombre2 = (nombre2 == null) ? "" : nombre2;
                edad2 = (edad2 == null) ? new Integer(0) : edad2;
                nacimiento2 = (nacimiento2 == null) ? new Date(System.currentTimeMillis()) : nacimiento2;

                if (!nombre2.equalsIgnoreCase(nombre1)) {
                    d1.setNombre(nombre2);
                }
                if (!edad2.equals(edad1)) {
                    d1.setEdad(edad2);
                }
                if (!nacimiento1.equals(nacimiento2)) {
                    d1.setNacimiento(nacimiento2);
                }
            }
        }
        JFR.imp(true, 1, "DESPUES");
        JFR.imp(true, 1, "d1=", d1.toString());
        JFR.imp(true, 1, "d2=", d2.toString());

        JFR.cabecera("testProcesaDummies", false);
        return esCorrecto;
    }

}
