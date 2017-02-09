package model;

/**
 * Metodo que representa la clave de Amigos
 * 
 * @author Grupo 502
 *
 */
public class AmigosId {

	private String amigo1; // El primer usuario
	private String amigo2; // El segundo usuario

	/**
	 * Constructor de la clase
	 * 
	 * @param amigo1
	 *            El primer usuario
	 * @param amigo2
	 *            El segundo usuario
	 */
	public AmigosId(String amigo1, String amigo2) {
		this.amigo1 = amigo1;
		this.amigo2 = amigo2;
	}

	/**
	 * Metodo get de amigo1
	 * 
	 * @return El primer usuario
	 */
	public String getAmigo1() {
		return amigo1;
	}

	/**
	 * Metodo set de amigo1
	 */
	public void setAmigo1(String amigo1) {
		this.amigo1 = amigo1;
	}

	/**
	 * Metodo get de amigo2
	 * 
	 * @return El segundo usuario
	 */
	public String getAmigo2() {
		return amigo2;
	}

	/**
	 * Metodo set de amigo2
	 */
	public void setAmigo2(String amigo2) {
		this.amigo2 = amigo2;
	}

}
