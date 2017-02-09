package model;


import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Clase que accede a los datos de la tabla Usuario de la base de datos.
 * @author Grupo 502
 *
 */

public class UsuariosMapper extends AbstractMapper<Usuario, String> {
	
	public UsuariosMapper(DataSource ds) {
		super(ds);
	}

	@Override
	protected String getTableName() {
		
		return "Usuario";
	}

	@Override
	protected String[] getColumnNames() {
		
		return new String[] {"Nick", "Pass", "Fecha", "Imagen"};
	}

	@Override
	protected String[] getKeyColumnNames() {
		
		return new String[] {"Nick"};
	}
	
	@Override
	protected Object[] decomposeKey(String id) {
		
		return new Object[]{(String) id};
	}

	@Override
	protected Object[] decomposeKeyByObj(Usuario object) {
		
		return new Object[]{object.getNick()};
	}

	@Override
	protected Usuario buildObject(ResultSet rs) throws SQLException {
		
		
		String nick = rs.getString(getKeyColumnNames()[0]);
		String pass = rs.getString("Pass");
		Date date = rs.getDate("Fecha");
		Blob image = rs.getBlob("Imagen");
		
		byte[] imageBytes = null;
		if (image != null) {
			imageBytes = image.getBytes(1, (int)image.length());
		}
		
		return new Usuario(nick, pass, date, imageBytes);
	}

	@Override
	protected Usuario buildObject(Object[] o) throws SQLException {
		
		return new Usuario((String) o[0],(String)o[1],(Date) o[2],(byte[]) o[3]);
	}

	@Override
	protected Object[] decomposeObject(Usuario o) {
		
		return new Object[]{o.getNick(), o.getPassword(), o.getDate(), o.getImage()};
	}

}