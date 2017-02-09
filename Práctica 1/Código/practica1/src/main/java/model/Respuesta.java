package model;

/**
 * Clase que representa la Tabla Respuesta
 * 
 * @author Grupo 502
 *
 */
public class Respuesta {

	private RespuestaId id; // Clave de la respuesta
	private String solvingUser; // Usuario que responde
	private String answer; // La respuesta
	private boolean correct; // Indica si la respuesta es correcta

	/**
	 * Constructor de la clase
	 * 
	 * @param id
	 *            Clave de la respuesta
	 * @param solvingUser
	 *            Usuario que responde
	 * @param answer
	 *            La respuesta
	 * @param correct
	 *            Indica si la respuesta es correcta
	 */
	public Respuesta(RespuestaId id, String solvingUser, String answer,
			boolean correct) {
		super();
		this.id = id;
		this.solvingUser = solvingUser;
		this.answer = answer;
		this.correct = correct;
	}

	/**
	 * Metodo get de la clave
	 * 
	 * @return La clave de la respuesta
	 */
	public RespuestaId getId() {
		return id;
	}

	/**
	 * Metodo set de la clave
	 * 
	 * @param id
	 *            La clave a insertar
	 */
	public void setId(RespuestaId id) {
		this.id = id;
	}

	/**
	 * Metodo get del atributo solvingUser
	 * 
	 * @return El usuario que ha respondido
	 */
	public String getSolvingUser() {
		return solvingUser;
	}

	/**
	 * Metodo set del atributo solvingUser
	 * 
	 * @param solvingUser
	 *            El usuario a insertar
	 */
	public void setSolvingUser(String solvingUser) {
		this.solvingUser = solvingUser;
	}

	/**
	 * Metodo get del atributo answer
	 * 
	 * @return La respuesta introducida
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * Metodo set del atributo answer
	 * 
	 * @param answer
	 *            La respuesta a insertar
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @return Si la respuesta es correcta o no
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * Indica si la respuesta es correcta o no
	 * 
	 * @param correct
	 *            Booleano que indica si es correcta o no
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
