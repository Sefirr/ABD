package model;

import java.sql.Date;

/**
 * Clase que representa un crucigrama de la aplicación. Tiene trea atributos:
 * Identificador natural del crucigrama, Título del crucigrama y Fecha de
 * creación del crucigrama.
 * 
 * @author JAVIER
 *
 */

public class Crucigrama {

	private Integer crosswordId; // Identificador natural del crucigrama
	private String title; // Título del crucigrama
	private Date date; // Fecha de creación del crucigrama

	/**
	 * Constructor de tres parámetros de la clase Crucigrama
	 * 
	 * @param crosswordId
	 *            Identificador natural del crucigrama
	 * @param title
	 *            Título del crucigrama
	 * @param date
	 *            Fecha de creación del crucigrama
	 */
	public Crucigrama(Integer crosswordId, String title, Date date) {
		this.crosswordId = crosswordId;
		this.title = title;
		this.date = date;
	}

	/**
	 * Método get del atributo crosswordId de la clase Crucigrama.
	 * 
	 * @return Devuelve el identificador natural del crucigrama
	 */
	public Integer getCrosswordId() {
		return crosswordId;
	}

	/**
	 * Método set del atributo crosswordId de la clase Crucigrama.
	 * 
	 * @param crosswordId
	 *            Identificador naturl del crucigrama
	 */
	public void setCrosswordId(Integer crosswordId) {
		this.crosswordId = crosswordId;
	}

	/**
	 * Método get del atributo title de la clase Crucigrama.
	 * 
	 * @return Devuelve el título del crucigrama
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Método set del atributo title de la clase Crucigrama.
	 * 
	 * @param title
	 *            Título del crucigrama
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Método get del atributo date de la clase Crucigrama.
	 * 
	 * @return Devuelve la fecha de creación del crucigrama
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Método set del atributo date de la clase Crucigrama.
	 * 
	 * @return Fecha de creación del crucigrama
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}