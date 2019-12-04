package name.ruiz.juanfco.home;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 * @author hamfree
 *
 */
public class JFR {
	private static Logger log;
	private static final String SL = System.getProperty("line.separator", "\n");

	/**
	 * Convierte una colección generíca en una lista de tipo 'clazz'.
	 * 
	 * @param clazz
	 *            el tipo que tendrán los elementos de la lista agregados desde la
	 *            colección
	 * @param c
	 *            la colección cuyos elementos se van a agregar a la nueva lista que
	 *            se devolverá
	 * @return una lista de tipo 'clazz' con los elementos de la colección 'c'
	 */
	public static <T> List<T> coleccionn2Lista(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	/**
	 * Convierte un Mapa en una Lista
	 * 
     * @param <T>
   	 * @param clazz
	 *            el tipo de la Lista en el que se convertírá el valor de cada
	 *            elemento del mapa
	 * @param m
	 *            el mapa con claves de tipo String y cualquier tipo de valor que
	 *            será convertido en una lista
	 * @return Una lista con los valores del mapa convertidos al tipo indicado por
	 *         'clazz'
	 */
	public static <T> List<T> mapa2Lista(Class<? extends T> clazz, Map<String, Object> m) {
        List<T> r = new ArrayList<>(m.size());

		for (Map.Entry<String, Object> entrada : m.entrySet()) {
			r.add(clazz.cast(entrada.getValue()));
		}
		return r;
	}

	/**
	 * Extrae los dígitos existentes en la cadena que se le pasa.
	 * 
	 * @param src
	 *            Un string que puede contener dígitos que se devolverán como una
	 *            cadena
	 * @return una string con los dígitos existentes en el parámetro pasado o la
	 *         cadena vacía si no encuentra dígitos.
	 */
	public String extractDigits(String src) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if (Character.isDigit(c)) {
				builder.append(c);
			}
		}
		return builder.toString();
	}

	/**
	 * Extrae un número entero largo de la cadena que se le pasa como parámetro y si
	 * no lo encuentra devolverá null
	 * 
	 * @param src
	 *            la cadena con el posible número entero largo embebido
	 * @return el entero largo o null en caso de no encontrar nada en la cadena
	 */
	public static Long extractLongFromString(String src) {
		Long numLargo = new Long(-1);
		try {
			if (src != null) {
				/*
				 * Saneamos la entrada, porque puede venir con caracteres que no son digitos...
				 */
				src = src.trim();

				/*
				 * expresion regular que 'solo' deja pasar digitos...
				 */
				src = src.replaceAll("\\D+", "");
				Number number = NumberFormat.getInstance().parse(src);
				numLargo = number.longValue();
			}

		} catch (NumberFormatException | ParseException ex) {
			log.severe(ex.getLocalizedMessage());
			numLargo = null;
		}

		log.info(numLargo.toString());
		return numLargo;
	}

	/**
	 * 
	 * @param laCadena
	 * @return
	 */
	public static Double extractDoubleFromString(String laCadena) {
		Double numDecimal = new Double(-1);
		try {
			if (laCadena != null) {
				/*
				 * Saneamos la entrada, porque puede venir con caracteres que no son digitos...
				 */
				laCadena = laCadena.trim();
				laCadena = laCadena.replaceAll("[^\\.0123456789]", "");
				/**
				 * TODO: Implementar también los negativos
				 */
				Number number = NumberFormat.getInstance().parse(laCadena);
				numDecimal = number.doubleValue();
			}

		} catch (NumberFormatException | ParseException ex) {
			log.severe(ex.getLocalizedMessage());
			numDecimal = new Double(Double.NaN);
		}

		log.info(numDecimal.toString());
		return numDecimal;
	}

	/**
	 * Imprime los argumentos pasados y después, si se pasa 'true' al parámetro 'sl'
	 * imprimirá tantos saltos de línea como indique el parámetro 'saltos'. En caso
	 * de que se pase 'false' al parámetro 'sl' no habrá ningún salto de línea y se
	 * ignora el parámetro 'saltos'.
	 * 
	 * @param sl
	 *            booleano que si es true hará que se impriman tantos saltos de
	 *            línea como indique el parámetro 'saltos'
	 * @param saltos
	 *            un entero con el número de saltos de línea a imprimir
	 * @param args
	 *            lista de argumentos variable que se imprimirán.
	 */
	public static void imp(boolean sl, int saltos, Object... args) {
		if (args != null && args.length > 0) {
			for (Object o : args) {
				System.out.print(o.toString());
			}
			if (sl && saltos > 0) {

				for (int i = 0; i < saltos; i++) {
					System.out.print(SL);
				}
			}
		}
	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @param log
	 *            the log to set
	 */
	public static void setLog(Logger log) {
		JFR.log = log;
	}

	/**
	 * @return the sl
	 */
	public static String getSl() {
		return SL;
	}

	/**
	 * 
	 * @param msg
	 * @param esInicio
	 */
	static void cabecera(String msg, boolean esInicio) {
		StringBuilder sb = new StringBuilder();
		if (msg != null && msg.length() > 0) {
			JFR.imp(true, 1, "");
			linea('-', 80);
			sb.append(JFR.getSl());
			sb.append(msg);
			if (esInicio) {
				sb.append("() - INICIO");
			} else {
				sb.append("() - FIN");
			}
			JFR.imp(true, 1, sb.toString());
			linea('-', 80);
		}
	}

	/**
	 * Imprime una línea compuesta por caracteres 'car' y que contendrá tantos como
	 * indica el parámetro 'longitud'
	 * 
	 * @param car
	 *            un char con el caracter del que estará compuesta la linea
	 * @param longitud
	 *            un entero con la cantidad de caracteres que compondrán la línea
	 */
	static void linea(Character car, int longitud) {
		if (car != null && longitud > 0) {
			String linea = repiteCaracter(car, longitud);
			JFR.imp(false, 1, linea);
		}
	}

	/**
	 * Devuelve una cadena compuesta por el caracter 'car' que será repetido tantas
	 * veces como indica el parámetro 'veces'
	 * 
	 * @param car
	 *            el caracter que se va a devolver repetido en una cadena
	 * @param veces
	 *            el numero de veces que se repetirá el caracter.
	 * @return una cadena
	 */
	static String repiteCaracter(Character car, int veces) {
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
