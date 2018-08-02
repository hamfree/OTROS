package name.ruiz.juanfco.importacsv.servicio;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.dao.DaoPoblacionesImpl;
import name.ruiz.juanfco.importacsv.dao.JdbcUtil;
import name.ruiz.juanfco.importacsv.dao.JdbcUtilImpl;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class PoblacionesServicioImpl implements PoblacionesServicio {

    private static final Logger LOG = Logger.getLogger(PoblacionesServicioImpl.class.getName());
    private final String SL = System.getProperty("line.separator");

    private DaoPoblacionesImpl dao;
    private JdbcUtil jdbcutl;
    private Properties jdbc;
    private String jndi;


    /**
     *
     */
    public PoblacionesServicioImpl() {
        dao = DaoPoblacionesImpl.getInstance();
    }

    /**
     *
     * @param jdbc
     * @throws ConfiguracionException
     */
    public PoblacionesServicioImpl(Properties jdbc) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();

        if (vp.validaJDBC(jdbc)) {
            dao = DaoPoblacionesImpl.getInstance();
            setJdbc(jdbc);
            try {
                jdbcutl = new JdbcUtilImpl();
                jdbcutl.configura(getJdbc(), null);
            } catch (ConfiguracionException ex) {
                LOG.severe("¡Parámetros JDBC nulos o incorrectos!");
                throw new ConfiguracionException("¡Parámetros JDBC nulos o incorrectos!");
            }
        } else {
            LOG.severe("¡Parámetros JDBC nulos o incorrectos!");
            throw new ConfiguracionException("¡Parámetros JDBC nulos o incorrectos!");
        }
    }

    /**
     *
     * @param jndi
     * @throws ConfiguracionException
     */
    public PoblacionesServicioImpl(String jndi) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();
        if (vp.validaJNDI(jndi)) {
            dao = DaoPoblacionesImpl.getInstance();
            setJdbc(jdbc);
            try {
                jdbcutl = new JdbcUtilImpl();
                jdbcutl.configura(null, getJndi());
            } catch (ConfiguracionException ex) {
                LOG.severe("¡Parámetro JNDI nulo o incorrecto!");
                throw new ConfiguracionException("¡Parámetro JNDI nulo o incorrecto!");
            }
        } else {
            throw new ConfiguracionException("¡Parámetro JNDI nulo o incorrecto!");
        }
    }

    /**
     *
     * @param idPoblacion
     * @return
     */
    @Override
    public Poblacion busca(String idPoblacion) {
        throw new UnsupportedOperationException(" ERROR: La búsqueda de una población no está soportada aún.");
    }

    /**
     *
     * @param poblacion
     * @return
     */
    @Override
    public boolean inserta(Poblacion poblacion) {
        StringBuilder sb = new StringBuilder();
        boolean esInsertado = false;
        try {
            esInsertado = dao.inserta(poblacion);
        } catch (SQLException ex) {
            sb.append("ERROR: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        }
        return esInsertado;
    }

    /**
     *
     * @param idPoblacion
     * @return
     */
    @Override
    public boolean elimina(String idPoblacion) {
        throw new UnsupportedOperationException(" ERROR: La eliminación de una población no está soportada aún.");
    }

    /**
     *
     * @param poblacion
     * @return
     */
    @Override
    public boolean modifica(Poblacion poblacion) {
        throw new UnsupportedOperationException(" ERROR: La modificación de una población no está soportada aún.");
    }

    public final String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public final Properties getJdbc() {
        return jdbc;
    }

    public final void setJdbc(Properties jdbc) {
        this.jdbc = jdbc;
    }

    public final JdbcUtil getJdbcutl() {
        return jdbcutl;
    }

    public final void setJdbcutl(JdbcUtil jdbcutl) {
        this.jdbcutl = jdbcutl;
    }

}
