package model;

/**
 * Clase que representa la Tabla Contiene
 * 
 * @author Grupo 502
 *
 */
public class Contiene {

	private ContieneId id; // La clave
	private String orientation; // Orientacion de la palabra
	private Integer x; // Coordenada X
	private Integer y; // Coordenada Y
	private Integer points; // Puntos de la palabra

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
	 * @param points
	 *            Puntos de la palabra
	 */
	public Contiene(ContieneId id, String orientation, Integer x, Integer y,
			Integer points) {
		this.id = id;
		this.orientation = orientation;
		this.x = x;
		this.y = y;
		this.points = points;
	}

	/**
	 * Metodo get de la clave
	 * 
	 * @return La clave
	 */
	public ContieneId getId() {
		return id;
	}

	/**
	 * Metodo set de la clave
	 * 
	 * @param id
	 *            La clave a insertar
	 */
	public void setId(ContieneId id) {
		this.id = id;
	}

	/**
	 * Metodo get de la orientacion
	 * 
	 * @return La orientacion
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * Metodo set de la orientacion
	 * 
	 * @param id
	 *            La orientacion a insertar
	 */
	public void setOrientation(String orientation) {
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
	 * Metodo get de los puntos
	 * 
	 * @return Los puntos de la palabra
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * Metodo set de los puntos
	 * 
	 * @param id
	 *            Los puntos a insertar
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}

}
