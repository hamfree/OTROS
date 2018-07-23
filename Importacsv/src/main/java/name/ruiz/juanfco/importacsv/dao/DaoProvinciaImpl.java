package name.ruiz.juanfco.importacsv.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public class DaoProvinciaImpl implements DaoProvincia {

    private static final Logger LOG = Logger.getLogger(DaoProvinciaImpl.class.getName());
    private final static String TABLA = "provincias";
    private final static String ID = "idprovincias";
    private final static String IDCCAA = "idccaa";
    private final static String NOMBRE = "nombre";
    private final String SL = System.getProperty("line.separator");

    // Acceso a la BBDD con JDBC
    private JdbcUtil jdbcutl;

    // Singleton
    static DaoProvinciaImpl dao = new DaoProvinciaImpl();

    // Constructor privado
    private DaoProvinciaImpl() {

    }

    public static DaoProvinciaImpl getInstance() {
        return getDao();
    }

    public static DaoProvinciaImpl getDao() {
        return dao;
    }

    public JdbcUtil getJdbcUtl() {
        return jdbcutl;
    }

    public void setJdbcUtl(JdbcUtil jdbcutl) {
        this.jdbcutl = jdbcutl;
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
