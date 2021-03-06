/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.servicio;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.dao.DaoProvinciaImpl;
import name.ruiz.juanfco.importacsv.dao.JdbcUtil;
import name.ruiz.juanfco.importacsv.dao.JdbcUtilImpl;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public class ProvinciaServicioImpl implements ProvinciaServicio {

    private static final Logger LOG = Logger.getLogger(ProvinciaServicioImpl.class.getName());
    private final String SL = System.getProperty("line.separator");
    private DaoProvinciaImpl dao;
    private JdbcUtil jdbcutl;
    private Properties jdbc;
    private String jndi;


    public ProvinciaServicioImpl() {
        dao = DaoProvinciaImpl.getDao();
    }

    public ProvinciaServicioImpl(Properties jdbc) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();

        if (vp.validaJDBC(jdbc)) {
            dao = DaoProvinciaImpl.getInstance();
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

    public ProvinciaServicioImpl(String jdni) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();
        if (vp.validaJNDI(jndi)) {
            dao = DaoProvinciaImpl.getInstance();
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
     * @param idProvincia
     * @return
     */
    @Override
    public Provincia busca(String idProvincia) {
        throw new UnsupportedOperationException(" ERROR: La consulta  de provincias no está soportada aún.");
    }

    /**
     *
     * @param provincia
     * @return
     */
    @Override
    public boolean inserta(Provincia provincia) {
        StringBuilder sb = new StringBuilder();
        boolean esInsertada = false;
        try {
            esInsertada = dao.inserta(provincia);
        } catch (SQLException ex) {
            sb.append("ERROR: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        }
        return esInsertada;
    }

    /**
     *
     * @param idProvincia
     * @return
     */
    @Override
    public boolean elimina(String idProvincia) {
        throw new UnsupportedOperationException(" ERROR: La eliminación  de provincias no está soportada aún.");
    }

    /**
     *
     * @param provincia
     * @return
     */
    @Override
    public boolean modifica(Provincia provincia) {
        throw new UnsupportedOperationException(" ERROR: La modificación  de provincias no está soportada aún.");
    }

    public DaoProvinciaImpl getDao() {
        return dao;
    }

    public void setDao(DaoProvinciaImpl dao) {
        this.dao = dao;
    }

    public final Properties getJdbc() {
        return jdbc;
    }

    public final void setJdbc(Properties jdbc) {
        this.jdbc = jdbc;
    }

    public final String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

    public final JdbcUtil getJdbcutl() {
        return jdbcutl;
    }

    public final void setJdbcutl(JdbcUtil jdbcutl) {
        this.jdbcutl = jdbcutl;
    }

}
