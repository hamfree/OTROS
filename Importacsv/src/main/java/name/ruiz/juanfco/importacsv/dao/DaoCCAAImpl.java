package name.ruiz.juanfco.importacsv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public class DaoCCAAImpl implements DaoCCAA {

    private final static String TABLA = "ccaa";
    private final static String ID = "idccaa";
    private final static String IDNOM = "nombre";
    private static final Logger LOG = Logger.getLogger(DaoCCAAImpl.class.getName());

    // Singleton
    static DaoCCAAImpl dao = new DaoCCAAImpl();
    private Connection con = null;
    private Statement stmt = null;
    private static Properties jdbc = null;
    private static String jndi = null;
    private final String SL = System.getProperty("line.separator");

    // Constructor privado
    private DaoCCAAImpl() {

    }

    public static DaoCCAAImpl getInstance() {
        return getDao();
    }

    public static DaoCCAAImpl getDao() {
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
        DaoCCAAImpl.jdbc = jdbc;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        DaoCCAAImpl.jndi = jndi;
    }

    public void configura(Properties jdbc, String jndi) throws ConfiguracionException {
        //Las validaciones "fuertes" se hacen en el servicio
        if (jndi != null) {
            setJndi(jndi);
        } else if (jdbc != null) {
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

    public void conectar() throws SQLException {
        if (estaConfigurado()) {
            if (jndi != null) {
                con = obtenerDS().getConnection();
            } else if (jdbc != null) {
                con = obtenerConexion();
            }
            setStmt(getCon().createStatement());
        } else {
            LOG.severe("Debe configurar el DAO antes de usarlo.");
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
    public CCAA busca(String idCCAA) throws SQLException {
        StringBuilder sb = new StringBuilder();
        CCAA ccaa = null;
        try {
            sb.setLength(0);
            sb.append("SELECT ")
                    .append(ID)
                    .append(",").append(IDNOM)
                    .append(" FROM ")
                    .append(TABLA)
                    .append(" WHERE ")
                    .append(ID).append("='").append(idCCAA).append("'");
            conectar();
            ResultSet rs = stmt.executeQuery(sb.toString());
            if (rs.first()) {
                ccaa = new CCAA();
                ccaa.setIdccaa(rs.getString(1));
                ccaa.setNombre(rs.getString(2));
            }
            cerrarConexion();
        } catch (SQLException ex) {
            sb.setLength(0);
            sb.append("Error en DAO : ")
                    .append(ex.getLocalizedMessage());
            LOG.severe(sb.toString());
        } finally {
            try {
                cerrarConexion();
            } catch (SQLException ex) {
                sb.setLength(0);
                sb.append("Error en DAO : ")
                        .append(ex);
                LOG.severe(sb.toString());
            }
        }
        return ccaa;
    }

    @Override
    public boolean inserta(CCAA ccaa) throws SQLException {
        boolean esInsertada = false;
        StringBuilder sb = new StringBuilder();
        try {
            sb.setLength(0);
            sb.append("INSERT INTO ").append(TABLA)
                    .append(" (")
                    .append(ID).append(",")
                    .append(IDNOM)
                    .append(") VALUES ")
                    .append("('").append(ccaa.getIdccaa()).append("',")
                    .append("'").append(ccaa.getNombre()).append("'")
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
    public boolean actualiza(CCAA ccaa) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean borra(String idCCAA) throws SQLException {
        boolean resultado = false;
        StringBuilder sb = new StringBuilder();

        if (idCCAA != null && idCCAA.length() > 0) {
            try {
                sb.setLength(0);
                sb.append("DELETE FROM ")
                        .append(TABLA)
                        .append(" WHERE ")
                        .append(ID)
                        .append("=")
                        .append("'").append(idCCAA).append("'");
                conectar();
                int res = stmt.executeUpdate(sb.toString());
                if (res > 0) {
                    resultado = true;
                }
            } catch (SQLException ex) {
                sb.setLength(0);
                sb.append("Error en DAO : ")
                        .append(ex.getLocalizedMessage());
                LOG.severe(sb.toString());
            } finally {
                try {
                    cerrarConexion();
                } catch (SQLException ex) {
                    sb.setLength(0);
                    sb.append("Error en DAO : ")
                            .append(ex);
                    LOG.severe(sb.toString());
                }
            }
        }
        return resultado;
    }

    @Override
    public List<CCAA> consulta(String... filtros) throws SQLException {
        ArrayList<CCAA> alCCAA = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (filtros != null && filtros.length > 0) {
                sb.setLength(0);
                sb.append("SELECT ")
                        .append(ID)
                        .append(",").append(IDNOM)
                        .append(" FROM ")
                        .append(TABLA)
                        .append(" WHERE ");
                //Nota: Los filtros deben ser expresiones SQL validas...
                for (String f : filtros) {
                    sb.append(" ")
                            .append(f)
                            .append(" AND ");
                }
                sb.append(" 1 = 1");
                conectar();
                ResultSet rs = stmt.executeQuery(sb.toString());
                while (rs.next()) {
                    CCAA ccaa = new CCAA();
                    ccaa.setIdccaa(rs.getString(1));
                    ccaa.setNombre(rs.getString(2));
                    if (alCCAA == null) {
                        alCCAA = new ArrayList<>();
                    }
                    alCCAA.add(ccaa);
                }
                cerrarConexion();
            }
        } catch (SQLException ex) {
            sb.setLength(0);
            sb.append("Error en DAO : ")
                    .append(ex.getLocalizedMessage());
            LOG.severe(sb.toString());
        } finally {
            try {
                cerrarConexion();
            } catch (SQLException ex) {
                sb.setLength(0);
                sb.append("Error en DAO : ")
                        .append(ex);
                LOG.severe(sb.toString());
            }
        }
        return alCCAA;
    }

}
