package model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que accede a los datos de la tabla Palabras de la bases de datos.
 * 
 * @author Grupo 502
 *
 */
public class PalabrasMapper extends AbstractMapper<Palabra, Integer> {

	public PalabrasMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {

		return "Palabra";
	}

	@Override
	protected String[] getColumnNames() {

		return new String[] { "Id_palabra", "Enunciado", "Imagen", "Letras" };
	}

	@Override
	protected String[] getKeyColumnNames() {

		return new String[] { "Id_palabra" };
	}

	@Override
	protected Object[] decomposeKey(Integer id) {

		return new Object[] { id };
	}

	@Override
	protected Object[] decomposeKeyByObj(Palabra object) {

		return new Object[] { object.getWordId() };
	}

	@Override
	protected Palabra buildObject(ResultSet rs) throws SQLException {

		Integer id_palabra = rs.getInt("Id_palabra");
		String title = rs.getString("letras");
		Blob image = rs.getBlob("Imagen");

		byte[] imageBytes = null;
		if (image != null) {
			imageBytes = image.getBytes(1, (int) image.length());
		}

		String wording = rs.getString("enunciado");

		return new Palabra(id_palabra, title, imageBytes, wording);
	}

	/**
	 * Metodo que busca una palabra
	 * 
	 * @param Title
	 *            La palabra que se busca
	 * @return Una lista con los resultados
	 */
	public List<Palabra> findByName(String Title) {
		List<Palabra> found = new LinkedList<Palabra>();
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE Letras LIKE ?  ";

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setObject(1, Title);

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

	@Override
	protected Palabra buildObject(Object[] o) throws SQLException {

		return new Palabra((Integer) o[0], (String) o[1], (byte[]) o[2],
				(String) o[3]);
	}

	@Override
	protected Object[] decomposeObject(Palabra o) {

		return new Object[] { o.getWordId(), o.getTitle(), o.getImage(),
				o.getWording() };
	}

}
