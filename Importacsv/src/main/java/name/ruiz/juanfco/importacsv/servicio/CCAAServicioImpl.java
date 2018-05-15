/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.servicio;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import name.ruiz.juanfco.importacsv.dao.DaoCCAAImpl;
import name.ruiz.juanfco.importacsv.excepciones.ConfiguracionException;
import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public class CCAAServicioImpl implements CCAAServicio {

    private DaoCCAAImpl dao;
    private Properties jdbc;
    private String jndi;
    private static final Logger LOG = Logger.getLogger(CCAAServicioImpl.class.getName());
    private final String SL = System.getProperty("line.separator");

    public CCAAServicioImpl() {
        dao = DaoCCAAImpl.getDao();
    }

    public CCAAServicioImpl(Properties jdbc) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();

        if (vp.validaJDBC(jdbc)) {
            dao = DaoCCAAImpl.getInstance();
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

    public CCAAServicioImpl(String jndi) throws ConfiguracionException {
        ValidaParametros vp = new ValidaParametrosImpl();
        if (vp.validaJNDI(jndi)) {
            dao = DaoCCAAImpl.getInstance();
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


    @Override
    public CCAA busca(String idCCAA) {
        StringBuffer sb = new StringBuffer();
        CCAA ccaa = null;
        try {
            if (idCCAA != null && idCCAA.length() > 0) {
                ccaa = dao.busca(idCCAA);
            }
        } catch (SQLException ex) {
            sb.append("ERROR: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        }
        return ccaa;
    }

    @Override
    public boolean inserta(CCAA ccaa) {
        StringBuilder sb = new StringBuilder();
        boolean esInsertado = false;
        try {
            esInsertado = dao.inserta(ccaa);
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

    @Override
    public boolean elimina(String idCCAA) {
        StringBuilder sb = new StringBuilder();
        boolean esEliminado = false;
        try {
            esEliminado = dao.borra(idCCAA);
        } catch (SQLException ex) {
            sb.append("ERROR: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        }
        return esEliminado;
    }

    @Override
    public boolean modifica(CCAA ccaa) {
        StringBuilder sb = new StringBuilder();
        boolean esModificado = false;
        try {
            esModificado = dao.actualiza(ccaa);
        } catch (SQLException ex) {
            sb.append("ERROR: ")
                    .append(ex.getLocalizedMessage())
                    .append(SL)
                    .append("Codigo de error del vendedor: ")
                    .append(ex.getErrorCode());
            LOG.severe(sb.toString());
        }
        return esModificado;
    }

    public DaoCCAAImpl getDao() {
        return dao;
    }

    public void setDao(DaoCCAAImpl dao) {
        this.dao = dao;
    }

    public Properties getJdbc() {
        return jdbc;
    }

    public void setJdbc(Properties jdbc) {
        this.jdbc = jdbc;
    }

    public String getJndi() {
        return jndi;
    }

    public void setJndi(String jndi) {
        this.jndi = jndi;
    }

}
