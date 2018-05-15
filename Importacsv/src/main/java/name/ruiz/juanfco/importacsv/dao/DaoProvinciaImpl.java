package name.ruiz.juanfco.importacsv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public class DaoProvinciaImpl implements DaoProvincia {

    private final static String TABLA = "provincias";
    private final static String ID = "idprovincias";
    private final static String IDCCAA = "idccaa";
    private final static String NOMBRE = "nombre";
    private static final Logger LOG = Logger.getLogger(DaoProvinciaImpl.class.getName());
    private final String SL = System.getProperty("line.separator");

    // Singleton
    static DaoProvinciaImpl dao = new DaoProvinciaImpl();
    private Connection con = null;
    private Statement stmt = null;
    private static Properties jdbc = null;
    private static String jndi = null;

    // Constructor privado
    private DaoProvinciaImpl() {

    }

    public static DaoProvinciaImpl getInstance() {
        return getDao();
    }

    public static DaoProvinciaImpl getDao() {
        return dao;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public Properties getJdbc() {
        return jdbc;
    }

    public void setJdbc(Properties jdbc) {
        DaoProvinciaImpl.jdbc = jdbc;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        DaoProvinciaImpl.jndi = jndi;
    }

    public void configura(Properties jdbc, String jndi) throws ConfiguracionException {
        //Las validaciones "fuertes" se hacen en el servicio
        if (jndi != null && jdbc == null) {
            setJndi(jndi);
        } else if (jndi == null && jdbc != null) {
            setJdbc(jdbc);
        } else {
            LOG.severe("Parametros nulos de configuracion.");
            throw new ConfiguracionException("Parametros nulos de configuracion.");
        }
    }

    public boolean estaConfigurado() {
        if (jdbc == null && jndi == null) {
            return false;
        } else if (jdbc == null && jndi.length() == 0) {
            return false;
        }
        return true;
    }

    private static javax.sql.DataSource obtenerDS() {
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

    private Connection obtenerConexion() throws SQLException {
        StringBuilder sb = new StringBuilder();
        try {
            Class.forName(jdbc.getProperty("driver"));
            con = DriverManager.getConnection(jdbc.getProperty("url"),
                    jdbc.getProperty("usuario"), jdbc.getProperty("clave"));
        } catch (ClassNotFoundException ex) {
            // Estos errores son irrecuperables. Nos salimos del programa.
            sb.append("Error en la conexion a la BBDD: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("*NO* se puede continuar. Terminando el programa ...");
            LOG.severe(sb.toString());
            System.exit(-255);
        } catch (SQLSyntaxErrorException ex) {
            // Estos errores son irrecuperables. Nos salimos del programa.
            sb.append("Error en la conexion a la BBDD: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del fabricante: ")
                    .append(ex.getErrorCode())
                    .append(SL)
                    .append("*NO* se puede continuar. Terminando el programa ...");
            LOG.severe(sb.toString());
            System.exit(-255);
        }
        return con;
    }

    public void conectar() throws SQLException {
        if (estaConfigurado()) {
            if (jndi != null) {
                con = obtenerDS().getConnection();
            } else if (jdbc != null) {
                con = obtenerConexion();
            }
            setStmt(getCon().createStatement());
        } else {
            throw new SQLException("Debe configurar el DAO antes de usarlo.");
        }
    }

    public void cerrarConexion() throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        if (con != null) {
            con.close();
        }
    }

    @Override
    public Provincia busca(String idProvincia) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean inserta(Provincia provincia) throws SQLException {
        boolean esInsertada = false;
        StringBuilder sb = new StringBuilder();
        try {
            sb.setLength(0);
            sb.append("INSERT INTO ").append(TABLA)
                    .append(" (")
                    .append(ID).append(",")
                    .append(IDCCAA).append(",")
                    .append(NOMBRE)
                    .append(") VALUES ")
                    .append("('").append(provincia.getIdprovincias()).append("',")
                    .append("'").append(provincia.getIdccaa()).append("',")
                    .append("'").append(provincia.getNombre()).append("'")
                    .append(")");
            conectar();
            int res = stmt.executeUpdate(sb.toString());
            if (res == 0 || res == 1) {
                esInsertada = true;
            }
            cerrarConexion();
        } catch (SQLException ex) {
            esInsertada = false;
            sb.setLength(0);
            sb.append("Error en DAO : ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de Error del Vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        } finally {
            try {
                cerrarConexion();
            } catch (SQLException ex) {
                sb.setLength(0);
                sb.append("Error en DAO : ")
                        .append(ex.getLocalizedMessage())
                        .append(SL)
                        .append("Codigo de Error del Vendedor: ")
                        .append(ex.getErrorCode());
                LOG.severe(sb.toString());
            }
        }
        return esInsertada;
    }

    @Override
    public boolean actualiza(Provincia provincia) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean borra(String idProvincia) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Provincia> consulta(String filtro) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
