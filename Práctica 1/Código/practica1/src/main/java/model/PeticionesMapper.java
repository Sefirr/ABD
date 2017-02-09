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
 * Clase que accede a los datos de la tabla Ayudas de la base de datos.
 * 
 * @author Grupo 502
 *
 */

public class PeticionesMapper extends AbstractMapper<Peticion, PeticionId> {

	public PeticionesMapper(DataSource ds) {
		super(ds);

	}

	@Override
	protected String getTableName() {

		return "Ayudas";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "NickPide", "NickAyuda", "Id_crucigrama" };
	}

	@Override
	protected String[] getKeyColumnNames() {

		return new String[] { "NickPide", "NickAyuda", "Id_crucigrama" };
	}

	@Override
	protected Object[] decomposeKey(PeticionId id) {

		return new Object[] { id.getSender(), id.getReceiver(),
				id.getCrosswordId() };
	}

	@Override
	protected Object[] decomposeKeyByObj(Peticion object) {

		PeticionId id = object.getId();

		return new Object[] { id.getSender(), id.getReceiver(),
				id.getCrosswordId() };
	}

	@Override
	protected Peticion buildObject(ResultSet rs) throws SQLException {

		String sender = rs.getString(getKeyColumnNames()[0]);
		String receiver = rs.getString(getKeyColumnNames()[1]);
		Integer crosswordId = rs.getInt(getKeyColumnNames()[2]);

		PeticionId id = new PeticionId(sender, receiver, crosswordId);
		return new Peticion(id);
	}

	@Override
	protected Peticion buildObject(Object[] o) throws SQLException {

		PeticionId id = new PeticionId((String) o[0], (String) o[1],
				(Integer) o[2]);
		return new Peticion(id);
	}

	@Override
	protected Object[] decomposeObject(Peticion o) {

		PeticionId id = o.getId();
		return new Object[] { id.getSender(), id.getReceiver(),
				id.getCrosswordId() };
	}

	/**
	 * Devuelve la lista de peticiones en las que el usuario pasado como
	 * parámetro ayuda a otro.
	 * 
	 * @param nick
	 *            Nick del usuario al que se ayuda
	 * @return
	 */
	public List<Object[]> findRequestByName(String nick) {
		List<Object[]> found = new LinkedList<Object[]>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE nickAyuda LIKE ?";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, nick);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					String sender = rs.getString("nickPide");
					Integer crosswordId = rs.getInt("id_crucigrama");
					found.add(new Object[] { sender, crosswordId });
				}
			}
			return found;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Determina si este usuario ha mandado una petición en el crucigrama
	 * correspondiente pasado como parámetro.
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @param crosswordId
	 *            Identificador del crucigrama
	 * @return
	 */
	public boolean isRequest(String nick, int crosswordId) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE nickPide LIKE ? AND id_crucigrama = ?";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, nick);
			pst.setObject(2, crosswordId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return true;
				} else {
					return false;
				}
			}

		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Determina si el usuario pasado como parámetro es ayudado por otro.
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @return
	 */
	public List<Peticion> findRequestByHelped(String nick) {
		List<Peticion> found = new LinkedList<Peticion>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE nickPide LIKE ?";

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

}
