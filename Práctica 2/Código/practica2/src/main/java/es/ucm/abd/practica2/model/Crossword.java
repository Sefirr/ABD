package es.ucm.abd.practica2.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Crossword {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer crosswordId; // Identificador natural del crucigrama
	@Column(length = 50)
	private String title; // TÃ­tulo del crucigrama
	@Column
	private Date date; // Fecha de creaciÃ³n del crucigrama
	@OneToMany(mappedBy = "crossword", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Contains> contains; // La relacion contiene, con la posicion de
	// las palabras

	public Crossword() {
	}

	/**
	 * Constructor de tres parÃ¡metros de la clase Crossword
	 * 
	 * @param crosswordId
	 *            Identificador natural del crucigrama
	 * @param title
	 *            TÃ­tulo del crucigrama
	 * @param date
	 *            Fecha de creaciÃ³n del crucigrama
	 */

	public Crossword(String title, Date date) {
		this.title = title;
		this.date = date;
		this.contains = new LinkedList<Contains>();
	}

	/**
	 * MÃ©todo get del atributo crosswordId de la clase Crossword
	 * 
	 * @return Devuelve el identificador natural del crucigrama
	 */
	public Integer getCrosswordId() {
		return crosswordId;
	}

	/**
	 * MÃ©todo set del atributo crosswordId de la clase Crossword
	 * 
	 * @param crosswordId
	 *            Identificador naturl del crucigrama
	 */
	public void setCrosswordId(Integer crosswordId) {
		this.crosswordId = crosswordId;
	}

	/**
	 * MÃ©todo get del atributo title de la clase Crossword
	 * 
	 * @return Devuelve el tÃ­tulo del crucigrama
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * MÃ©todo set del atributo title de la clase Crossword
	 * 
	 * @param title
	 *            TÃ­tulo del crucigrama
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * MÃ©todo get del atributo date de la clase Crossword
	 * 
	 * @return Devuelve la fecha de creaciÃ³n del crucigrama
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * MÃ©todo set del atributo date de la clase Crossword
	 * 
	 * @return Fecha de creaciÃ³n del crucigrama
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public List<Contains> getContains() {
		return contains;
	}

}
