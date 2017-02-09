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
 * Clase que accede a los datos de la tabla Historial de la base de datos.
 * 
 * @author Grupo 502
 *
 */
public class HistorialMapper extends AbstractMapper<Historial, HistorialId> {

	public HistorialMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {

		return "Historial";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "Nick", "Id_crucigrama", "Finalizado" };
	}

	@Override
	protected String[] getKeyColumnNames() {

		return new String[] { "Nick", "Id_crucigrama" };
	}

	@Override
	protected Object[] decomposeKey(HistorialId id) {

		return new Object[] { id.getNick(), id.getCrosswordId() };
	}

	@Override
	protected Object[] decomposeKeyByObj(Historial object) {

		HistorialId id = object.getId();
		return new Object[] { id.getNick(), id.getCrosswordId() };
	}

	@Override
	protected Historial buildObject(ResultSet rs) throws SQLException {
		String nick = rs.getString("Nick");
		Integer crosswordId = rs.getInt("Id_crucigrama");
		boolean finished = rs.getBoolean("Finalizado");

		return new Historial(new HistorialId(nick, crosswordId), finished);
	}

	@Override
	protected Historial buildObject(Object[] o) throws SQLException {

		return new Historial((HistorialId) o[0], (boolean) o[1]);
	}

	@Override
	protected Object[] decomposeObject(Historial o) {

		HistorialId id = o.getId();
		return new Object[] { id.getNick(), id.getCrosswordId(), o.isFinished() };
	}

	/**
	 * Metodo que busca los crucigramasdel historial de un usuario
	 * 
	 * @param nick
	 *            El usuario que se busca
	 * @return Una lista con los resultados
	 */
	public List<Historial> findByNick(String nick) {
		List<Historial> found = new LinkedList<Historial>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE Nick LIKE ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, nick);

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

	/**
	 * Pone un crucigrama como resuelto en la base de datos
	 * 
	 * @param id
	 *            La clave de el historial correspondiente
	 */
	public void updateResolved(HistorialId id) {

		String sql = "UPDATE historial SET finalizado = TRUE WHERE Nick LIKE ? AND Id_crucigrama = ?";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, id.getNick());
			pst.setObject(2, id.getCrosswordId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}