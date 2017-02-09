package model;

/**
 * Clase que representa la Tabla Amigos
 * 
 * @author Grupo 502
 *
 */
public class Amigos {
	private AmigosId id; // La clave

	/**
	 * Constructor de la clase
	 * 
	 * @param id
	 *            La clave del objeto
	 */
	public Amigos(AmigosId id) {
		this.id = id;
	}

	/**
	 * Metodo get de la clave
	 * 
	 * @return La clave
	 */
	public AmigosId getId() {
		return id;
	}

	/**
	 * Metodo set de la clave
	 * 
	 * @param id
	 *            La clave a insertar
	 */
	public void setId(AmigosId id) {
		this.id = id;
	}

}
