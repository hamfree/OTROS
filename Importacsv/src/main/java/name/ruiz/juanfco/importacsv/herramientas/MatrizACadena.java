package name.ruiz.juanfco.importacsv.herramientas;

import java.lang.reflect.Array;

/**
 * Metodo de conveniencia para producir un texto simple representacion de una
 * matriz.
 *
 * <P>
 * El formato de la <code>String</code> devuelta es el mismo que el de
 * <code>AbstractCollection.toString</code>:
 * <ul>
 * <li>matriz no-vacia: [blah, blah]
 * <li>matriz vacia: []
 * <li>matriz nula: null
 * </ul>
 *
 * @author Jerome Lacoste
 * @author www.javapractices.com
 */
public final class MatrizACadena {

    /**
     * <code>aArray</code> Es una matriz posiblemente-nula cuyos elementos son
     * primitivos u objetos; matrices de matrices son tambien validos, en cuyo
     * caso <code>aArray</code> se representa de forma anidada y recursiva
     * @param unaMatriz
     * @return una cadena con la representación textual del contenido de una
     * matriz
     */
    public static String get(Object unaMatriz) {
        if (unaMatriz == null) {
            return NULO;
        }
        compruebaObjetoEsUnaMatriz(unaMatriz);

        StringBuilder resultado = new StringBuilder(CAR_INICIAL);
        int longitud = Array.getLength(unaMatriz);
        for (int idx = 0; idx < longitud; ++idx) {
            Object item = Array.get(unaMatriz, idx);
            if (esUnaMatrizNoNula(item)) {
                //ATENCION: llamada recursiva
                resultado.append(get(item));
            } else {
                resultado.append(item);
            }
            if (!esUltimoElemento(idx, longitud)) {
                resultado.append(SEPARADOR);
            }
        }
        resultado.append(CAR_FINAL);
        return resultado.toString();
    }

    // Privado
    private static final String CAR_INICIAL = "[";
    private static final String CAR_FINAL = "]";
    private static final String SEPARADOR = ", ";
    private static final String NULO = "null";

    private static void compruebaObjetoEsUnaMatriz(Object unaMatriz) {
        if (!unaMatriz.getClass().isArray()) {
            throw new IllegalArgumentException("El Objeto no es una matriz.");
        }
    }

    private static boolean esUnaMatrizNoNula(Object unElemento) {
        return unElemento != null && unElemento.getClass().isArray();
    }

    private static boolean esUltimoElemento(int unIndice, int unaLongitud) {
        return (unIndice == unaLongitud - 1);
    }
}
