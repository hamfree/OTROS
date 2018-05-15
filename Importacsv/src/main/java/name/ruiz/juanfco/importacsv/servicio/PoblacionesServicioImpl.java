package name.ruiz.juanfco.importacsv.servicio;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.dao.DaoPoblacionesImpl;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class PoblacionesServicioImpl implements PoblacionesServicio {

    private DaoPoblacionesImpl dao;
    private Properties jdbc;
    private String jndi;
    private static final Logger LOG = Logger.getLogger(PoblacionesServicioImpl.class.getName());
    private final String SL = System.getProperty("line.separator");

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
            this.jdbc = jdbc;
            try {
                dao.configura(this.jdbc, null);
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
            this.jndi = jndi;
            try {
                dao.configura(null, this.jndi);
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param poblacion
     * @return
     */
    @Override
    public boolean modifica(Poblacion poblacion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public Properties getJdbc() {
        return jdbc;
    }

    public void setJdbc(Properties jdbc) {
        this.jdbc = jdbc;
    }

}
