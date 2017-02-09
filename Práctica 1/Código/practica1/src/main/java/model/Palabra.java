package model;

/**
 * Clase que representa la Tabla Palabra
 * 
 * @author Grupo 502
 *
 */
public class Palabra {

	private Integer wordId; // Clave de la palabra
	private String title; // La palabra
	private byte[] image; // Imagen asociada
	private String wording; // Pista

	/**
	 * Constructor de la clase
	 * 
	 * @param wordId
	 *            Clave de la palabra
	 * @param title
	 *            La palabra
	 * @param image
	 *            Imagen asociada
	 * @param wording
	 *            Pista
	 */
	public Palabra(Integer wordId, String title, byte[] image, String wording) {
		this.wordId = wordId;
		this.title = title;
		this.image = image;
		this.wording = wording;
	}

	/**
	 * Metodo get de la clave
	 * 
	 * @return La clave de la palabra
	 */
	public Integer getWordId() {
		return wordId;
	}

	/**
	 * Metodo set de la clave
	 * 
	 * @param wordId
	 *            La clave a insertar
	 */
	public void setWordId(Integer wordId) {
		this.wordId = wordId;
	}

	/**
	 * Metodo get de la palabra
	 * 
	 * @return La palabra
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Metodo set de la palabra
	 * 
	 * @param wordId
	 *            La palabra a insertar
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Metodo get de la imagen
	 * 
	 * @return La imagen de la palabra
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Metodo set de la imagen
	 * 
	 * @param wordId
	 *            La imagen a insertar
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * Metodo get de la pista
	 * 
	 * @return La pista de la palabra
	 */
	public String getWording() {
		return wording;
	}

	/**
	 * Metodo set de la pista
	 * 
	 * @param wordId
	 *            La pista a insertar
	 */
	public void setWording(String wording) {
		this.wording = wording;
	}

}
