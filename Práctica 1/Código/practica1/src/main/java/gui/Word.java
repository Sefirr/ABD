package gui;

import es.ucm.abd.crossword.WordModel;

/**
 * Clase que representa las palabras de los crucigramas
 * 
 * @author Grupo 502
 *
 */
class Word implements WordModel {
	private int x; // Coordenada X
	private int y; // Coordenada Y
	private String word; // La palabra
	private boolean horizontal; // Indica si esta en horizontal o vertical

	/**
	 * Constructor de la clase
	 * 
	 * @param x
	 *            Coordenada X
	 * @param y
	 *            Coordenada Y
	 * @param word
	 *            La palabra
	 * @param horizontal
	 *            Indica si esta en horizontal o vertical
	 */
	public Word(int x, int y, String word, boolean horizontal) {
		this.x = x;
		this.y = y;
		this.word = word;
		this.horizontal = horizontal;
	}

	/**
	 * Metodo get del atributo x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Metodo get del atributo y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Metodo get del atributo word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * Indica si la palabra esta en horizontal o vertical
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Metodo que genera el hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (horizontal ? 1231 : 1237);
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Metodo equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (horizontal != other.horizontal)
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
