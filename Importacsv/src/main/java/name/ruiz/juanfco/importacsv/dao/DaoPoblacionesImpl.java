package name.ruiz.juanfco.importacsv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class DaoPoblacionesImpl implements DaoPoblaciones {

    private final static String TABLA = "poblacion";
    private final static String IDPOB = "idpoblacion";
    private final static String IDPRO = "idprovincia";
    private final static String IDCCA = "idccaa";
    private final static String POBLA = "poblacion";
    private static final Logger LOG = Logger.getLogger(DaoPoblacionesImpl.class.getName());


    // Singleton
    static DaoPoblacionesImpl dao = new DaoPoblacionesImpl();
    private Connection con = null;
    private Statement stmt = null;
    private static Properties jdbc = null;
    private static String jndi = null;
    private final String SL = System.getProperty("line.separator");

    // Constructor privado
    private DaoPoblacionesImpl() {

    }

    public static DaoPoblacionesImpl getInstance() {
        return getDao();
    }

    public static DaoPoblacionesImpl getDao() {
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
        DaoPoblacionesImpl.jdbc = jdbc;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        DaoPoblacionesImpl.jndi = jndi;
    }

    public void configura(Properties jdbc, String jndi) throws ConfiguracionException {
        //Las validaciones "fuertes" se hacen en el servicio
        if (jndi != null && jdbc == null) {
            setJndi(jndi);
        } else if (jndi == null && jdbc != null) {
            setJdbc(jdbc);
        } else {
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
        StringBuilder sb = new StringBuilder();
        DataSource ds = null;
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
    public Poblacion busca(String idPoblacion) throws SQLException {
        Poblacion pob = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.setLength(0);
            sb.append("SELECT ")
                    .append(IDPOB)
                    .append(",").append(IDCCA)
                    .append(",").append(IDPRO)
                    .append(",").append(POBLA)
                    .append(" FROM ")
                    .append(TABLA)
                    .append(" WHERE ")
                    .append(IDPOB).append("='").append(idPoblacion).append("'");
            conectar();
            ResultSet rs = stmt.executeQuery(sb.toString());
            if (rs.first()) {
                pob = new Poblacion();
                pob.setIdPoblacion(rs.getString(1));
                pob.setIdCCAA(rs.getString(2));
                pob.setIdProvincia(rs.getString(3));
                pob.setPoblacion(rs.getString(4));
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
                        .append(ex.getLocalizedMessage());
                LOG.severe(sb.toString());
            }
        }
        return pob;
    }

    @Override
    public boolean inserta(Poblacion poblacion) throws SQLException {
        boolean esInsertado = false;
        StringBuilder sb = new StringBuilder();
        try {
            sb.setLength(0);
            sb.append("INSERT INTO ").append(TABLA)
                    .append(" (")
                    .append(IDPOB).append(",")
                    .append(IDPRO).append(",")
                    .append(IDCCA).append(",")
                    .append(POBLA)
                    .append(") VALUES ")
                    .append("('").append(filtraCampo(poblacion.getIdPoblacion())).append("',")
                    .append("'").append(filtraCampo(poblacion.getIdProvincia())).append("',")
                    .append("'").append(filtraCampo(poblacion.getIdCCAA())).append("',")
                    .append("'").append(filtraCampo(poblacion.getPoblacion())).append("')");
            conectar();
            int res = stmt.executeUpdate(sb.toString());
            if (res == 0 || res == 1) {
                esInsertado = true;
            }
            cerrarConexion();
        } catch (SQLException ex) {
            sb.setLength(0);
            esInsertado = false;
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
        return esInsertado;
    }

    @Override
    public boolean actualiza(Poblacion poblacion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean borra(String idPoblacion) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Poblacion> consulta(String filtro) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String filtraCampo(String campo) {
        StringBuilder sb = new StringBuilder();
        String comilla = "'";
        if (campo.contains(comilla)) {
            for (int i = 0; i < campo.length(); i++) {
                if (campo.charAt(i) == '\'') {
                    sb.append("\\").append(campo.charAt(i));
                } else {
                    sb.append(campo.charAt(i));
                }
            }
            return sb.toString();
        }
        return campo;
    }
}
