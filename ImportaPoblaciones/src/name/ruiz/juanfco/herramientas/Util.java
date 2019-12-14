package name.ruiz.juanfco.herramientas;

import java.io.Console;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

/**
 *
 * @author hamfree
 */
public class Util {

    public final String SL = System.getProperty("line.separator");
    public final String SF = System.getProperty("file.separator");

    // Métodos de utilidad
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
            List l = Arrays.asList(obj);
            return l.isEmpty();
        } else if (obj.getClass().isAssignableFrom(Collection.class)) {
            Collection col = (Collection) obj;
            return col.isEmpty();
        }
        return false;
    }

    /**
     * Imprime los argumentos indicados en la salida estándar. Puede usar el
     * objeto Console si se pasa true al parámetro usaConsola. En caso contrario
     * usara el canal estandar de salida.
     *
     * @param usaConsola booleano que si es true intentará usar Console.
     * @param args una lista de objetos a imprimir
     * @throws Exception en caso de pasar parámetros nulos.
     */
    public static void imp(boolean usaConsola, Object... args) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (args == null) {
            throw new Exception("¡Parámetros nulos!");
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
     *
     * @param usaConsola
     * @return
     * @throws java.lang.Exception
     */
    public static String read(boolean usaConsola) throws Exception {
        String dato = "";
        if (usaConsola) {
            Console con = System.console();
            if (con == null) {
                Scanner sc = new Scanner(System.in);
                dato = sc.nextLine();
            } else {
                dato = con.readLine();
            }
        }
        return dato;
    }

    /**
     *
     * @param dato
     * @return
     */
    public boolean isInteger(String dato) {
        if (!isNullOrEmpty(dato)) {
            try {
                int tmp = Integer.parseInt(dato);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @param dato
     * @return
     */
    public boolean isDouble(String dato) {
        if (!isNullOrEmpty(dato)) {
            try {
                double tmp = Double.parseDouble(dato);
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @return
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

}
