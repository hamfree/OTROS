package name.ruiz.juanfco.importacsv.vista;

import java.util.logging.Logger;

/**
 *
 * @author hamfree
 */
public class VistaImpl implements Vista {

    private static final Logger LOG = Logger.getLogger(VistaImpl.class.getName());
    private final String S = "|";
    private final String T = "\"";
    private final String CO = "'";
    private final String LA = "{";
    private final String LC = "}";
    private final String CA = "[";
    private final String CC = "]";
    private final String PA = " (";
    private final String PC = ") ";
    private final String P = ".";
    private final String DP = ": ";
    private final String TAB = "    ";
    private final String NULL = "null";
    private final String ELE = " elemento";
    private final String ELES = " elementos";
    private final String VAC = "vacío";
    private final String CLV = "CLAVE=";
    private final String VAL = "VALOR=";

    /**
     *
     * @param msj
     * @return
     */
    @Override
    public void showObjectAsTable(Object obj) {
        StringBuilder sb = new StringBuilder();
    }

    /**
     *
     * @param mapa
     * @param nivel
     * @return
     */
    @Override
    public void showObjectAsList(Object obj) {
        StringBuilder sb = new StringBuilder();
    }

    /**
     *
     * @param msj
     * @return
     */
    @Override
    public void showObjectAsCsv(Object obj) {
        StringBuilder sb = new StringBuilder();
    }

}
