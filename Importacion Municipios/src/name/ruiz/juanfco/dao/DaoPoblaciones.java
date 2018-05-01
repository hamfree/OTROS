package name.ruiz.juanfco.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import name.ruiz.juanfco.excepciones.ConfiguracionException;
import name.ruiz.juanfco.modelo.Poblacion;

/**
 *
 * @author hamfree
 */
interface DaoPoblaciones {

    public void configura(Properties jdbc, String jndi) throws ConfiguracionException;

    public boolean estaConfigurado();

    public Poblacion busca(String idPoblacion) throws SQLException;

    public boolean inserta(Poblacion poblacion) throws SQLException;

    public boolean actualiza(Poblacion poblacion) throws SQLException;

    public boolean borra(String idPoblacion) throws SQLException;

    public List<Poblacion> consulta(String filtro) throws SQLException;
}
