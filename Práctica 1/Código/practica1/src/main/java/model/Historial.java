package model;

/**
 * Esta clase representa el historial del usuario, es decir, la lista de
 * crucigramas activos del usuario. Tiene dos atributos: El identificador
 * natural del historial y un booleano que indiva si se ha acabado o no un
 * crucigrama del historial.
 * 
 * @author Grupo 502
 *
 */

public class Historial {
	private HistorialId id; // Identificador natural del historial
	private boolean finished; // Booleano que indica si se ha acabado un
	// crucigrama del historial del usuario

	/**
	 * Constructor de dos parámetros de la clase Historial.
	 * 
	 * @param id
	 *            Identificador natural de un crucigrama del historial
	 * @param finished
	 *            Booleano que indica si se ha acabado un crucigrama del
	 *            historial del usuario
	 */
	public Historial(HistorialId id, boolean finished) {
		this.id = id;
		this.finished = finished;
	}

	/**
	 * Método get del atributo id de la clase Historial.
	 * 
	 * @return Devuelve el identificador natural de un crucigrama del historial
	 */
	public HistorialId getId() {
		return id;
	}

	/**
	 * Métood set del atributo id de la clase Historial.
	 * 
	 * @param id
	 *            Identificador natural de un crucigrama presente en el
	 *            historial del usuario
	 */
	public void setId(HistorialId id) {
		this.id = id;
	}

	/**
	 * Método get del atributo finished de la clase Historial.
	 * 
	 * @return Devuelve si se ha acabado el crucigrama del historial
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Método set del atributo finished de la clase Historial.
	 * 
	 * @param finished
	 *            Booleano que indica si se ha acabado un crucigrama del
	 *            historial
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}