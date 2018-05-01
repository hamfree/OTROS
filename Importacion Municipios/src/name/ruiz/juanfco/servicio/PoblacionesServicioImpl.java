package name.ruiz.juanfco.servicio;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import name.ruiz.juanfco.dao.DaoPoblacionesImpl;
import name.ruiz.juanfco.excepciones.ConfiguracionException;
import name.ruiz.juanfco.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
public class PoblacionesServicioImpl implements PoblacionesServicio {

    private DaoPoblacionesImpl dao;
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
            this.jdbc = jdbc;
            try {
                dao.configura(this.jdbc, null);
            } catch (ConfiguracionException ex) {
                Logger.getLogger(PoblacionesServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new ConfiguracionException("¡Parámetros JDBC nulos o incorrectos!");
            }
        } else {
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
                Logger.getLogger(PoblacionesServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param poblacion
     * @return
     */
    @Override
    public boolean inserta(Poblacion poblacion) {
        boolean esInsertado = false;
        try {
            esInsertado = dao.inserta(poblacion);
        } catch (SQLException ex) {
            Logger.getLogger(PoblacionesServicioImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param poblacion
     * @return
     */
    @Override
    public boolean modifica(Poblacion poblacion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
