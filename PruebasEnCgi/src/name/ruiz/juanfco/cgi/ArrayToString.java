package name.ruiz.juanfco.cgi;

import java.lang.reflect.Array;

/**
 * Registrar el contenido de una Collection es simple, ya que 
 * AbstractCollection.toString está siempre disponible. Para un array sin embargo el método toString por 
 * defecto no es muy informativo, y no incluye los contenidos del array. Para suministrar representaciones 
 * más útiles de los arrays, se añadieron varios métodos toString (y el método deepToString) a la clase Arrays 
 * en el JDK 1.5. Esos métodos se pueden usar cuando estén disponibles, como en:
 * 
 * Arrays.toString(myArray); 
 * Arrays.deepToString(myObjectArray); //recursive 
 * 
 * Si necesita una alternativa, puede simplemente convertir el array en una colección, como en 
 * 
 * Arrays.asList(myArray).toString(); 
 * 
 * Finalmente, aquí tiene una tercera opción, usando una clase de utilidad la cual:
 * 
 * usa un estilo ligeramente diferente del usado en Arrays y funciona con versiones anteriores del JDK 1.5 y 
 * devuelve texto en la misma forma que AbstractCollection.toString maneja los arrays de todos los tipos 
 * primitivos, y arrays de Objetos como también maneja arrays de arrays usando la recursión. * 
 * 
 * 
 * Mñetodo de convenencia para producir una representación textual simple de un array.
 *
 * <P>
 * El formato de la <code>String</code> devuelta es el mismo que 
 * <code>AbstractCollection.toString</code>:
 * <ul>
 * <li>array no vacío: [blah, blah]
 * <li>array vacío   : []
 * <li>array nulo    : null
 * </ul>
 *
 * @author Jerome Lacoste
 * @author www.javapractices.com
 */
public final class ArrayToString {

	/**
	 * <code>aArray</code>  es un array posiblemente nulo cuyos elementos son tipos primitivos u  objetos;
	 * los arrays de arrays también son válidos, en cuyo caso <code>aArray</code>
	 * se representa de forma anidada y recursiva.
	 */
	public static String get(Object aArray) {
		if (aArray == null)
			return fNULL;
		checkObjectIsArray(aArray);

		StringBuilder result = new StringBuilder(fSTART_CHAR);
		int length = Array.getLength(aArray);
		for (int idx = 0; idx < length; ++idx) {
			Object item = Array.get(aArray, idx);
			if (isNonNullArray(item)) {
				// recursive call!
				result.append(get(item));
			} else {
				result.append(item);
			}
			if (!isLastItem(idx, length)) {
				result.append(fSEPARATOR);
			}
		}
		result.append(fEND_CHAR);
		return result.toString();
	}

	// PRIVATE
	private static final String fSTART_CHAR = "[";
	private static final String fEND_CHAR = "]";
	private static final String fSEPARATOR = ", ";
	private static final String fNULL = "null";

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

	/** Test harness. */
	public static void main(String... args) {

		boolean[] booleans = { true, false, false };
		char[] chars = { 'B', 'P', 'H' };
		byte[] bytes = { 3 };
		short[] shorts = { 5, 6 };
		int[] ints = { 7, 8, 9, 10 };
		long[] longs = { 100, 101, 102 };
		float[] floats = { 99.9f, 63.2f };
		double[] doubles = { 212.2, 16.236, 42.2 };
		String[] strings = { "blah", "blah", "blah" };
		java.util.Date[] dates = { new java.util.Date(), new java.util.Date() };
		System.out.println("booleans: " + get(booleans));
		System.out.println("chars: " + get(chars));
		System.out.println("bytes: " + get(bytes));
		System.out.println("shorts: " + get(shorts));
		System.out.println("ints: " + get(ints));
		System.out.println("longs: " + get(longs));
		System.out.println("floats: " + get(floats));
		System.out.println("double: " + get(doubles));
		System.out.println("strings: " + get(strings));
		System.out.println("dates: " + get(dates));

		int[] nullInts = null;
		int[] emptyInts = {};
		String[] emptyStrings = { "", "" };
		String[] nullStrings = { null, null };
		System.out.println("null ints: " + get(nullInts));
		System.out.println("empty ints: " + get(emptyInts));
		System.out.println("empty Strings: " + get(emptyStrings));
		System.out.println("null Strings: " + get(nullStrings));

		String[] arrayA = { "A", "a" };
		String[] arrayB = { "B", "b" };
		String[][] arrayOfArrays = { arrayA, arrayB };
		System.out.println("array Of Arrays: " + get(arrayOfArrays));
	}
}