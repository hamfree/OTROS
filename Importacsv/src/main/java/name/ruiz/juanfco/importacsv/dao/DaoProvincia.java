package name.ruiz.juanfco.importacsv.dao;

import java.sql.SQLException;
import java.util.List;
import name.ruiz.juanfco.importacsv.modelo.Provincia;

/**
 *
 * @author hamfree
 */
public interface DaoProvincia {

    public Provincia busca(String idProvincia) throws SQLException;

    public boolean inserta(Provincia provincia) throws SQLException;

    public boolean actualiza(Provincia provincia) throws SQLException;

    public boolean borra(String idProvincia) throws SQLException;

    public List<Provincia> consulta(String filtro) throws SQLException;
}
