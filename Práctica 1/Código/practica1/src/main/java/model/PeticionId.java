package model;

/**
 * Identificador natural de la entidad Petición, es decir, identifica
 * univocamente a la petición.
 * 
 * @author Grupo 502
 *
 */

public class PeticionId {

	private String sender; // Persona que envía la petición
	private String receiver; // Persona que recibe la petición
	private Integer crosswordId; // Identificador natural del crucigrama para el
	// que se pide ayuda

	/**
	 * Constructor de tres parámetros de la clase PeticionId
	 * 
	 * @param sender
	 *            Persona que envía la petición
	 * @param receiver
	 *            Persona que recibe la petición
	 * @param crosswordId
	 *            Identificador natural del crucigrama para el que se pide ayuda
	 */
	public PeticionId(String sender, String receiver, Integer crosswordId) {
		this.sender = sender;
		this.receiver = receiver;
		this.crosswordId = crosswordId;
	}

	/**
	 * Método get del atributo sender de la clase PeticionId.
	 * 
	 * @return Devuelve la persona que envía la petición
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Método set del atributo sender de la clase PeticionId.
	 * 
	 * @param Persona
	 *            que envía la petición
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * Método get del atributo receiver de la clase PeticionId.
	 * 
	 * @return Devuelve la persona que recibe la petición
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * Método set del atributo receiver de la clase PeticionId.
	 * 
	 * @param Pwrsona
	 *            que recibe la petición
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * Método get del atributo crosswordId de la clase PeticionId.
	 * 
	 * @return Devuelve el identificador natural del crucigrama para el que se
	 *         pide ayuda
	 */
	public Integer getCrosswordId() {
		return crosswordId;
	}

	/**
	 * Método set del atributo crosswordId de la clase PeticionId.
	 * 
	 * @param Identificador
	 *            natural del crucigrama para el que se pide ayuda
	 */
	public void setCrosswordId(Integer crosswordId) {
		this.crosswordId = crosswordId;
	}

}
