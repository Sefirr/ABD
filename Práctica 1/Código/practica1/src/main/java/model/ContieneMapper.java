package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que accede a los datos de la tabla Contiene de la bases de datos.
 * 
 * @author Grupo 502
 *
 */
public class ContieneMapper extends AbstractMapper<Contiene, ContieneId> {

	public ContieneMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {

		return "Contiene";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "Id_crucigrama", "Id_palabra", "Orientacion",
				"X", "Y", "Puntuacion" };
	}

	@Override
	protected String[] getKeyColumnNames() {

		return new String[] { "Id_crucigrama", "Id_palabra" };
	}

	@Override
	protected Object[] decomposeKey(ContieneId id) {

		return new Object[] { id.getCrosswordId(), id.getWordId() };
	}

	@Override
	protected Object[] decomposeKeyByObj(Contiene object) {

		ContieneId id = object.getId();
		return new Object[] { id.getCrosswordId(), id.getWordId() };
	}

	@Override
	protected Contiene buildObject(ResultSet rs) throws SQLException {

		Integer crosswordId = rs.getInt(getKeyColumnNames()[0]);
		Integer wordId = rs.getInt(getKeyColumnNames()[1]);
		String orientation = rs.getString("Orientacion");
		Integer x = rs.getInt("X");
		Integer y = rs.getInt("Y");
		Integer points = rs.getInt("Puntuacion");

		return new Contiene(new ContieneId(crosswordId, wordId), orientation,
				x, y, points);
	}

	@Override
	protected Contiene buildObject(Object[] o) throws SQLException {

		return new Contiene(new ContieneId((Integer) o[0], (Integer) o[1]),
				(String) o[2], (Integer) o[3], (Integer) o[4], (Integer) o[5]);
	}

	@Override
	protected Object[] decomposeObject(Contiene o) {

		ContieneId id = o.getId();
		return new Object[] { id.getCrosswordId(), id.getWordId(),
				o.getOrientation(), o.getX(), o.getY(), o.getPoints() };
	}

	/**
	 * Devuelve la lista de palabra de un crucigrama junto con su orientación y
	 * los puntos que se pueden conseguir en el crucigrama por esas palabras
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama
	 * @return
	 */
	public List<Contiene> findByCrosswordId(Integer crosswordId) {
		List<Contiene> found = new LinkedList<Contiene>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE Id_crucigrama = ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, crosswordId);

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
