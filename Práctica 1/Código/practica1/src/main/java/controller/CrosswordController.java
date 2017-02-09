package controller;

import java.sql.Date;
import java.util.List;
import java.util.Observer;

import model.CrosswordModel;

/**
 * Clase que representa el controlador de la aplicación, que permite
 * interaccionar a la vista con el modelo.
 * 
 * @author Grupo 502
 *
 */
public class CrosswordController {

	private CrosswordModel model; // Modelo de la aplicación

	/**
	 * Constructor de la clase
	 * 
	 * @param model
	 *            Modelo de la aplicación
	 */
	public CrosswordController(CrosswordModel model) {

		this.model = model;
	}

	/**
	 * Metodo getUser
	 * 
	 * @return Devuelve el nick del usuario que está manejando la aplicación
	 */
	public String getUser() {
		return model.getUser();
	}

	/**
	 * Metodo getCrossword
	 * 
	 * @return Devuelve el identificador del crucigrama que está resolviendo el
	 *         usuario
	 */
	public Integer getCrossword() {
		return model.getCrossword();
	}

	/**
	 * Metodo setCrossword Establece el crucigrama indicado como el crucigrama
	 * actual
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama que está resolviendo el usuario
	 */
	public void setCrossword(Integer crosswordId) {
		model.setCrossword(crosswordId);
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
		return model.addUser(user, pass);
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
		return model.login(user, pass);
	}

	/**
	 * Devuelve la contraseña del usuario pasado como parámetro.
	 * 
	 * @param user
	 *            Nick del usuario
	 * @return Devuelve la contraseña del usuario pasado como parámetro.
	 */
	public String getPassword(String user) {

		return model.getPassword(user);
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario que está manejando la
	 * aplicación.
	 * 
	 * @return Devuelve la fecha de nacimiento del usuario que está manejando la
	 *         aplicación
	 */
	public Date getDate() {
		return model.getDate();
	}

	/**
	 * Devuelve la imagen de perfil del usuario que está manejando la
	 * aplicación.
	 * 
	 * @return Devuelve la imagen de perfil del usuario que está manejando la
	 *         aplicación
	 */
	public byte[] getImage() {
		return model.getImage();
	}

	/**
	 * Modifica la contraseña del usuario que está manejando la aplicación.
	 * 
	 * @param newPassword
	 *            Nueva contraseña
	 */
	public void modifyPassword(String newPassword) {

		model.modifyPassword(newPassword);

	}

	/**
	 * Modifica la fecha de nacimiento del usuario que está manejando la
	 * aplicación.
	 * 
	 * @param newDate
	 *            Nueva fecha de nacimiento
	 */
	public void modifyDate(Date newDate) {

		model.modifyDate(newDate);

	}

	/**
	 * Modifica la imagen de perfil del usuario que está manejando la aplicación
	 * 
	 * @param newImage
	 *            Nueva imagen
	 */
	public void modifyImage(byte[] newImage) {

		model.modifyImage(newImage);

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

		return model.findCrosswordsByTitle(str);
	}

	/**
	 * Devuelve el título del crucigrama pasado como parámetro.
	 * 
	 * @param id
	 *            Identificador del crucigrama
	 * @return El título del crucigrama pasado como parámetro.
	 */
	public String getCrosswordTitle(Object id) {

		return model.getCrosswordTitle(id);
	}

	/**
	 * Devuelve la fecha de creación del crucigrama pasado como parámetro
	 * 
	 * @param id
	 *            Identificador del crucigrama
	 * @return La fecha de creación del crucigrama pasado como parámetro
	 */
	public Date getCrosswordDate(Object id) {

		return model.getCrosswordDate(id);
	}

	/**
	 * Añade un crucigrama al historial del usuario que está manejando la
	 * aplicación.
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama a añadir.
	 */
	public void addCrosswordToUser(Object crosswordId) {

		model.addCrosswordToUser(crosswordId);
	}

	/**
	 * Devuelve la lista de identificadores de los crucigramas activos del
	 * usuario que está manejando la aplicación.
	 */
	public List<Object> getCrosswordsOf() {

		return model.getCrosswordsOf();
	}

	/**
	 * Devuelve la lista de palabra del crucigrama que está resolviendo el
	 * usuario.
	 * 
	 * @return La lista de palabra del crucigrama que está resolviendo el
	 *         usuario.
	 */
	public List<Object[]> getWordOf() {

		return model.getWordsOfCrossword();
	}

	/**
	 * Añade un osbservador al modelo
	 * 
	 * @param o
	 *            EL observador a añadir
	 */
	public void addObserver(Observer o) {
		model.addObserver(o);
	}

	/**
	 * Notifica al historial que tiene que refrescar la vista.
	 */
	public void refresh() {
		model.refreshHistorial();

	}

	/**
	 * @return Devuelve el título del crucigrama que está resolviendo el usuario
	 *         que maneja la aplicación.
	 */
	public String getCurrentCrosswordTitle() {

		return model.getCurrentCrosswordTitle();
	}

	/**
	 * Devuelve la imagen de la palabra pasada como parámetro
	 * 
	 * @param name
	 *            Secuencia de caracteres de la palabra
	 * @return La imagen de la palabra pasada como parámetro
	 */
	public byte[] getImageFromWord(String name) {
		return model.getImageFromWord(name);
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
		model.addPoints(pointsFromWord);

	}

	/**
	 * @return Devuelve los puntos que tiene el usuario que maneja la
	 *         aplicación.
	 */
	public Integer getPoints() {
		return model.getPoints();
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
		model.addAnswer(b, text, id);

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
		return model.isResolved(id);
	}

	/**
	 * Añadir un amigo a la lista de amigos del usuario
	 * 
	 * @param amigo
	 *            Nick del usuario al que se quiere hacer amigo
	 */
	public void addFriend(String amigo) {
		model.addFriend(amigo);
	}

	/**
	 * Eliminar un amigo de la lista de amigos.
	 * 
	 * @param amigo
	 *            Nick del amigo al que se quiere eliminar
	 */
	public void deleteFriend(String amigo) {
		model.deleteFriend(amigo);
	}

	/**
	 * @return Una lista todos los amigos del usuario que está manejando la
	 *         aplicación.
	 */
	public List<Object[]> listFriends() {
		return model.listFriends();
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
		model.addRequest(amigo, crosswordId);
	}

	/**
	 * @return La lista de peticiones entrantes que tiene el usuario que maneja
	 *         la aplicación.
	 */
	public List<Object[]> listRequest() {
		return model.listRequest();
	}

	/**
	 * @return Indica si has enviado una petición para el crucigrama actual.
	 */
	public boolean isRequest() {
		return model.isRequest();
	}

	/**
	 * @return Indica si hay una petición a la que te piden ayuda en el
	 *         crucigrama actual.
	 */
	public boolean isRequestHelped() {
		return model.isRequestHelped();
	}

	/**
	 * Metodo que prepara a la aplicación para resolver una petición de ayuda
	 * 
	 * @param crosswordId
	 *            Identificador del crucigrama que se va a resolver
	 * @param helped
	 *            Nick del usuario al que vas a ayudar
	 */
	public void setCrosswordHelp(Integer crosswordId, String helped) {
		model.setHelped(helped);
		model.setCrossword(crosswordId);

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
		return model.isResolvedHelped(id);
	}

	/**
	 * Indica a la aplicación que ya no estas resolviendo una petición
	 */
	public void reset() {
		model.setHelped(null);

	}

	/**
	 * @return Devuelve el nick del usuario al que vas a ayudar
	 */
	public String getHelped() {
		return model.getHelped();

	}

	/**
	 * Finaliza el crucigrama en el caso de que esté resuelto completamente.
	 */
	public void resolved() {
		model.resolved();

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
		model.deleteRequest(helped, crosswordId);

	}

}
