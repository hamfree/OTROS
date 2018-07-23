/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;

/**
 *
 * @author hamfree
 */
public class JdbcUtilImpl implements JdbcUtil {

    private static final Logger LOG = Logger.getLogger(JdbcUtilImpl.class.getName());
    private Connection con = null;
    private final Statement stmt = null;
    private final Properties jdbc = null;
    private final String jndi = null;

    @Override
    public boolean configura(Properties jdbc, String jndi) throws ConfiguracionException {
        //Las validaciones "fuertes" se hacen en el servicio
        boolean estaConfigurado = false;
        if (jndi != null) {
            setJndi(jndi);
            estaConfigurado = true;
        } else if (jdbc != null) {
            setJdbc(jdbc);
            estaConfigurado = true;
        } else {
            LOG.severe("Parametros nulos de configuracion.");
            throw new ConfiguracionException("Parametros nulos de configuracion.");
        }
        return estaConfigurado;
    }

    @Override
    public Connection abrirConexion() throws SQLException {
        StringBuilder sb = new StringBuilder();
        try {
            Class.forName(jdbc.getProperty("driver"));
            con = DriverManager.getConnection(jdbc.getProperty("url"),
                    jdbc.getProperty("usuario"), jdbc.getProperty("clave"));
        } catch (ClassNotFoundException ex) {
            // Estos errores son irrecuperables. Nos salimos del programa.
            sb.append("Error en la conexion a la BBDD: ")
                    .append(ex.getLocalizedMessage())
                    .append("\n*NO* se puede continuar. Terminando el programa ...");
            LOG.severe(sb.toString());
            System.exit(-255);
        } catch (SQLSyntaxErrorException ex) {
            // Estos errores son irrecuperables. Nos salimos del programa.
            sb.append("Error en la conexion a la BBDD: ")
                    .append(ex.getLocalizedMessage())
                    .append("\nCodigo de error del fabricante: ")
                    .append(ex.getErrorCode())
                    .append("\n*NO* se puede continuar. Terminando el programa ...");
            LOG.severe(sb.toString());
            System.exit(-255);
        }
        return con;
    }

    @Override
    public DataSource obtenFuenteDatos() {
        DataSource ds = null;
        StringBuilder sb = new StringBuilder();
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup(jndi);
        } catch (NamingException e) {
            sb.append("Error al obtener la fuente de datos. ")
                    .append(e.getLocalizedMessage());
            LOG.severe(sb.toString());
        }
        return ds;
    }

    @Override
    public void cerrarConexion() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    @Override
    public Connection getCon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Connection setCon(Connection con) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement getStmt() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Statement setStmt(Statement stmt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Properties getJdbc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Properties setJdbc(Properties jdbc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean estaConfigurado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setJndi(String jndi) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
