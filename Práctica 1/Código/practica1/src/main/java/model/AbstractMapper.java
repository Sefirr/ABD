package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;

/**
 * Clase que representa el Mapper Abstracto
 * 
 * @author Grupo 502
 *
 * @param <T>
 *            La clase concreta
 * @param <K>
 *            La clave de la clase
 */
public abstract class AbstractMapper<T, K> {
	protected DataSource ds; // Datasource del mapper

	protected abstract String getTableName(); // Devuelve el nombre de la tabla

	protected abstract String[] getColumnNames(); // Devuelve el nombre de las
	// columnas

	protected abstract String[] getKeyColumnNames(); // Devuelve el nombre de
	// las columnas que forman parte de la clave

	protected abstract Object[] decomposeKey(K id); // Descompone una clave

	protected abstract Object[] decomposeKeyByObj(T object); // Descompone una
	// clave a partir del objeto

	protected abstract T buildObject(ResultSet rs) throws SQLException; // Construye
	// un objeto a partir de los resultados de la consulta

	protected abstract T buildObject(Object[] o) throws SQLException; // Construye
	// un objeto a partir de un array con los atributos

	protected abstract Object[] decomposeObject(T o); // Descompone un objeto en
	// sus atributos

	/**
	 * Constructor de la clase
	 * 
	 * @param ds
	 *            El datasource a utilizar
	 */
	public AbstractMapper(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Inserta un objeto en la base de datos
	 * 
	 * @param obj
	 *            EL objeto a insertar
	 * @return Si se realizo la insercion con exito
	 */
	public boolean insert(T obj) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();

		String fieldList = StringUtils.join(columnNames, ",");
		String[] marks = new String[columnNames.length];
		Arrays.fill(marks, "?");
		String markList = StringUtils.join(marks, ",");
		String sql = "INSERT INTO " + tableName + " (" + fieldList
				+ ") VALUES (" + markList + ")";
		Object[] values = decomposeObject(obj);
		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			for (int i = 0; i < columnNames.length; i++) {
				pst.setObject(i + 1, values[i]);
			}

			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Actualiza un objeto en la base de datos
	 * 
	 * @param obj
	 *            El objeto a actualizar
	 */
	public void update(T obj) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String[] keyColumnNames = getKeyColumnNames();

		String fieldList, keyList;
		String[] field = new String[columnNames.length];
		String[] condition = new String[keyColumnNames.length];

		for (int i = 0; i < columnNames.length; i++)
			field[i] = columnNames[i] + " = ?";
		fieldList = StringUtils.join(field, ",");

		for (int i = 0; i < keyColumnNames.length; i++)
			condition[i] = keyColumnNames[i] + " = ?";
		keyList = StringUtils.join(condition, ",");

		String sql = "UPDATE " + tableName + " SET " + fieldList + " WHERE "
				+ keyList;

		Object[] newValues = decomposeObject(obj);
		Object[] keyValues = decomposeKeyByObj(obj);

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {
			for (int i = 0; i < newValues.length; i++) {
				pst.setObject(i + 1, newValues[i]);
			}

			for (int i = 0; i < keyValues.length; i++) {
				pst.setObject(i + newValues.length + 1, keyValues[i]);
			}

			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Borra un objeto de la base de datos
	 * 
	 * @param obj
	 *            EL objeto a eliminar
	 * @return Si se realizo la operacion con exito
	 */
	public boolean delete(T obj) {
		String tableName = getTableName();
		String[] keyColumnNames = getKeyColumnNames();

		String keyList;
		String[] condition = new String[keyColumnNames.length];

		for (int i = 0; i < keyColumnNames.length; i++) {
			condition[i] = keyColumnNames[i] + " = ?";
		}
		keyList = StringUtils.join(condition, " AND ");

		String sql = "DELETE FROM " + tableName + " WHERE " + keyList;

		Object[] keyValues = decomposeKeyByObj(obj);

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

			for (int i = 0; i < keyValues.length; i++) {
				pst.setObject(i + 1, keyValues[i]);
			}

			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Busca un objeto a partir de una clave
	 * 
	 * @param id
	 *            La clave del objeto buscado
	 * @return El objeto buscado
	 */
	public T findById(K id) {
		String tableName = getTableName();
		String[] columnNames = getColumnNames();
		String[] keyColumnNames = getKeyColumnNames();

		String sql = "SELECT " + StringUtils.join(columnNames, ", ") + " FROM "
				+ tableName + " WHERE ";

		String[] conds = new String[keyColumnNames.length];
		for (int i = 0; i < keyColumnNames.length; i++)
			conds[i] = keyColumnNames[i] + " = ?";

		String conditionList = StringUtils.join(conds, " AND ");
		sql += conditionList;

		try (Connection con = ds.getConnection();
				PreparedStatement pst = con.prepareStatement(sql)) {

			for (int i = 0; i < keyColumnNames.length; i++)
				pst.setObject(i + 1, decomposeKey(id)[i]);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return buildObject(rs);
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			return null;
		}
	}

}