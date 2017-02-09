package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que accede a los datos de la tabla Crucigrama de la base de datos.
 * 
 * @author Grupo 502
 *
 */

public class CrucigramasMapper extends AbstractMapper<Crucigrama, Integer> {

	public CrucigramasMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		return "Crucigrama";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "Id_crucigrama", "Titulo", "Fecha" };
	}

	@Override
	protected String[] getKeyColumnNames() {
		return new String[] { "Id_crucigrama" };
	}

	@Override
	protected Object[] decomposeKey(Integer id) {
		return new Object[] { id };
	}

	@Override
	protected Object[] decomposeKeyByObj(Crucigrama object) {
		return new Object[] { object.getCrosswordId() };
	}

	@Override
	protected Crucigrama buildObject(ResultSet rs) throws SQLException {
		Integer crosswordId = rs.getInt(getKeyColumnNames()[0]);
		String title = rs.getString("Titulo");
		Date date = rs.getDate("Fecha");

		return new Crucigrama(crosswordId, title, date);
	}

	@Override
	protected Crucigrama buildObject(Object[] o) throws SQLException {
		return new Crucigrama((Integer) o[0], (String) o[1], (Date) o[2]);
	}

	@Override
	protected Object[] decomposeObject(Crucigrama o) {
		return new Object[] { o.getCrosswordId(), o.getTitle(), o.getDate() };
	}

	/**
	 * Devuelve la lista de crucigramas cuyo titulo contiene la secuencia de
	 * caracteres indicada por el parámetro Title
	 * 
	 * @param Title
	 *            Cadena a buscar
	 * @return
	 */
	public List<Crucigrama> findByTitle(String Title) {
		List<Crucigrama> found = new LinkedList<Crucigrama>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE Titulo LIKE ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, "%" + Title + "%");

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					found.add(buildObject(rs));
				}
			}
			return found;
		} catch (SQLException e) {
			return null;
		}
	}

}
