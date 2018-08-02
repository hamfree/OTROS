package name.ruiz.juanfco.importacsv.vista;

/**
 *
 * @author hamfree
 */
public class VistaImpl implements Vista {

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

    @Override
    public void showObjectAsTable(Object obj) {

    }

    @Override
    public void showObjectAsList(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showObjectAsCsv(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
