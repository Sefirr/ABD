package es.ucm.abd.practica2.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Contains {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id; // La clave
	
	@Enumerated(EnumType.STRING)
	private Orientation orientation; // Orientacion de la palabra
	
	private Integer x; // Coordenada X
	private Integer y; // Coordenada Y
	@OneToOne
	private Word word; // El identificador de la palabra
	@OneToOne
	private Crossword crossword; // El crucigrama

	protected Contains() {
	}

	/**
	 * Constructor de la clase
	 * 
	 * @param id
	 *            La clave
	 * @param orientation
	 *            Orientacion de la palabra
	 * @param x
	 *            Coordenada X
	 * @param y
	 *            Coordenada Y
	 * @param crosswordId
	 *            El identificador del crucigrama
	 * 
	 * @param wordId
	 *            El idenntificador de la palabra
	 * 
	 */

	public Contains(Crossword crossword, Word word, int row, int column,
			Orientation orientation) {
		this.crossword = crossword;
		this.word = word;
		x = row;
		y = column;
		this.orientation = orientation;
	}

	/**
	 * Metodo get de la clave
	 * 
	 * @return La clave
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Metodo set de la clave
	 * 
	 * @param id
	 *            La clave a insertar
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Metodo get de la orientacion
	 * 
	 * @return La orientacion
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Metodo set de la orientacion
	 * 
	 * @param id
	 *            La orientacion a insertar
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Metodo get de la coordenada X
	 * 
	 * @return La coordenada X
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * Metodo set de la coordenada X
	 * 
	 * @param id
	 *            La coordenada X a insertar
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * Metodo get de la coordenada Y
	 * 
	 * @return La coordenada Y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Metodo set de la coordenada Y
	 * 
	 * @param id
	 *            La coordenada Y a insertar
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Metodo get del identificador del crucigrama
	 * 
	 * @return El identificador del crucigrama
	 */
	public Crossword getCrossword() {
		return crossword;
	}

	/**
	 * Metodo set del identificador del crucigrama
	 * 
	 * @param id
	 *            El identificador del crucigrama a insertar
	 */
	public void setCrossword(Crossword crossword) {
		this.crossword = crossword;
	}

	/**
	 * Metodo get del identificador de la palabra
	 * 
	 * @return El identificador de la palabra
	 */
	public Word getWord() {
		return word;
	}

	/**
	 * Metodo set del identificador de la palabra
	 * 
	 * @param id
	 *            El identificador de la palabra
	 */
	public void setWord(Word wordId) {
		this.word = wordId;
	}

}
