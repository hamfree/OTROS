package name.ruiz.juanfco.cgi;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author operador
 *
 */
public class App {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        App miApp = new App();

        if (args.length == 1 || args.length == 2) {
            try {
                miApp.ejecuta(args);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        } else {
            sb.append("Este programa requiere de un comando y ").append("opcionalmente un segundo argumento.")
                    .append("Escriba 'App help' para mostrar la lista de comandos disponibles");
            System.out.println(sb.toString());
        }

        System.exit(0);
    }

    @SuppressWarnings("deprecation")
    void ejecuta(String[] args) throws Exception {
        Dummy d1 = new Dummy("Juan Francisco Ruiz", 48, new Date(1969, 11, 22));
        Dummy d2 = new Dummy("Jose Antonio Sevillano", 52, new Date(1965, 07, 05));

        String argumento = args[0];

        if (argumento.equalsIgnoreCase(Arg.HELP.toString())) {
            System.out.println("Los argumentos disponibles son: ");
            int i = 0;
            while (i < Arg.values().length) {
                if (i < (Arg.values().length) - 1) {
                    System.out.print(Arg.values()[i] + ",");
                } else {
                    System.out.print(Arg.values()[i]);
                }
                i++;
            }
            System.out.println("\n");
        } else if (argumento.equalsIgnoreCase(Arg.REPLACE.toString())) {
            testReplace(d1);
        } else if (argumento.equalsIgnoreCase(Arg.HOLAMUNDO.toString())) {

            holaMundo();
        } else if (argumento.equalsIgnoreCase(Arg.MAPA2LISTA.toString())) {
            testMapa2Lista();
        } else if (argumento.equalsIgnoreCase(Arg.EXTRACTDIGITS.toString())) {
            testExtractDigits();
        } else if (argumento.equalsIgnoreCase(Arg.EXTRACTDOUBLEFROMSTRING.toString())) {
            testExtractDoubleFromString();
        } else if (argumento.equalsIgnoreCase(Arg.EXTRACTLONGFROMSTRING.toString())) {
            testExtractLongFromString();
        } else if (argumento.equalsIgnoreCase(Arg.COMPARACIONES.toString())) {
            testComparaciones();
        } else if (argumento.equalsIgnoreCase(Arg.PROCESADUMMIES.toString())) {
            testProcesaDummies(d1, d2);
        } else if (argumento.equalsIgnoreCase(Arg.TROCEOCADENA.toString())) {
            testTroceoCadena();
        } else if (argumento.equalsIgnoreCase(Arg.ELIMINAPARENTESIS.toString())) {
            testEliminaParentesis();
        } else if (argumento.equalsIgnoreCase(Arg.VALIDAFORMATO.toString())) {
            testValidaFormato();
        } else if (argumento.equalsIgnoreCase(Arg.ARRAYTOSTRING.toString())) {
            testArrayToString();
        } else if (argumento.equalsIgnoreCase(Arg.OBJETOS.toString())) {
            testObjetos();
        } else if (argumento.equalsIgnoreCase(Arg.FECHAS.toString())) {
            testFechas();
        } else if (argumento.equalsIgnoreCase(Arg.REFLEXION.toString())) {
            testReflexion();
        } else {
            System.out.println("Error: Comando '" + argumento + "' no reconocido.");

        }
    }

    void testFechas() {
        cabecera("testFechas", true);
        Utl.imp(true, 2, "Probamos la conversion dentro del método...");

        Date fecha = new Date(System.currentTimeMillis());
        Utl.imp(true, 1, "Valor de la variable del tipo Date.......: ", fecha);

        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(fecha);
        LocalDate localdate = LocalDate.of(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH),
                gc.get(Calendar.DAY_OF_MONTH));
        Utl.imp(true, 2, "Valor de la variable del tipo LocalDate..: ", localdate);

        Utl.imp(true, 2, "Probamos la conversión como resultado de la llamada al método Utl.date2LocalDate()...");

        Utl.imp(true, 1, "Establecemos la fecha a 1 de Enero de 2015....");
        gc.set(2015, Calendar.JANUARY, 1);
        Date fecha2 = new Date(gc.getTimeInMillis());
        Utl.imp(true, 1, "Valor de la variable del tipo Date.......: ", fecha2);

        LocalDate localdate2 = Utl.date2LocalDate(fecha2);
        Utl.imp(true, 2, "Valor de la variable del tipo LocalDate..: ", localdate2);

        Utl.imp(true, 1, "Establecemos la fecha a 31 de Diciembre de 2015....");
        gc.set(2015, Calendar.DECEMBER, 31);
        Date fecha3 = new Date(gc.getTimeInMillis());
        Utl.imp(true, 1, "Valor de la variable del tipo Date.......: ", fecha3);

        LocalDate localdate3 = Utl.date2LocalDate(fecha3);
        Utl.imp(true, 2, "Valor de la variable del tipo LocalDate..: ", localdate3);

        cabecera("testFechas", false);
    }

    void testObjetos() {
        cabecera("testObjeto", true);
        Dummy d1 = new Dummy("Rui", 49, new Date());
        Dummy d2 = null;

        try {
            d2 = (Dummy) d1.clone();
        } catch (CloneNotSupportedException e) {
            Utl.imp(true, 1, e.getLocalizedMessage());
        }

        d2.setEdad(38);
        Utl.imp(true, 1, "edad en d1 -> ", d1.getEdad());
        Utl.imp(true, 1, "edad en d2 -> ", d2.getEdad());
        Utl.imp(true, 1, "nombre en d2 -> ", d2.getNombre());
        cabecera("testObjeto", false);
    }

    // Todos los test que voy realizando....
    void testArrayToString() {
        cabecera("testArrayToString", true);

        boolean[] booleans = {true, false, false};
        char[] chars = {'B', 'P', 'H'};
        byte[] bytes = {3};
        short[] shorts = {5, 6};
        int[] ints = {7, 8, 9, 10};
        long[] longs = {100, 101, 102};
        float[] floats = {99.9f, 63.2f};
        double[] doubles = {212.2, 16.236, 42.2};
        String[] strings = {"blah", "blah", "blah"};
        java.util.Date[] dates = {new java.util.Date(), new java.util.Date()};

        int[] nullInts = null;
        int[] emptyInts = {};
        String[] emptyStrings = {"", ""};
        String[] nullStrings = {null, null};

        String[] arrayA = {"A", "a"};
        String[] arrayB = {"B", "b"};
        String[][] arrayOfArrays = {arrayA, arrayB};

        System.out.println("booleans: " + Utl.arrayToString(booleans));
        System.out.println("chars: " + Utl.arrayToString(chars));
        System.out.println("bytes: " + Utl.arrayToString(bytes));
        System.out.println("shorts: " + Utl.arrayToString(shorts));
        System.out.println("ints: " + Utl.arrayToString(ints));
        System.out.println("longs: " + Utl.arrayToString(longs));
        System.out.println("floats: " + Utl.arrayToString(floats));
        System.out.println("double: " + Utl.arrayToString(doubles));
        System.out.println("strings: " + Utl.arrayToString(strings));
        System.out.println("dates: " + Utl.arrayToString(dates));

        System.out.println("null ints: " + Utl.arrayToString(nullInts));
        System.out.println("empty ints: " + Utl.arrayToString(emptyInts));
        System.out.println("empty Strings: " + Utl.arrayToString(emptyStrings));
        System.out.println("null Strings: " + Utl.arrayToString(nullStrings));

        System.out.println("array Of Arrays: " + Utl.arrayToString(arrayOfArrays));

        cabecera("testArrayToString", false);
    }

    void testValidaFormato() throws Exception {
        cabecera("testValidaFormato", true);
        final String SEP = ",";
        final String DEL = "'";
        boolean isNumericList;
        boolean isStringList;
        StringBuilder sb = new StringBuilder();

        Utl.imp(false, 1, "Dame una CONDITIONVALUE para validarla:");
        String valueToValidate = Utl.read(false);

        if (Utl.isNull(valueToValidate)) {
            Utl.imp(true, 1, "CONDITIONVALUE can't be NULL");
            return;
        } else {
            if (valueToValidate.isEmpty()) {
                Utl.imp(true, 1, "CONDITIONVALUE can't be blank");
                return;
            } else {
                // Comprobamos que no empiece ni termine por comas
                if (valueToValidate.startsWith(SEP) || valueToValidate.endsWith(SEP)) {
                    sb.append("CONDITIONVALUE can't begins or ends with the value delimiter (").append(SEP).append(")");
                    Utl.imp(true, 1, sb.toString());
                    return;
                }

                // Comprobamos si es una lista de números o de cadenas.
                if (valueToValidate.substring(0, 1).equals(DEL)) {
                    isStringList = true;
                    isNumericList = false;
                } else {
                    isStringList = false;
                    isNumericList = true;
                }

                // Comprobamos el formato
                Scanner sc = new Scanner(valueToValidate);
                sc.useDelimiter(SEP);
                if (isStringList) {
                    while (sc.hasNext()) {
                        sb.setLength(0);
                        String elemento = sc.next();
                        if (!elemento.startsWith(DEL) || !elemento.endsWith(DEL)) {
                            sb.append("The item |").append(elemento)
                                    .append("| is not enclosed in quotes or one of them is missing.");
                            Utl.imp(true, 1, sb.toString());
                            sc.close();
                            return;
                        }
                        if (elemento.isEmpty()) {
                            Utl.imp(true, 1, "The item can not be an empty string.");
                            sc.close();
                            return;
                        }
                        sb.append("Element |").append(elemento).append("| meets the syntax.");
                        Utl.imp(true, 1, sb.toString());
                    }
                } else if (isNumericList) {
                    while (sc.hasNext()) {
                        sb.setLength(0);
                        String elemento = sc.next();
                        if (elemento.startsWith(DEL) || elemento.endsWith(DEL)) {
                            sb.append("the item  |").append(elemento).append("| can not be in single quotes.");
                            Utl.imp(true, 1, sb.toString());
                            sc.close();
                            return;
                        }
                        if (elemento.isEmpty()) {
                            Utl.imp(true, 1, "The item can not be an empty string.");
                            sc.close();
                            return;
                        }
                        sb.append("Element |").append(elemento).append("| meets the syntax.");
                        Utl.imp(true, 1, sb.toString());
                    }
                } else {
                    sc.close();
                    throw new Exception("No se qué diablos me has pasado...");
                }
                sc.close();
            }
        }
        cabecera("testValidaFormato", false);
    }

    /**
     *
     * @param d
     */
    void testReplace(Dummy d) {
        cabecera("testReplace", true);
        Utl.imp(true, 1, "");
        Utl.imp(true, 1, "ANTES");

        String nombre = d.getNombre();
        System.out.println("nombre = " + nombre);
        nombre.replace('u', 'X');
        System.out.println("DESPUES SIN ASIGNACION");
        System.out.println("nombre = " + nombre);
        System.out.println("DESPUES CON ASIGNACION");
        nombre = nombre.replace('u', 'X');
        System.out.println("nombre = " + nombre);
        cabecera("testReplace", false);
    }

    /**
     *
     */
    void holaMundo() {
        cabecera("holaMundo", true);
        Utl.imp(true, 1, "");
        System.out.println("¡Hola mundo!");
        cabecera("holaMundo", false);
    }

    /**
     *
     */
    void testColeccion2Lista() {
        cabecera("testColeccion2Lista", true);

        cabecera("testColeccion2Lista", false);
    }

    /**
     *
     */
    void testMapa2Lista() {
        cabecera("testMapa2Lista", true);

        cabecera("testMapa2Lista", false);
    }

    /**
     *
     */
    void testExtractDigits() {
        cabecera("testExtractDigits", true);

        cabecera("testExtractDigits", false);
    }

    /**
     *
     */
    void testExtractDoubleFromString() {
        cabecera("testExtractDoubleFromString", true);

        cabecera("testExtractDoubleFromString", false);
    }

    /**
     *
     */
    void testExtractLongFromString() {
        cabecera("testExtractLongFromString", true);

        cabecera("testExtractLongFromString", false);
    }

    /**
     *
     */
    @SuppressWarnings("null")
    void testComparaciones() {
        cabecera("testComparaciones", true);
        Utl.imp(true, 1, "");
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
        cabecera("testComparaciones", false);
    }

    void testProcesaDummies(Dummy d1, Dummy d2) {
        cabecera("testProcesaDummies", true);

        Utl.imp(true, 1, "");
        Utl.imp(true, 1, "ANTES");
        Utl.imp(true, 1, "d1=", d1.toString());
        Utl.imp(true, 1, "d2=", d2.toString());

        if (d1 != null && d2 != null) {
            if (!d1.equals(d2)) {
                String nombre1 = d1.getNombre();
                Integer edad1 = d1.getEdad();
                Date nacimiento1 = d1.getNacimiento();

                String nombre2 = d2.getNombre();
                Integer edad2 = d2.getEdad();
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

        Utl.imp(true, 1, "DESPUES");
        Utl.imp(true, 1, "d1=", d1.toString());
        Utl.imp(true, 1, "d2=", d2.toString());

        cabecera("testProcesaDummies", false);
    }

    @SuppressWarnings("unused")
    void testTroceoCadena() {
        String cadena = "'CPC1','CPC2','CPC3',67,34.11,333,'Ultimo'";
        Scanner sc = new Scanner(cadena);
        ArrayList<String> lista = new ArrayList<>();
        int index = 0;
        sc.useDelimiter(",");

        while (sc.hasNext()) {
            lista.add(sc.next());
            index++;
        }
        sc.close();

        for (String elemento : lista) {
            System.out.print(elemento + ",");
        }
    }

    void testEliminaParentesis() {
        cabecera("testEliminaParentesis", true);
        String cadena = "(20,50,100)";
        Utl.imp(true, 1, "ANTES:", cadena);
        if (cadena.startsWith("(")) {
            cadena = cadena.substring(1);
        }
        if (cadena.endsWith(")")) {
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        Utl.imp(true, 1, "DESPUES:", cadena);
        cabecera("testEliminaParentesis", false);
    }

    void testReflexion() {
        //http://tutorials.jenkov.com/java-reflection/generics.html
        try {
            cabecera("testReflexion", true);
            Utl.imp(true, 1, "");
            Utl.imp(true, 1, "Hallando el tipo generico que devuelve el metodo getLista():");
            Method metodoLista = MiClase.class.getMethod("getLista", (Class<?>[]) null);

            Type retornoTipo = metodoLista.getGenericReturnType();

            if (retornoTipo instanceof ParameterizedType) {
                ParameterizedType tipoParametrizado = (ParameterizedType) retornoTipo;
                Type[] argumentosTipo = tipoParametrizado.getActualTypeArguments();
                for (Type argumentoTipo : argumentosTipo) {
                    Class argtipoClase = (Class) argumentoTipo;
                    Utl.imp(true, 1, "argumentoTipo=", argtipoClase);
                }
            }

            Utl.imp(true, 1, "");
            Utl.imp(true, 2, "Hallando el tipo generico que devuelve el metodo getMapa():");
            Method metodoMapa = MiClase.class.getMethod("getMapa", (Class<?>[]) null);

            Type retornoTipo2 = metodoMapa.getGenericReturnType();

            // Muy interesante ....
            Utl.imp(true, 2, "retornoTipo2 = ", retornoTipo2.toString());

            if (retornoTipo2 instanceof ParameterizedType) {
                ParameterizedType tipoParametrizado = (ParameterizedType) retornoTipo2;
                Type[] argumentosTipo = tipoParametrizado.getActualTypeArguments();
                for (Type argumentoTipo : argumentosTipo) {
                    Class argtipoClase = (Class) argumentoTipo;
                    Utl.imp(true, 1, "argumentoTipo=", argtipoClase);
                }
            }

            Utl.imp(true, 1, "");
            Utl.imp(true, 2, "Hallando los tipos genericos de los parametros del metodo setMapa():");
            Method metodoSetMap = MiClase.class.getMethod("setMapa", Map.class);

            Type[] tiposParametrosGenericos = metodoSetMap.getGenericParameterTypes();

            // Muy interesante ....
            Utl.imp(true, 2, "tiposParametrosGenericos = ", tiposParametrosGenericos.toString());

            for (Type tipoParametroGenerico : tiposParametrosGenericos) {
                if (tipoParametroGenerico instanceof ParameterizedType) {
                    ParameterizedType tipoParametrizado = (ParameterizedType) tipoParametroGenerico;
                    Type[] tiposArgumentosActuales = tipoParametrizado.getActualTypeArguments();
                    for (Type tipoArgumentoActual : tiposArgumentosActuales) {
                        Class claseArgumentoTipoActual = (Class) tipoArgumentoActual;
                        Utl.imp(true, 1, "claseArgumentoTipoActual = ", claseArgumentoTipoActual);
                    }
                }
            }

            cabecera("testReflexion", false);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *********************
     * Metodos de utilidad * *********************
     */
    /**
     *
     * @param msg
     * @param esInicio
     */
    void cabecera(String msg, boolean esInicio) {
        StringBuilder sb = new StringBuilder();
        if (msg != null && msg.length() > 0) {
            Utl.imp(true, 1, "");
            linea('-', 80);
            sb.append(Utl.getSl());
            sb.append(msg);
            if (esInicio) {
                sb.append("() - INICIO");
            } else {
                sb.append("() - FIN");
            }
            Utl.imp(true, 1, sb.toString());
            linea('-', 80);
            Utl.imp(true, 1, "");
        }
    }

    /**
     *
     * @param car
     * @param longitud
     */
    void linea(Character car, int longitud) {
        if (car != null && longitud > 0) {
            String linea = repiteCaracter(car, longitud);
            Utl.imp(false, 1, linea);
        }
    }

    /**
     *
     * @param car
     * @param veces
     * @return
     */
    String repiteCaracter(Character car, int veces) {
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        if (car != null && veces > 0) {
            for (int i = 0; i < veces; i++) {
                sb.append(car);
            }
        }
        return sb.toString();
    }
}
