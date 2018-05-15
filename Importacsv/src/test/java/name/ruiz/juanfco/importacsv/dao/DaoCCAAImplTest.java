package name.ruiz.juanfco.importacsv.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.CCAA;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author hamfree
 */
public class DaoCCAAImplTest {

    Properties jdbc;
    String jndi;
    DaoCCAAImpl daoCCAA;
    ArrayList<CCAA> listaComunidades;

    public DaoCCAAImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        jdbc = new Properties();
        jdbc.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        jdbc.setProperty("url", "jdbc:mysql://localhost:3306/prueba?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        jdbc.setProperty("usuario", "dummy");
        jdbc.setProperty("clave", "dummy");
        jdbc.setProperty("fichero", "F:\\des\\src\\OTROS\\Importacsv\\target\\classes\\18codmun.csv");
        jndi = null;
        daoCCAA = DaoCCAAImpl.dao;

        listaComunidades = new ArrayList<>();
        listaComunidades.add(new CCAA("11", "Extremadura"));
        listaComunidades.add(new CCAA("12", "Galicia"));
        listaComunidades.add(new CCAA("13", "Madrid, Comunidad de"));
    }

    @After
    public void tearDown() {
        try {
            daoCCAA.cerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of estaConfigurado method, of class DaoCCAAImpl.
     */
    @Test
    public void testEstaConfigurado() {
        System.out.println("estaConfigurado");
        DaoCCAAImpl instance = daoCCAA;
        boolean expResult = true;

        try {
            instance.configura(jdbc, jndi);
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean result = instance.estaConfigurado();
        assertEquals(expResult, result);
    }

    /**
     * Test of conectar method, of class DaoCCAAImpl.
     */
    @Test
    public void testConectar() throws Exception {
        System.out.println("conectar");
        DaoCCAAImpl instance = daoCCAA;

        //Primero configuramos
        try {
            instance.configura(jdbc, jndi);
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Y ahora el test
        instance.conectar();
        assertNotNull(instance.getCon());
        assertNotNull(instance.getStmt());
    }

    /**
     * Test of cerrarConexion method, of class DaoCCAAImpl.
     */
    @Test
    public void testCerrarConexion() throws Exception {
        System.out.println("cerrarConexion");
        DaoCCAAImpl instance = daoCCAA;

        //Primero configuramos y luego conectamos
        try {
            instance.configura(jdbc, jndi);
            instance.conectar();
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Y ahora hacemos el test...
        instance.cerrarConexion();
        Assert.assertTrue("Debe estar cerrada", instance.getCon().isClosed());
    }

    /**
     * Test of inserta method, of class DaoCCAAImpl.
     */
    @Test
    public void testInserta() throws Exception {
        System.out.println("inserta");

        //Primero configuramos y luego conectamos
        DaoCCAAImpl instance = daoCCAA;
        try {
            instance.configura(jdbc, jndi);
            instance.conectar();
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (CCAA ca : listaComunidades) {
            boolean expResult = true;
            boolean result = instance.inserta(ca);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of busca method, of class DaoCCAAImpl.
     */
    @Test
    public void testBusca() throws Exception {
        System.out.println("busca");

        //Primero configuramos y luego conectamos
        DaoCCAAImpl instance = daoCCAA;
        try {
            instance.configura(jdbc, jndi);
            instance.conectar();
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String idCCAA = "20";
        CCAA expResult = new CCAA("20", "Comunidad de Prueba");
        CCAA result = instance.busca(idCCAA);
        assertEquals(expResult, result);
    }

    /**
     * Test of actualiza method, of class DaoCCAAImpl.
     */
    @Test
    public void testActualiza() throws Exception {
        System.out.println("actualiza");
        CCAA ccaa = null;
        DaoCCAAImpl instance = null;
        boolean expResult = false;
        boolean result = instance.actualiza(ccaa);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of borra method, of class DaoCCAAImpl.
     */
    @Test
    public void testBorra() throws Exception {
        System.out.println("borra");
        //Primero configuramos y luego conectamos
        DaoCCAAImpl instance = daoCCAA;
        try {
            instance.configura(jdbc, jndi);
            instance.conectar();
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Borramos la Comunidad de pruebas que insertamos en un test anterior
        String idCCAA = "20";
        boolean expResult = true;
        boolean result = instance.borra(idCCAA);
        assertEquals(expResult, result);
    }

    /**
     * Test of consulta method, of class DaoCCAAImpl.
     */
    @Test
    public void testConsulta() throws Exception {
        System.out.println("consulta");
        //Primero configuramos y luego conectamos
        DaoCCAAImpl instance = daoCCAA;
        try {
            instance.configura(jdbc, jndi);
            instance.conectar();
        } catch (ConfiguracionException ex) {
            Logger.getLogger(DaoCCAAImplTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        String filtro1 = "idCCAA > 10";
        String filtro2 = "idCCAA < 14";
        List<CCAA> result = instance.consulta(filtro1, filtro2);
        assertEquals(listaComunidades, result);
    }

}
