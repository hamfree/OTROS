package name.ruiz.juanfco.importacsv.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class DaoPoblacionesImpl implements DaoPoblaciones {

    private static final Logger LOG = Logger.getLogger(DaoPoblacionesImpl.class.getName());
    private final static String TABLA = "poblacion";
    private final static String IDPOB = "idpoblacion";
    private final static String IDPRO = "idprovincia";
    private final static String IDCCA = "idccaa";
    private final static String POBLA = "poblacion";

    // Acceso a la BBDD con JDBC
    private JdbcUtil jdbcutl;

    // Singleton
    static DaoPoblacionesImpl dao = new DaoPoblacionesImpl();
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

    public JdbcUtil getJdbcUtl() {
        return jdbcutl;
    }

    public void setJdbcUtl(JdbcUtil jdbcutl) {
        this.jdbcutl = jdbcutl;
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
            jdbcutl.abrirConexion();
            ResultSet rs = jdbcutl.getStmt().executeQuery(sb.toString());
            if (rs.first()) {
                pob = new Poblacion();
                pob.setIdPoblacion(rs.getString(1));
                pob.setIdCCAA(rs.getString(2));
                pob.setIdProvincia(rs.getString(3));
                pob.setPoblacion(rs.getString(4));
            }
            jdbcutl.cerrarConexion();
        } catch (SQLException ex) {
            sb.setLength(0);
            sb.append("Error en DAO : ")
                    .append(ex.getLocalizedMessage());
            LOG.severe(sb.toString());
        } finally {
            try {
                jdbcutl.cerrarConexion();
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
            jdbcutl.abrirConexion();
            int res = jdbcutl.getStmt().executeUpdate(sb.toString());
            if (res == 0 || res == 1) {
                esInsertado = true;
            }
            jdbcutl.cerrarConexion();
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
                jdbcutl.cerrarConexion();
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
