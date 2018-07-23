package name.ruiz.juanfco.importacsv.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
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

    // Acceso a la BBDD con JDBC
    private JdbcUtil jdbcutl;

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

    public JdbcUtil getJdbcUtl() {
        return jdbcutl;
    }

    public void setJdbcUtl(JdbcUtil jdbcutl) {
        this.jdbcutl = jdbcutl;
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
            jdbcutl.abrirConexion();
            ResultSet rs = jdbcutl.getStmt().executeQuery(sb.toString());
            if (rs.first()) {
                ccaa = new CCAA();
                ccaa.setIdccaa(rs.getString(1));
                ccaa.setNombre(rs.getString(2));
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
            jdbcutl.abrirConexion();
            int res = jdbcutl.getStmt().executeUpdate(sb.toString());
            if (res == 0 || res == 1) {
                esInsertada = true;
            }
            jdbcutl.cerrarConexion();
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
                jdbcutl.abrirConexion();
                int res = jdbcutl.getStmt().executeUpdate(sb.toString());
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
                    jdbcutl.cerrarConexion();
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
                jdbcutl.abrirConexion();
                ResultSet rs = jdbcutl.getStmt().executeQuery(sb.toString());
                while (rs.next()) {
                    CCAA ccaa = new CCAA();
                    ccaa.setIdccaa(rs.getString(1));
                    ccaa.setNombre(rs.getString(2));
                    if (alCCAA == null) {
                        alCCAA = new ArrayList<>();
                    }
                    alCCAA.add(ccaa);
                }
                jdbcutl.cerrarConexion();
            }
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
                        .append(ex);
                LOG.severe(sb.toString());
            }
        }
        return alCCAA;
    }

}
