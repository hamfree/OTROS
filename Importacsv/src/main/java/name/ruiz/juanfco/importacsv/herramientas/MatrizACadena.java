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
     * <code>unObjeto</code> es una psoible matriz posiblemente-nula cuyos
     * elementos son primitivos u objetos; matrices de matrices son también
     * válidas, en cuyo caso <code>aArray</code> se representa de forma anidada
     * y recursiva.
     *
     * @param unObjeto
     * @return una cadena con la representación textual del contenido de una
     * matriz
     */
    public static String get(Object unObjeto) {
        if (unObjeto == null) {
            return NULO;
        }
        compruebaObjetoEsUnaMatriz(unObjeto);

        StringBuilder resultado = new StringBuilder(CAR_INICIAL);
        int longitud = Array.getLength(unObjeto);
        for (int idx = 0; idx < longitud; ++idx) {
            Object item = Array.get(unObjeto, idx);
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

    /**
     * Comprueba que el objeto que se le pasa es del tipo array. Si no lo es
     * lanza una excepción <code>IllegalArgumentException</code>
     *
     * @param unObjeto El objeto que se quiere comprobar
     */
    private static void compruebaObjetoEsUnaMatriz(Object unObjeto) {
        if (!unObjeto.getClass().isArray()) {
            throw new IllegalArgumentException("El Objeto no es una matriz.");
        }
    }

    /**
     * Comprueba si el objeto que se le pasa es una matriz y además no es nula.
     * En ese caso devuelve <code>true</code>. Devuelve <code>false</code> en el
     * resto de casos.
     *
     * @param unObjeto El objeto que se quiere comprobar
     * @return un booleano que será true en el caso de que el objeto sea una
     * matriz y no sea nula
     */
    private static boolean esUnaMatrizNoNula(Object unObjeto) {
        return unObjeto != null && unObjeto.getClass().isArray();
    }

    /**
     * Comprueba si el indice que se le pasa es el último teniendo en cuenta los
     * parámetros que se le pasan. En caso de que el índice sea el último
     * devolverá <code>true</code>. En el resto de casos devuelve
     * <code>false</code>.
     *
     * @param unIndice un entero con el índice del que se quiere comprobar su
     * posición
     * @param unaLongitud un entero con la longitud de la matriz
     * @return un booleano
     */
    private static boolean esUltimoElemento(int unIndice, int unaLongitud) {
        return (unIndice == unaLongitud - 1);
    }
}
