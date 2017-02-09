package model;

import java.util.Date;

/**
 * Clase que representa la clave de la tabla Respuesta
 * 
 * @author Grupo 502
 *
 */
public class RespuestaId {

	private int word; // Identificador de la palabra
	private int crossword; // Identificador del crucigrama
	private Date date; // Fecha en que se envio la respuesta
	private String owner; // Propietario del crucigrama

	/**
	 * Constructor de la clase
	 * 
	 * @param word
	 *            Identificador de la palabra
	 * @param crossword
	 *            Identificador del crucigrama
	 * @param date
	 *            Fecha en que se envio la respuesta
	 * @param owner
	 *            Propietario del crucigrama
	 */
	public RespuestaId(int word, int crossword, Date date, String owner) {
		super();
		this.word = word;
		this.crossword = crossword;
		this.date = date;
		this.owner = owner;
	}

	/**
	 * Metodo get del atributo word
	 * 
	 * @return El identificador la palabra
	 */
	public int getWord() {
		return word;
	}

	/**
	 * Metodo set del atributo word
	 * 
	 * @param word
	 *            El identificador a insertar
	 */
	public void setWord(int word) {
		this.word = word;
	}

	/**
	 * Metodo get del atributo crossword
	 * 
	 * @return El identificador del crucigrama
	 */
	public int getCrossword() {
		return crossword;
	}

	/**
	 * Metodo set del atributo crossword
	 * 
	 * @param crossword
	 *            El identificador del crucigrama
	 */
	public void setCrossword(int crossword) {
		this.crossword = crossword;
	}

	/**
	 * Metodo get del atributo date
	 * 
	 * @return La fecha de la respuesta
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Metodo set del atributo date
	 * 
	 * @param date
	 *            La fecha a insertar
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Metodo get del atributo owner
	 * 
	 * @return El propietario del crucigrama
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Metodo set del atributo owner
	 * 
	 * @param owner
	 *            El propietario a insertar
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

}
