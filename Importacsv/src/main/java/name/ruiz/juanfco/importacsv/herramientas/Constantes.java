package name.ruiz.juanfco.importacsv.herramientas;

/**
 * Constantes recogidas de utilidad muy general.
 *
 * Todas las constantes deben ser inmutables. Ninguna instancia de esta clase
 * puede ser construida.
 */
public final class Constantes {

    /**
     * Previene la construccion del objeto fuera de esta clase.
     */
    private Constantes() {
        //vacio
    }

    /**
     * Solo se refieren a primitivos y objetos inmutables.
     *
     * Las matrices (arrays en ingles) presentan un problema ya que las matrices
     * son siempre mutables- NO USE campos de matrices publicos estaticos y
     * finales. Un estilo es utilizar una Lista no modificable, construida en un
     * bloque de inicializador estatico.
     *
     * Otro estilo es usar una matriz privada y envolverla de esta forma:
     * <pre>
     *  private static final Vehiculo[] VEHICULOS_PRIVADOS = {...};
     *  public static final List VEHICULOS =
     *         Collections.unmodifiableList(Arrays.asList(VEHICULOS_PRIVADOS));
     * </pre>
     */
    //caracteres
    public static final String NUEVA_LINEA = System.getProperty("line.separator");
    public static final String SEPARADOR_FICHEROS = System.getProperty("file.separator");
    public static final String SEPARADOR_RUTA = System.getProperty("path.separator");

    public static final String CADENA_VACIA = "";
    public static final String ESPACIO = " ";
    public static final String PUNTO = ".";
    public static final String TAB = "\t";

    //signos algebraicos
    public static final int POSITIVO = 1;
    public static final int NEGATIVO = -1;
    public static final String SIGNO_MAS = "+";
    public static final String SIGNO_MENOS = "-";
}
