package name.ruiz.juanfco.servicio;

import name.ruiz.juanfco.importacsv.servicio.ImportaCSVimpl;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hamfree
 */
public class ImportaCSVimplTest {

    public ImportaCSVimplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of importa method, of class ImportaCSVimpl.
     */
    @Test
    public void testImporta() {
        System.out.println("importa");
        File fcsv = new File("F:\\des\\src\\OTROS\\Importacion Municipios\\testMunicipios.csv");
        ImportaCSVimpl instance = new ImportaCSVimpl();

        // Resultados esperados
        List<Poblacion> expResult = new ArrayList<>();
        expResult.add(new Poblacion("051", "01", "16", "Agurain/Salvatierra"));
        expResult.add(new Poblacion("001", "01", "16", "Alegría-Dulantzi"));
        expResult.add(new Poblacion("002", "01", "16", "Amurrio"));
        expResult.add(new Poblacion("049", "01", "16", "Añana"));
        expResult.add(new Poblacion("003", "01", "16", "Aramaio"));
        expResult.add(new Poblacion("006", "01", "16", "Armiñón"));
        expResult.add(new Poblacion("037", "01", "16", "Arraia-Maeztu"));
        expResult.add(new Poblacion("008", "01", "16", "Arratzua-Ubarrundia"));
        expResult.add(new Poblacion("004", "01", "16", "Artziniega"));
        expResult.add(new Poblacion("009", "01", "16", "Asparrena"));

        //Se importan los valores del CSV
        List<Poblacion> result = instance.importa(fcsv, "Windows-1252", ";", false);
        System.out.println("*** resultado importa() ***");
        for (Poblacion p : result) {
            System.out.println(p.toString());
        }
        System.out.println("*** resultado esperado ***");
        for (Poblacion p : expResult) {
            System.out.println(p.toString());
        }

        //Se hace el test.
        assertEquals(expResult, result);
    }

    @Test
    public void testImportaSinFichero() {
        System.out.println("importa con fichero erroneo");
        File fcsv = new File("F:\\ficheroQueNoExiste.csv");
        ImportaCSVimpl instance = new ImportaCSVimpl();

        List<Poblacion> result = instance.importa(fcsv, "Windows-1252", ";", false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

    @Test
    public void testImportaConCodificacionErronea() {
        System.out.println("importa con codificacion erronea");
        File fcsv = new File("F:\\des\\src\\OTROS\\Importacion Municipios\\testMunicipios.csv");
        ImportaCSVimpl instance = new ImportaCSVimpl();

        List<Poblacion> result = instance.importa(fcsv, "fake-charset", ";", false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

    @Test
    public void testImportaConDelimitadorErroneo() {
        System.out.println("importa con delimitador erroneo");
        File fcsv = new File("F:\\des\\src\\OTROS\\Importacion Municipios\\testMunicipios.csv");
        ImportaCSVimpl instance = new ImportaCSVimpl();

        List<Poblacion> result = instance.importa(fcsv, "fake-charset", null, false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

}
