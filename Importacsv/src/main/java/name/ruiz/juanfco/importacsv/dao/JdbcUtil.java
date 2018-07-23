/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;

/**
 *
 * @author hamfree
 */
public interface JdbcUtil {

    public boolean configura(Properties jdbc, String jndi) throws ConfiguracionException;

    public Connection abrirConexion() throws SQLException;

    public javax.sql.DataSource obtenFuenteDatos();

    public Connection getCon();

    public Connection setCon(Connection con);

    public Statement getStmt();

    public Statement setStmt(Statement stmt);

    public Properties getJdbc();

    public Properties setJdbc(Properties jdbc);

    public boolean estaConfigurado();

    public void cerrarConexion() throws SQLException;

}
