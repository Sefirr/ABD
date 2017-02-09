package model;

/**
 * Esta clase representa el identificador natural de la entidad Historial, es
 * decir, identifica univocamente una entrada del historial del usuario.
 * 
 * @author Grupo 502
 *
 */
public class HistorialId {
	private String nick; // Nick del usuario al que pertenece el historial
	private Integer crosswordId; // Identificador natural de una entrada del
	// historial

	/**
	 * Constructor de dos parámetros de la clase HistorialId.
	 * 
	 * @param nick
	 *            Nick del usuario al que pertenece el historial
	 * @param crosswordId
	 *            Identificador natural de un crucigrama del historial.
	 */
	public HistorialId(String nick, Integer crosswordId) {
		this.nick = nick;
		this.crosswordId = crosswordId;
	}

	/**
	 * Método get del atributo nick de la clase HistorialId.
	 * 
	 * @return Devuelve Nick del usuario al que pertenece el historial
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Método set del atributo nick de la clase HistorialId.
	 * 
	 * @param nick
	 *            Nick del usuario al que pertenece el historial
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Método get del atributo crosswordId de la clase HistorialId.
	 * 
	 * @return Devuelve el identificador natural de un crucigrama del historial.
	 */
	public Integer getCrosswordId() {
		return crosswordId;
	}

	/**
	 * Método set del atributo crosswordId de la clase HistorialId.
	 * 
	 * @param crosswordId
	 *            Identificador natural de un crucigrama del historial.
	 */
	public void setCrosswordId(Integer crosswordId) {
		this.crosswordId = crosswordId;
	}

}