package name.ruiz.juanfco.cgi;

import java.io.Console;
import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Utl {
	private static Logger log;
	private static final String SL = System.getProperty("line.separator", "\n");
	private static final String START_CHAR = "[";
	private static final String END_CHAR = "]";
	private static final String SEPARATOR = ", ";
	private static final String NULL = "null";

	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isInteger(String source) {
		boolean result;

		try {
			if (isNull(source)) {
				return false;
			}
			Integer.parseInt(source);
			result = true;
		} catch (NumberFormatException excepcion) {
			result = false;
		}

		return result;
	}

	public static boolean isLong(String source) {
		boolean result;

		try {
			if (isNull(source)) {
				return false;
			}
			Long.parseLong(source);
			result = true;
		} catch (NumberFormatException excepcion) {
			result = false;
		}

		return result;
	}

	public static boolean isDouble(String source) {
		boolean result;

		try {
			if (isNull(source)) {
				return false;
			}
			Double.parseDouble(source);
			result = true;
		} catch (NumberFormatException excepcion) {
			result = false;
		}

		return result;
	}

	public static <T> List<T> coleccionn2Lista(Class<? extends T> clazz, Collection<?> c) {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

	public static <T> List<T> mapa2Lista(Class<? extends T> clazz, Map<String, Object> m) {
		List<T> r = new ArrayList<T>(m.size());

		for (Map.Entry<String, Object> entrada : m.entrySet()) {
			r.add(clazz.cast(entrada.getValue()));
		}
		return r;
	}

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
	 * 
	 */
	public static Long extractLongFromString(String laCadena) {
		Long numLargo = new Long(-1);
		try {
			if (laCadena != null) {
				// 'Saneamos' la entrada, porque puede venir con caracteres que
				// no son digitos...
				laCadena = laCadena.trim();
				laCadena = laCadena.replaceAll("\\D+", ""); // expresion regular
															// que 'solo' deja
															// pasar digitos...
				Number number = NumberFormat.getInstance().parse(laCadena); // hace
																			// la
																			// conversión
				numLargo = number.longValue();
			}

		} catch (NumberFormatException | ParseException ex) {
			log.severe(ex.getLocalizedMessage());
			numLargo = new Long(-1);
		}

		log.severe(numLargo.toString());
		return numLargo;
	}

	public static Double extractDoubleFromString(String laCadena) {
		Double numDecimal = new Double(-1);
		try {
			if (laCadena != null) {
				// 'Saneamos' la entrada, porque puede venir con caracteres que
				// no son digitos...
				laCadena = laCadena.trim();
				laCadena = laCadena.replaceAll("[^\\.0123456789]", ""); // ¿Y
																		// los
																		// negativos?
																		// Por
																		// hacer
				Number number = NumberFormat.getInstance().parse(laCadena); // hace
																			// la
																			// conversión
				numDecimal = number.doubleValue();
			}

		} catch (NumberFormatException | ParseException ex) {
			log.severe(ex.getLocalizedMessage());
			numDecimal = new Double(Double.NaN);
		}

		log.severe(numDecimal.toString());
		return numDecimal;
	}

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

	public static String read(boolean usaConsola) throws Exception {
		String dato = "";
		if (usaConsola) {
			Console con = System.console();
			if (con == null) {
				Scanner sc = new Scanner(System.in);
				dato = sc.nextLine();
				sc.close();
			} else {
				dato = con.readLine();
			}
		} else {
			Scanner sc = new Scanner(System.in);
			dato = sc.nextLine();
			sc.close();
		}
		return dato;
	}

	public static LocalDate date2LocalDate(Date theDate) {
		if (isNull(theDate)) {
			return null;
		}

		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(theDate);
		LocalDate localdate = LocalDate.of(gc.get(Calendar.YEAR), (gc.get(Calendar.MONTH) + 1),
				gc.get(Calendar.DAY_OF_MONTH));
		return localdate;
	}

	public static String arrayToString(Object aArray) {

		if (aArray == null)
			return NULL;
		checkObjectIsArray(aArray);

		StringBuilder result = new StringBuilder(START_CHAR);
		int length = Array.getLength(aArray);
		for (int idx = 0; idx < length; ++idx) {
			Object item = Array.get(aArray, idx);
			if (isNonNullArray(item)) {
				// ¡llamada recursiva!
				result.append(arrayToString(item));
			} else {
				result.append(item);
			}
			if (!isLastItem(idx, length)) {
				result.append(SEPARATOR);
			}
		}
		result.append(END_CHAR);
		return result.toString();
	}

	private static void checkObjectIsArray(Object aArray) {
		if (!aArray.getClass().isArray()) {
			throw new IllegalArgumentException("Object is not an array.");
		}
	}

	private static boolean isNonNullArray(Object aItem) {
		return aItem != null && aItem.getClass().isArray();
	}

	private static boolean isLastItem(int aIdx, int aLength) {
		return (aIdx == aLength - 1);
	}

	// Metodos getter y setter
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
		Utl.log = log;
	}

	/**
	 * @return the sl
	 */
	public static String getSl() {
		return SL;
	}

}
