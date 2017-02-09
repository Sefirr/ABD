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
 * Clase que accede a los datos de la tabla Amigos de la bases de datos.
 * 
 * @author Grupo 502
 *
 */
public class AmigosMapper extends AbstractMapper<Amigos, AmigosId> {

	public AmigosMapper(DataSource ds) {
		super(ds);

	}

	@Override
	protected String getTableName() {

		return "Amistad";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "Amigo1", "Amigo2" };
	}

	@Override
	protected String[] getKeyColumnNames() {

		return new String[] { "Amigo1", "Amigo2" };
	}

	@Override
	protected Object[] decomposeKey(AmigosId id) {

		return new Object[] { id.getAmigo1(), id.getAmigo2() };
	}

	@Override
	protected Object[] decomposeKeyByObj(Amigos object) {

		AmigosId id = object.getId();

		return new Object[] { id.getAmigo1(), id.getAmigo2() };
	}

	@Override
	protected Amigos buildObject(ResultSet rs) throws SQLException {

		String amigo1 = rs.getString(getKeyColumnNames()[0]);
		String amigo2 = rs.getString(getKeyColumnNames()[1]);
		AmigosId id = new AmigosId(amigo1, amigo2);

		return new Amigos(id);
	}

	@Override
	protected Amigos buildObject(Object[] o) throws SQLException {

		return new Amigos((AmigosId) o[0]);
	}

	@Override
	protected Object[] decomposeObject(Amigos o) {

		AmigosId id = o.getId();

		return new Object[] { id.getAmigo1(), id.getAmigo2() };
	}

	/**
	 * Metodo que busca una relacion de amistad segun un nick
	 * 
	 * @param nick
	 *            El nick a buscar
	 * @return Lista con las relaciones encontradas
	 */
	public List<Object[]> findFriendsByName(String nick) {
		List<Object[]> found = new LinkedList<Object[]>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE amigo1 LIKE ? OR amigo2 LIKE ? ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, nick);
			pst.setObject(2, nick);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					String amigo1 = rs.getString("Amigo1");
					String amigo2 = rs.getString("Amigo2");
					found.add(new Object[] { amigo1, amigo2 });
				}
			}
			return found;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Metodo que comprueba una relacion de amistad
	 * 
	 * @param amigo1
	 *            El primer usuario
	 * @param amigo2
	 *            El segundo usuario
	 * @return Si la relacion se ha encontrado o no
	 */
	public boolean isFriend(String amigo1, String amigo2) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT "
				+ StringUtils.join(columnNames, ", ")
				+ " FROM "
				+ tableName
				+ " WHERE (amigo1 LIKE ? AND amigo2 LIKE ?) OR (amigo1 LIKE ? AND amigo2 LIKE ?) ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, amigo1);
			pst.setObject(2, amigo2);
			pst.setObject(3, amigo2);
			pst.setObject(4, amigo1);
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
	 * Borra una relacion de amistad
	 * 
	 * @param amigo1
	 *            El primer usuario
	 * @param amigo2
	 *            El segundo usuario
	 */
	public void deleteById(String amigo1, String amigo2) {
		String tableName = getTableName();

		String sql = "DELETE FROM "
				+ tableName
				+ " WHERE (amigo1 LIKE ? AND amigo2 LIKE ?) OR (amigo1 LIKE ? AND amigo2 LIKE ?)";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, amigo1);
			pst.setObject(2, amigo2);
			pst.setObject(3, amigo2);
			pst.setObject(4, amigo1);

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}