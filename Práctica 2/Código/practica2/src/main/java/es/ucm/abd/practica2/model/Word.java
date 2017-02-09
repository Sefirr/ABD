package es.ucm.abd.practica2.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Word {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer wordId; // Clave de la palabra
	@Column(length = 20)
	private String title; // La palabra
	@Lob
	private byte[] image; // Imagen asociada
	@Column(length = 50)
	private String wording; // Pista
	@ElementCollection
	private List<String> tags; // Lista de las etiquetas

	protected Word() {
	}

	public Word(String sequence, String hint, String[] tags) {
		title = sequence;
		wording = hint;
		this.tags = new LinkedList<String>();
		for (String s : tags)
			this.tags.add(s);
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

	/**
	 * Metodo get de las etiquetas
	 * 
	 * @return Las etiquetas de la palabra
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Metodo set de las etiquetas
	 * 
	 * @param wordId
	 *            Las etiquetas a insertar
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * Añade una etiqueta a la palabra
	 * 
	 * @param tag
	 *            La etiqueta añadida
	 */
	public void addTag(String tag) {
		if (!tags.contains(tag))
			tags.add(tag);
	}

	/**
	 * Elimina una etiqueta de la palabra
	 * 
	 * @param tag
	 *            La etiqueta a eliminar
	 * @return Si la operación se realizó con éxito o no
	 */
	public boolean removeTag(String tag) {
		Boolean ret = tags.remove(tag);
		return ret;
	}

}
