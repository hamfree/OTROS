/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.ruiz.juanfco.importacsv.dao;

import java.sql.SQLException;
import java.util.List;
import name.ruiz.juanfco.importacsv.modelo.CCAA;

/**
 *
 * @author hamfree
 */
public interface DaoCCAA {

    public CCAA busca(String idCCAA) throws SQLException;

    public boolean inserta(CCAA ccaa) throws SQLException;

    public boolean actualiza(CCAA ccaa) throws SQLException;

    public boolean borra(String idCCAA) throws SQLException;

    public List<CCAA> consulta(String... filtro) throws SQLException;
}
