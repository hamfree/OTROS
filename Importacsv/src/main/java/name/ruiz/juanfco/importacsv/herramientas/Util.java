package name.ruiz.juanfco.importacsv.herramientas;

import java.io.Console;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Clase de utilidad con métodos usados ampliamente en la aplicación.
 *
 * @author hamfree
 */
public class Util {
    private static final Logger LOG = Logger.getLogger(Util.class.getName());
    public static final String SL = System.getProperty("line.separator");
    public static final String SF = System.getProperty("file.separator");
	private static Scanner sc;

    // Metodos de utilidad
    /**
     *
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String) {
            return obj.toString().length() == 0;
        } else if (obj.getClass().isArray()) {
            List<Object> l = Arrays.asList(obj);
            return l.isEmpty();
        } else if (obj.getClass().isAssignableFrom(Collection.class)) {
            Collection<?> col = (Collection<?>) obj;
            return col.isEmpty();
        }
        return false;
    }

    /**
     * Imprime los argumentos indicados en la salida estandar. Puede usar el
     * objeto Console si se pasa true al parametro usaConsola. En caso contrario
     * usara el canal estandar de salida.
     *
     * @param usaConsola booleano que si es true intentara usar Console.
     * @param args una lista de objetos a imprimir
     */
    public static void imp(boolean usaConsola, Object... args) {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            return;
        } else {
            for (Object arg : args) {
                sb.append(arg.toString());
            }
        }
        if (usaConsola) {
            Console con = System.console();
            if (con == null) {
                System.out.format("%s", sb.toString());
            } else {
                con.printf("%s", sb.toString());
            }
        } else {
            System.out.format("%s", sb.toString());
        }
    }

    /**
     * Imprime los argumentos indicados en la salida estandar y despues un salto
     * de linea. Puede usar el objeto Console si se pasa true al parametro
     * usaConsola. En caso contrario usara el canal estandar de salida.
     *
     * @param usaConsola booleano que si es true intentara usar Console.
     * @param args una lista de objetos a imprimir
     */
    public static void impsl(boolean usaConsola, Object... args) {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            return;
        } else {
            for (Object arg : args) {
                sb.append(arg.toString());
            }
            sb.append(SL);
        }
        if (usaConsola) {
            Console con = System.console();
            if (con == null) {
                System.out.format("%s", sb.toString());
            } else {
                con.printf("%s", sb.toString());
            }
        } else {
            System.out.format("%s", sb.toString());
        }
    }

    /**
     * Lee los caracteres introducidos por teclado por el usuario. En caso de
     * pasar true al parámetro <code>usaConsola</code> intentará utilizar el
     * objeto Console para capturar la entrada. NOTA: Usa la clase Scanner para
     * recoger la entrada, por lo que la versión mínima de Java debe ser la 1.5.
     *
     * @param usaConsola boolean que si vale true hace que el método use el
     * objeto Console para capturar la entrada del usuario.
     * @return una cadena con lo introducido por el usuario hasta que pulsa
     * la
     * tecla INTRO.
     */
    public static String read(boolean usaConsola) {
        String dato = "";
        if (usaConsola) {
            Console con = System.console();
            if (con == null) {
                sc = new Scanner(System.in);
                dato = sc.nextLine();
            } else {
                dato = con.readLine();
            }
        } //TODO: ¡Falta implementar el método en caso de que no se quiera usar Console!

        return dato;
    }

    /**
     * Comprueba si el parametro es un Integer
     *
     * @param dato Cadena con el parametro a comprobar
     * @return true si el parametro es un Integer, false en caso contrario
     */
    public boolean stringDataIsInteger(String dato) {
        if (!isNullOrEmpty(dato)) {
            try {
                Integer.parseInt(dato);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Comprueba si el parametro es un Double
     *
     * @param dato Cadena con el parametro a comprobar
     * @return true si el parametro es un Double, false en caso contrario
     */
    public boolean stringDataisDouble(String dato) {
        if (!isNullOrEmpty(dato)) {
            try {
                Double.parseDouble(dato);
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    public boolean isPrimitiveType(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isPrimitive();
        }
        return false;
    }

    public boolean isArray(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isArray();
        }
        return false;
    }

    public boolean isEnum(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isEnum();
        }
        return false;
    }

    public boolean isByte(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Byte.class);
        }
        return false;
    }

    public boolean isChar(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Character.class);
        }
        return false;
    }

    public boolean isShort(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Short.class);
        }
        return false;
    }

    public boolean isInteger(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Integer.class);
        }
        return false;
    }

    public boolean isLong(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Long.class);
        }
        return false;
    }

    public boolean isDouble(Object obj) {
        if (!isNullOrEmpty(obj)) {
            return obj.getClass().isAssignableFrom(Double.class);
        }
        return false;
    }


    /**
     * Obtiene una lista con todos los conjuntos de caracteres que puede manejar
     * la Maquina Virtual de Java actual.
     *
     * @return una lista de conjuntos de caracteres
     */
    public static List<Charset> getAllCharsets() {
        SortedMap<String, Charset> sm;
        ArrayList<Charset> al = null;
        sm = Charset.availableCharsets();
        Iterator<Charset> it = sm.values().iterator();
        if (it != null) {
            al = new ArrayList<>();
            while (it.hasNext()) {
                Charset ch = it.next();
                al.add(ch);
            }
        }
        return al;
    }

    /**
     *
     * @param LOG
     * @param patron
     * @param limite
     * @param contador
     * @param seAgrega
     * @throws SecurityException
     * @throws IOException
     */
    public static void activaLog(Logger LOG, String patron, int limite, int contador, boolean seAgrega)
            throws SecurityException, IOException {
        // se crea el manejador de archivo para el log
        FileHandler fh;
        fh = new FileHandler(patron, limite, contador, seAgrega);
        fh.setLevel(Level.ALL); // Nivel de trazado
        fh.setFormatter(new SimpleFormatter()); // Formateador de mensajes

        // agregamos el manejador de archivo a nuestro LOG
        LOG.addHandler(fh);

        // el manejador de consola se agrega automaticamente, solo
        // cambiamos el nivel de detalle a desplegar
        LOG.getHandlers()[0].setLevel(Level.SEVERE);

        // se establece el nivel predeterminado global
        LOG.setLevel(Level.INFO);
    }
}
