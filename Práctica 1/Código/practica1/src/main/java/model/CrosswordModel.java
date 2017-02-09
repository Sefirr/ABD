package model;

import java.sql.Date;
import java.util.List;
import java.util.Observable;

/**
 * Clase que representa el modelo de la aplicación. Se trata de la fachada de
 * operaciones de la aplicación.
 * 
 * @author Grupo 502
 *
 */
public class CrosswordModel extends Observable {

	private CrosswordDAO dao; // DAO de crucigramas
	private String user; // Usuario que está manejando la aplicación
	private String helped; // Usuario al que está ayudando el usuario que maneja
	// la aplicación
	private Integer crosswordId; // Identificador del crucigrama que está
	// resolviendo el usuario
	private Integer points; // Puntos del usuario que está manejando la
	// aplicación.

	/**
	 * Constructor sin parámetros de la clase CrosswordModel.
	 */
	public CrosswordModel() {
		points = 0;
		try {
			this.dao = new CrosswordDAO();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método get del atributo user de la clase CrosswordModel.
	 * 
	 * @return Devuelve el nick del usuario que está manejando la aplicación
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * Método set del atributo user de la clase CrosswordModel.
	 * 
	 * @param user
	 *            Usuario que maneja la aplicación
	 */
	public void setUser(String user) {
		this.user = user;
		points = dao.pointCalculation(user);
	}

	/**
	 * Método get del atributo crosswordId de la clase CrosswordModel.
	 * 
	 * @return Devuelve el identificador del crucigrama que está resolviendo el
	 *         usuario
	 */
	public Integer getCrossword() {
		return this.crosswordId;
	}

	/**
	 * Método set del atributo crosswordId de la clase CrosswordModel.
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama que está resolviendo el usuario
	 */
	public void setCrossword(Integer crosswordId) {

		if (crosswordId != null) {
			this.crosswordId = crosswordId;
			crosswordNotify("OPEN_CROSSWORD");
		} else {
			crosswordNotify("OPEN_CROSSWORD_FAILURE");
		}
	}

	/**
	 * Devuelve la lista de palabra del crucigrama que está resolviendo el
	 * usuario.
	 * 
	 * @return La lista de palabra del crucigrama que está resolviendo el
	 *         usuario.
	 */
	public List<Object[]> getWordsOfCrossword() {
		return dao.getWordsOfCrossword(crosswordId);
	}

	/**
	 * Añadir un usuario a la lista de usuarios de la BD.
	 * 
	 * @param user
	 *            Nick del usuario creado
	 * @param pass
	 *            Contraseña del usuario creado
	 * @return devuelve si se añadio con exito el usuario
	 */
	public boolean addUser(String user, String pass) {
		if (!dao.addUser(user, pass)) {
			crosswordNotify("EXISTING_USER");
			return false;
		} else {
			crosswordNotify("USER_CREATED");
			return true;
		}

	}

	/**
	 * Método de login del usuario.
	 * 
	 * @param user
	 *            Nick del usuario
	 * @param pass
	 *            Contraeña del usuario
	 * @return Devuelve true si el usuario se logueo correctamente.
	 */
	public boolean login(String user, String pass) {

		if (dao.findUser(user) == null) {
			crosswordNotify("LOGIN_FAILURE");
			return false;
		}

		if (getPassword(user).compareTo(pass) == 0) {
			setUser(user);
			crosswordNotify("LOGIN");
			return true;
		} else {
			crosswordNotify("LOGIN_FAILURE");
			return false;
		}

	}

	/**
	 * Devuelve la contraseña del usuario pasado como parámetro.
	 * 
	 * @param user
	 *            Nick del usuario
	 * @return Devuelve la contraseña del usuario pasado como parámetro.
	 */
	public String getPassword(String user) {

		return dao.getPassword(user);
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario que está manejando la
	 * aplicación.
	 * 
	 * @return Devuelve la fecha de nacimiento del usuario que está manejando la
	 *         aplicación
	 */
	public Date getDate() {

		return dao.getDate(user);
	}

	/**
	 * Devuelve la imagen de perfil del usuario que está manejando la
	 * aplicación.
	 * 
	 * @return Devuelve la imagen de perfil del usuario que está manejando la
	 *         aplicación
	 */
	public byte[] getImage() {
		return dao.getImage(user);
	}

	/**
	 * Modifica la contraseña del usuario que está manejando la aplicación.
	 * 
	 * @param newPassword
	 *            Nueva contraseña
	 */
	public void modifyPassword(String newPassword) {
		if (newPassword != null) {
			dao.modifyPassword(user, newPassword);
			crosswordNotify("UPDATE_USER");
		}
	}

	/**
	 * Modifica la fecha de nacimiento del usuario que está manejando la
	 * aplicación.
	 * 
	 * @param newDate
	 *            Nueva fecha de nacimiento
	 */
	public void modifyDate(Date newDate) {
		dao.modifyDate(user, newDate);
		crosswordNotify("UPDATE_USER");
	}

	/**
	 * Modifica la imagen de perfil del usuario que está manejando la aplicación
	 * 
	 * @param newImage
	 *            Nueva imagen
	 */
	public void modifyImage(byte[] newImage) {
		if (newImage != null) {
			dao.modifyImage(user, newImage);
			crosswordNotify("UPDATE_USER");
		}
	}

	/**
	 * Devuelve la lista de crucigramas que contiene en su título el parámetro
	 * str.
	 * 
	 * @param str
	 *            Cadena a buscar
	 * @return La lista de crucigramas que contiene en su título el parámetro
	 *         str.
	 */
	public List<Object> findCrosswordsByTitle(String str) {

		return dao.findCrosswordsByTitle(str);
	}

	/**
	 * Devuelve el título del crucigrama pasado como parámetro.
	 * 
	 * @param id
	 *            Identificador del crucigrama
	 * @return El título del crucigrama pasado como parámetro.
	 */
	public String getCrosswordTitle(Object id) {

		return dao.getCrosswordTitle(id);
	}

	/**
	 * Devuelve la fecha de creación del crucigrama pasado como parámetro
	 * 
	 * @param id
	 *            Identificador del crucigrama
	 * @return La fecha de creación del crucigrama pasado como parámetro
	 */
	public Date getCrosswordDate(Object id) {
		return dao.getCrosswordDate(id);
	}

	/**
	 * Añade un crucigrama al historial del usuario que está manejando la
	 * aplicación.
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama a añadir.
	 */
	public void addCrosswordToUser(Object crosswordId) {
		if (crosswordId == null) {
			crosswordNotify("HISTORIAL_ADD_FAILURE2");
		} else {
			if (dao.addCrosswordToUser(user, crosswordId))
				crosswordNotify("HISTORIAL_ADD");
			else
				crosswordNotify("HISTORIAL_ADD_FAILURE");
		}

	}

	/**
	 * Devuelve la lista de identificadores de los crucigramas activos del
	 * usuario que está manejando la aplicación.
	 */
	public List<Object> getCrosswordsOf() {

		return dao.getCrosswordsOf(user);
	}

	/**
	 * Notifica al historial que tiene que refrescar la vista.
	 */
	public void refreshHistorial() {
		crosswordNotify("REFRESH_HISTORIAL");

	}

	/**
	 * Método que notifica a los observadores eventos del crucigrama.
	 * 
	 * @param arg
	 *            Evento de la aplicación
	 */
	private void crosswordNotify(String arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}

	/**
	 * @return Devuelve el título del crucigrama que está resolviendo el usuario
	 *         que maneja la aplicación.
	 */
	public String getCurrentCrosswordTitle() {
		return dao.getCrosswordTitle(crosswordId);
	}

	/**
	 * Devuelve la imagen de la palabra pasada como parámetro
	 * 
	 * @param name
	 *            Secuencia de caracteres de la palabra
	 * @return La imagen de la palabra pasada como parámetro
	 */
	public byte[] getImageFromWord(String name) {
		return dao.getImageWord(name);
	}

	/**
	 * Método que añade los puntos correspondientes al usuario al resolver una
	 * palabra.
	 * 
	 * @param pointsFromWord
	 *            Puntos totales a repartir entre los usuarios que resuelven la
	 *            palabra.
	 */
	public void addPoints(Integer pointsFromWord) {
		if (helped == null)
			points += pointsFromWord;
		else
			points += (pointsFromWord) / 2;
		crosswordNotify("CHANGE_POINTS");

	}

	/**
	 * @return Devuelve los puntos que tiene el usuario que maneja la
	 *         aplicación.
	 */
	public Integer getPoints() {
		return points;
	}

	/**
	 * Responder a una palabra del crucigrama.
	 * 
	 * @param b
	 *            Booleano que inica si es correcta la respuesta
	 * @param text
	 *            Texto introducido por el usuario al responder
	 * @param id
	 *            Identificador de la palabra
	 */
	public void addAnswer(boolean b, String text, Integer id) {
		if (helped == null)
			dao.addAnswer(id, crosswordId, user, text, b);
		else
			dao.addAnswerHelped(id, crosswordId, user, text, b, helped);

	}

	/**
	 * Determina si una palabra ha sido resuelta en el crucigrama que está
	 * resolviendo el usuario que está manejando la aplicación.
	 * 
	 * @param id
	 *            Identificador de la palabra
	 * @return Si la palabra esta resuelta o no
	 */
	public Boolean isResolved(Integer id) {
		return dao.isResolved(crosswordId, id, user);
	}

	/**
	 * Añadir un amigo a la lista de amigos del usuario
	 * 
	 * @param amigo
	 *            Nick del usuario al que se quiere hacer amigo
	 */
	public void addFriend(String amigo) {

		if (amigo == null || amigo.isEmpty()) {
			crosswordNotify("FRIEND_ADD_FAILURE2");
		} else {
			if (dao.addFriend(user, amigo))
				crosswordNotify("FRIEND_ADD");
			else
				crosswordNotify("FRIEND_ADD_FAILURE");
		}

	}

	/**
	 * Eliminar un amigo de la lista de amigos.
	 * 
	 * @param amigo
	 *            Nick del amigo al que se quiere eliminar
	 */
	public void deleteFriend(String amigo) {
		if (amigo == null || amigo.isEmpty()) {
			crosswordNotify("FRIEND_DELETE_FAILURE");
		} else {
			dao.deleteFriend(user, amigo);
			crosswordNotify("FRIEND_DELETE");
		}
	}

	/**
	 * @return Una lista todos los amigos del usuario que está manejando la
	 *         aplicación.
	 */
	public List<Object[]> listFriends() {
		return dao.listFriends(user);
	}

	/**
	 * Añadir una petición de ayuda
	 * 
	 * @param amigo
	 *            Nick del amigo al que quiere pedir ayuda
	 * @param crosswordId
	 *            Identificador del crucigrama al que quieres que te ayude el
	 *            amigo
	 */
	public void addRequest(String amigo, int crosswordId) {

		if (!dao.isFriend(user, amigo) || amigo == null || amigo.isEmpty())
			crosswordNotify("REQUEST_ADD_FAILURE2");
		else {
			dao.addRequest(user, amigo, crosswordId);
			crosswordNotify("REQUEST_ADD");
		}
	}

	/**
	 * Eliminar una petición
	 * 
	 * @param helped
	 *            Usuario al que ayuda
	 * @param crosswordId
	 *            Identificador del crucigrama en el que estás ayudando.
	 */
	public void deleteRequest(String helped, Integer crosswordId) {
		dao.deleteRequest(helped, crosswordId, user);
		crosswordNotify("REFRESH_REQUEST");

	}

	/**
	 * @return La lista de peticiones entrantes que tiene el usuario que maneja
	 *         la aplicación.
	 */
	public List<Object[]> listRequest() {
		return dao.listRequest(user);
	}

	/**
	 * @return Indica si hay una petición a la que te piden ayuda en el
	 *         crucigrama actual.
	 */
	public boolean isRequestHelped() {
		if (helped != null)
			return dao.isRequest(helped, crosswordId);
		else
			return false;
	}

	/**
	 * @return Indica si has enviado una petición para el crucigrama actual.
	 */
	public boolean isRequest() {
		if (user != null)
			return dao.isRequest(user, crosswordId);
		else
			return false;
	}

	/**
	 * Método get del atributo helped de la clase CrosswordModel.
	 * 
	 * @return Devuelve el nick del usuario al que vas a ayudar
	 */
	public String getHelped() {
		return helped;
	}

	/**
	 * Modifica set del atributo helped de la clase CrosswordModel.
	 * 
	 * @param helped
	 *            Nick del usuario al que vas a ayudar
	 */
	public void setHelped(String helped) {
		this.helped = helped;
	}

	/**
	 * Determina si el usuario al que vas a ayudar ya ha resuelvo la palabra que
	 * vas a responder del crucigrama del que te pide ayuda.
	 * 
	 * @param id
	 *            La palabra que se comprueba
	 * @return Si esta resuelta o no por el usuario al que ayudas
	 */
	public Object isResolvedHelped(Integer id) {
		return dao.isResolved(crosswordId, id, helped);
	}

	/**
	 * Finaliza el crucigrama en el caso de que esté resuelto completamente.
	 */
	public void resolved() {
		if (helped != null) {
			dao.resolved(helped, crosswordId);
			dao.deleteRequest(helped, crosswordId, user);
		} else {
			dao.resolved(user, crosswordId);
			dao.deleteRequest(user, crosswordId, helped);
		}
		crosswordNotify("REFRESH_REQUEST");
		crosswordNotify("REFRESH_HISTORIAL");

	}

}
