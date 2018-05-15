package name.ruiz.juanfco.servicio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;
import name.ruiz.juanfco.importacsv.servicio.ImportaPobImpl;
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

    static final Logger LOG = Logger.getLogger(ImportaPobImpl.class.getName());
    static final String RUTA = "C:\\des\\src\\OTROS\\Importacsv\\target\\classes\\testMunicipios.csv";
    static final String FLOG = "./pruebas.log";

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
        // creando manejador de archivo
        FileHandler fh;
        try {
            fh = new FileHandler(FLOG, 10485760, 3, true);
            fh.setLevel(Level.ALL); // level
            fh.setFormatter(new SimpleFormatter()); //formatter

            // agregar el manejador de archivo al LOG
            LOG.addHandler(fh);

            // el manejador de consola se agrega automaticamente, solo
            // cambiamos el nivel de detalle a desplegar
            LOG.getHandlers()[0].setLevel(Level.SEVERE);

            // se establece el nivel predeterminado global
            LOG.setLevel(Level.INFO);
        } catch (IOException | SecurityException ex) {
            String nc = ImportaCSVimplTest.class.getName();
            Logger.getLogger(nc).log(Level.SEVERE, null, ex);
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of importa method, of class ImportaPobImpl.
     */
    @Test
    public void testImporta() {
        System.out.println("\nTEST importa, que debe funcionar");

        File fcsv = new File(RUTA);
        ImportaPobImpl instance = new ImportaPobImpl();

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
        System.out.println("\nTEST importa con fichero erroneo");
        File fcsv = new File("F:\\ficheroQueNoExiste.csv");
        ImportaPobImpl instance = new ImportaPobImpl();

        List<Poblacion> result = instance.importa(fcsv, "Windows-1252", ";", false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

    @Test
    public void testImportaConCodificacionErronea() {
        System.out.println("\nTEST importa con codificacion erronea");
        File fcsv = new File(RUTA);
        ImportaPobImpl instance = new ImportaPobImpl();

        List<Poblacion> result = instance.importa(fcsv, "fake-charset", ";", false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

    @Test
    public void testImportaConDelimitadorErroneo() {
        System.out.println("\nTEST importa con delimitador erroneo");
        File fcsv = new File(RUTA);
        ImportaPobImpl instance = new ImportaPobImpl();

        List<Poblacion> result = instance.importa(fcsv, "fake-charset", null, false);

        List<Poblacion> expectedResult = null;
        //Se hace el test.
        assertEquals(expectedResult, result);
    }

}
