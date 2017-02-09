package model;

/**
 * Esta clase representa las peticiones. Una petición es una solicitud de ayuda
 * a un amigo. Después de pedir la petición, no podrás responder ninguna palabra
 * del crucigrama para el que habías pedido la petición hasta que tu amigo la
 * descarte. Tiene un atributo, id, el cúal es una instancia de la clase
 * PeticionId que es la clave primaria de las peticiones en la base de datos.
 * 
 * @author Grupo 502
 *
 */

public class Peticion {

	private PeticionId id; // Identificador natural de la petición

	/**
	 * Constructor de un parámetro de la clase Peticion.
	 * 
	 * @param id
	 *            Identificador natural de la petición.
	 */
	public Peticion(PeticionId id) {
		this.id = id;
	}

	/**
	 * Método get del atributo id de la clase Peticion.
	 * 
	 * @return Devuelve el identificador natural de la clase Peticion. En este
	 *         caso, es una clave compuesta formada por la persona que envia la
	 *         petición, la persona que la recibe y el crucigrama para el que se
	 *         pide ayuda.
	 */
	public PeticionId getId() {
		return id;
	}

	/**
	 * Método set del atributo id de la clase Peticion.
	 * 
	 * @param id
	 *            Identificador natural de la clase Peticion.
	 * @return Devuelve el identificador natural de la clase Peticion. En este
	 *         caso, es una clave compuesta formada por la persona que envia la
	 *         petición, la persona que la recibe y el crucigrama para el que se
	 *         pide ayuda.
	 */
	public void setId(PeticionId id) {
		this.id = id;
	}

}