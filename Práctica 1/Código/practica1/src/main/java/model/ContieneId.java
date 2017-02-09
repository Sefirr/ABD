package model;

/**
 * Clase que representa la clave de la tabla Contiene
 * 
 * @author Grupo 502
 *
 */
public class ContieneId {

	private Integer crosswordId; // Identificador del crucigrama
	private Integer wordId; // Identificador de la palabra

	/**
	 * Constructor de la clase
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama
	 * @param wordId
	 *            Identificador de la palabra
	 */
	public ContieneId(Integer crosswordId, Integer wordId) {
		this.crosswordId = crosswordId;
		this.wordId = wordId;
	}

	/**
	 * Metodo get de crosswordId
	 * 
	 * @return Identificador del crucigrama
	 */
	public Integer getCrosswordId() {
		return crosswordId;
	}

	/**
	 * Metodo set de crosswordId
	 * 
	 * @return Identificador del crucigrama
	 */
	public void setCrosswordId(Integer crosswordId) {
		this.crosswordId = crosswordId;
	}

	/**
	 * Metodo get de worddId
	 * 
	 * @return Identificador de la palabra
	 */
	public Integer getWordId() {
		return wordId;
	}

	/**
	 * Metodo set de worddId
	 * 
	 * @return Identificador de la palabra
	 */
	public void setWordId(Integer wordId) {
		this.wordId = wordId;
	}

}
