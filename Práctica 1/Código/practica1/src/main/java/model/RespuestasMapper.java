package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que accede a los datos de la tabla Respuesas de la bases de datos.
 * 
 * @author Grupo 502
 *
 */
public class RespuestasMapper extends AbstractMapper<Respuesta, RespuestaId> {

	public RespuestasMapper(DataSource ds) {
		super(ds);

	}

	@Override
	protected String getTableName() {
		return "Respuestas";
	}

	@Override
	protected String[] getColumnNames() {
		return new String[] { "Id_palabra", "Id_crucigrama", "Fecha",
				"Propietario_crucigrama", "Usuario_respondido", "Contestacion",
		"Correcto" };
	}

	@Override
	protected String[] getKeyColumnNames() {
		return new String[] { "Id_palabra", "Id_crucigrama", "Fecha",
		"Propietario_crucigrama" };
	}

	@Override
	protected Object[] decomposeKey(RespuestaId id) {
		return new Object[] { id.getWord(), id.getCrossword(), id.getDate(),
				id.getOwner() };

	}

	@Override
	protected Object[] decomposeKeyByObj(Respuesta object) {
		RespuestaId id = object.getId();
		return new Object[] { id.getWord(), id.getCrossword(), id.getDate(),
				id.getOwner() };
	}

	@Override
	protected Object[] decomposeObject(Respuesta o) {
		RespuestaId id = o.getId();
		return new Object[] { id.getWord(), id.getCrossword(), id.getDate(),
				id.getOwner(), o.getSolvingUser(), o.getAnswer(), o.isCorrect() };
	}

	@Override
	protected Respuesta buildObject(ResultSet rs) throws SQLException {
		Integer word = rs.getInt(getKeyColumnNames()[0]);
		;
		Integer cross = rs.getInt(getKeyColumnNames()[1]);
		;
		Date date = rs.getDate(getKeyColumnNames()[2]);
		String owner = rs.getString(getKeyColumnNames()[3]);
		String solvingUser = rs.getString("Usuario_Respondido");
		String answer = rs.getString("Contestacion");
		boolean correct = rs.getBoolean("Correcto");
		return new Respuesta(new RespuestaId(word, cross, date, owner),
				solvingUser, answer, correct);
	}

	@Override
	protected Respuesta buildObject(Object[] o) throws SQLException {

		return new Respuesta(new RespuestaId((Integer) o[0], (Integer) o[1],
				(Date) o[2], (String) o[3]), (String) o[4], (String) o[5],
				(Boolean) o[6]);
	}

	/**
	 * Metodo que busca respuestas por su propietario
	 * 
	 * @param user
	 *            El propietario de la respuesta
	 * @return Una lista con los resultados
	 */
	public List<Respuesta> findByOwner(String user) {
		List<Respuesta> found = new LinkedList<Respuesta>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE Propietario_crucigrama LIKE ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, user);

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
	 * Metodo que busca las respuestas que sobre un determinado crucigrama de un
	 * usuario
	 * 
	 * @param crosswordId
	 *            El identificador del crucigrama
	 * @param id
	 *            El identificador de la palabra
	 * @param user
	 *            El propietario del crucigrama
	 * @return Una lista con los resultados
	 */
	public List<Respuesta> findResolved(Integer crosswordId, Integer id,
			String user) {
		List<Respuesta> found = new LinkedList<Respuesta>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT "
				+ StringUtils.join(columnNames, ", ")
				+ " FROM "
				+ tableName
				+ " WHERE Id_palabra = ? AND Id_crucigrama = ? AND Propietario_crucigrama LIKE ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, id);
			pst.setObject(2, crosswordId);
			pst.setObject(3, user);

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
	 * Metodo que busca respuestas en una peticion de ayuda de un usuario
	 * 
	 * @param user
	 *            El usuario que ha respondido
	 * @return Una lista con los resultados
	 */
	public List<Respuesta> findByHelped(String user) {
		List<Respuesta> found = new LinkedList<Respuesta>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT "
				+ StringUtils.join(columnNames, ", ")
				+ " FROM "
				+ tableName
				+ " WHERE Usuario_respondido LIKE ?  AND Propietario_Crucigrama NOT LIKE ? ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, user);
			pst.setObject(2, user);
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
