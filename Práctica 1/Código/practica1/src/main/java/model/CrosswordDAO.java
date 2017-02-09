package model;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import model.Crucigrama;
import model.CrucigramasMapper;
import model.Historial;
import model.HistorialId;
import model.HistorialMapper;
import model.Usuario;
import model.UsuariosMapper;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class CrosswordDAO {
	private DataSource ds;

	/**
	 * Aquí se debe inicializar el pool de conexiones, mediante la creación de
	 * un DataSource, que deberá ser asignado a la variable ds.
	 */
	public CrosswordDAO() throws Exception {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver");
		cpds.setJdbcUrl("jdbc:mysql://localhost/practica1_502");
		cpds.setUser("UsuarioP1");
		cpds.setPassword("");
		cpds.setAcquireRetryAttempts(1);
		cpds.setAcquireRetryDelay(1);

		this.ds = cpds;
	}

	/**
	 * Añade un usuario a la aplicación.
	 * 
	 * @param user
	 *            Nick del usuario creado
	 * @param pass
	 *            Contraseña del usuario creado
	 * @return Devuelve si se ha añadido correctamente el usuario
	 */
	public boolean addUser(String user, String pass) {

		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = new Usuario(user, pass, null, null);
		return um.insert(u);

	}

	/**
	 * Devuelve el usuario correspondiente al nick.
	 * 
	 * El usuario se identifica mediante el nick
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @return Devuelve el usuario correspondiente al nick si es que lo
	 *         encuentra.
	 */
	public Usuario findUser(String nick) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);

		return u;
	}

	/**
	 * Devuelve la fecha de nacimiento del usuario cuyo nick se pasa como
	 * parámetro. Devuelve null si el usuario no existe.
	 * 
	 * @oaram nick Nick del usuario
	 * @return Fecha de nacimiento del usuario pasado como parámetro
	 */
	public Date getDate(String nick) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);

		if (u == null)
			return null;

		return u.getDate();
	}

	/**
	 * Devuelve la imagen del usuario cuyo nick se pasa como parámetro. Devuelve
	 * null si el usuario no existe
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @return Imagen de perfil del usuario pasado como parámetro
	 */
	public byte[] getImage(String nick) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);

		if (u == null)
			return null;

		return u.getImage();
	}

	/**
	 * Devuelve la imagen de la palabra de un crucigrama cuya secuencia de
	 * carácteres corresponde con lo escrito en el parámetro palabra. Devuelve
	 * null si la palabra no existe.
	 * 
	 * @param palabra
	 *            Secuencia de caracteres que forman la palabra
	 * @return Imagen de la palabra pasada como parámetro
	 */
	public byte[] getImageWord(String palabra) {
		PalabrasMapper um = new PalabrasMapper(ds);
		List<Palabra> p = um.findByName(palabra);

		return p.get(0).getImage();
	}

	/**
	 * Devuelve la contraseña del usuario cuyo nick se pasa como parámetro.
	 * Devuelve null si el usuario no existe
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @return Contraseña del usuario pasado como parámetro
	 */
	public String getPassword(String nick) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);

		if (u == null)
			return null;

		return u.getPassword();
	}

	/**
	 * Modifica la contraseña del usuario pasado como parámetro
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @param newPassword
	 *            Nueva contraseña
	 */
	public void modifyPassword(String nick, String newPassword) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);
		u.setPassword(newPassword);
		um.update(u);

	}

	/**
	 * Modifica la fecha de nacimiento del usuario pasado como parámetro
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @param newDate
	 *            Nueva fecha de nacimiento
	 */
	public void modifyDate(String nick, Date newDate) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);
		u.setDate(newDate);
		um.update(u);

	}

	/**
	 * Modifica el avatar del usuario pasado como parámetro
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @param newImage
	 *            Nueva imagen de perfil de usuario
	 */
	public void modifyImage(String nick, byte[] newImage) {
		UsuariosMapper um = new UsuariosMapper(ds);
		Usuario u = um.findById(nick);
		u.setImage(newImage);
		um.update(u);

	}

	/**
	 * Devuelve una lista de las claves de aquellos crucigramas cuyo título
	 * contenga str.
	 * 
	 * Si escogisteis una clave numérica para la tabla de crucigramas, se debe
	 * devolver una lista de Integer. Si escogisteis el título como clave, se
	 * debe devolver una lista de String. Si, por el contrario, escogisteis una
	 * clave compuesta, debéis crear una clase para almacenar los atributos de
	 * dicha clave.
	 */
	public List<Object> findCrosswordsByTitle(String str) {
		CrucigramasMapper cm = new CrucigramasMapper(ds);
		List<Crucigrama> c = new LinkedList<Crucigrama>();
		c = cm.findByTitle(str);
		List<Object> ret = new LinkedList<Object>();
		for (int i = 0; i < c.size(); i++) {
			ret.add(c.get(i).getCrosswordId());
		}
		return ret;
	}

	/**
	 * Devuelve el título del crucigrama cuya clave se pasa como parámetro.
	 */
	public String getCrosswordTitle(Object id) {
		CrucigramasMapper cm = new CrucigramasMapper(ds);
		Crucigrama c = cm.findById(new Integer(String.valueOf(id)));
		return c.getTitle();
	}

	/**
	 * Devuelve la fecha del crucigrama cuya clave se pasa como parámetro.
	 */
	public Date getCrosswordDate(Object id) {
		CrucigramasMapper cm = new CrucigramasMapper(ds);
		Crucigrama c = cm.findById(new Integer(String.valueOf(id)));
		return c.getDate();
	}

	/**
	 * Añade un crucigrama a la lista de crucigramas activos de un usuario.
	 * 
	 * El crucigrama se especifica mediante su clave
	 * 
	 * @return Devuelve un booleano que indica si el crucigrama se ha añadido
	 *         correctamente al historial.
	 */
	public boolean addCrosswordToUser(String nick, Object crosswordId) {
		HistorialMapper hm = new HistorialMapper(ds);
		HistorialId id = new HistorialId(nick, (Integer) crosswordId);
		return hm.insert(new Historial(id, false));
	}

	/**
	 * Devuelve una lista de objects que contiene la información de las palabras
	 * del identificador del crucigrama que se pasa como parámetro.
	 * 
	 * @param crosswordId
	 *            Identificador natural del crucigrama
	 */
	public List<Object[]> getWordsOfCrossword(Object crosswordId) {
		ContieneMapper cm = new ContieneMapper(ds);
		List<Contiene> contentList = cm
				.findByCrosswordId((Integer) crosswordId);
		List<Object[]> ret = new LinkedList<Object[]>();
		PalabrasMapper pm = new PalabrasMapper(ds);
		for (Contiene i : contentList) {
			ContieneId id = i.getId();
			Palabra p = pm.findById(id.getWordId());
			ret.add(new Object[] { i.getOrientation(), i.getX(), i.getY(),
					p.getTitle(), p.getWording(), p.getImage(), i.getPoints(),
					p.getWordId() });
		}
		return ret;

	}

	/**
	 * Devuelve la lista de identificadores de los crucigramas activos del
	 * usuario pasado como parámetro
	 */
	public List<Object> getCrosswordsOf(String nick) {
		HistorialMapper hm = new HistorialMapper(ds);
		List<Historial> h = hm.findByNick(nick);
		List<Object> ret = new LinkedList<Object>();
		for (int i = 0; i < h.size(); i++)
			if (!h.get(i).isFinished())
				ret.add(h.get(i).getId().getCrosswordId());
		return ret;
	}

	/**
	 * Añade una respuesta a la BD del usuario que está resolviento el
	 * crucigrama.
	 * 
	 * @return
	 */
	public boolean addAnswer(Integer id, Integer crosswordId, String user,
			String text, boolean b) {

		RespuestasMapper rm = new RespuestasMapper(ds);
		java.util.Date date = new java.util.Date();
		Respuesta r = new Respuesta(
				new RespuestaId(id, crosswordId, date, user), user, text, b);
		return rm.insert(r);

	}

	/**
	 * Añade una respuesta a la BD del usuario que está resolviento el
	 * crucigrama, es decir, al que se le ha mandado la petición de ayuda.
	 * 
	 * @return
	 */
	public boolean addAnswerHelped(Integer id, Integer crosswordId,
			String user, String text, boolean b, String helped) {

		RespuestasMapper rm = new RespuestasMapper(ds);
		java.util.Date date = new java.util.Date();
		Respuesta r = new Respuesta(new RespuestaId(id, crosswordId, date,
				helped), user, text, b);
		return rm.insert(r);

	}

	/**
	 * Calcula la puntuación que se tiene que llevar el usuario por cada palabra
	 * que resuelve.
	 * 
	 * @return
	 */
	public Integer pointCalculation(String user) {
		Integer points = 0;
		RespuestasMapper rm = new RespuestasMapper(ds);
		ContieneMapper cm = new ContieneMapper(ds);
		List<Respuesta> answers = rm.findByOwner(user);
		for (Respuesta i : answers) {
			if (i.isCorrect()) {
				ContieneId id = new ContieneId(i.getId().getCrossword(), i
						.getId().getWord());
				Contiene c = cm.findById(id);
				if (i.getSolvingUser().equalsIgnoreCase(user))
					points += c.getPoints();
				else
					points += (c.getPoints()) / 2;
			}

			else if (i.getSolvingUser().equalsIgnoreCase(user))
				points -= 10;
		}
		answers = rm.findByHelped(user);
		for (Respuesta i : answers) {
			if (i.isCorrect()) {
				ContieneId id = new ContieneId(i.getId().getCrossword(), i
						.getId().getWord());
				Contiene c = cm.findById(id);
				points += (c.getPoints()) / 2;
			}

			else
				points -= 10;

		}
		return points;
	}

	/**
	 * Indica si se ha resuelto correctamente la palabra del crucigrama resuelta
	 * por el usuario, todo ello pasado como parámetro.
	 * 
	 * @return
	 */
	public Boolean isResolved(Integer crosswordId, Integer id, String user) {
		Boolean ret = false;
		RespuestasMapper rm = new RespuestasMapper(ds);
		List<Respuesta> r = rm.findResolved(crosswordId, id, user);
		if (r != null) {
			for (Respuesta w : r)
				if (w.isCorrect())
					ret = true;
		}

		return ret;
	}

	/**
	 * Añade un amigo a la lista de amigos del usuario.
	 * 
	 * @return
	 */
	public boolean addFriend(String amigo1, String amigo2) {
		AmigosMapper am = new AmigosMapper(ds);
		Amigos amistad = new Amigos(new AmigosId(amigo1, amigo2));

		return am.insert(amistad);
	}

	/**
	 * Elimina un amigo de la lista de amigos del usuario.
	 */
	public void deleteFriend(String amigo1, String amigo2) {
		AmigosMapper am = new AmigosMapper(ds);

		am.deleteById(amigo1, amigo2);
	}

	/**
	 * Devuelve una lista de arrays de objetos, con la lista de amigos del
	 * usuario pasado como parametro.
	 * 
	 * @return
	 */
	public List<Object[]> listFriends(String nick) {
		AmigosMapper am = new AmigosMapper(ds);
		return am.findFriendsByName(nick);
	}

	/**
	 * Añade una petición de ayuda.
	 */
	public void addRequest(String amigo1, String amigo2, Integer crosswordId) {
		PeticionesMapper pm = new PeticionesMapper(ds);
		PeticionId id = new PeticionId(amigo1, amigo2, crosswordId);
		pm.insert(new Peticion(id));
	}

	/**
	 * Devuelve una lista de arrays de objetos, con la lista de peticiones
	 * entrantes del usuario pasado como parámetro.
	 * 
	 * @return
	 */
	public List<Object[]> listRequest(String nick) {
		PeticionesMapper pm = new PeticionesMapper(ds);

		List<Object[]> request = pm.findRequestByName(nick);
		List<Object[]> result = new LinkedList<Object[]>();
		for (Object[] i : request) {
			String nombre_crucigrama = getCrosswordTitle((Integer) i[1]);
			result.add(new Object[] { i[0], nombre_crucigrama });
		}

		return result;
	}

	/**
	 * Indica si son amigos, los dos usuarios pasado como parámetro.
	 * 
	 * @return
	 */
	public boolean isFriend(String amigo1, String amigo2) {
		AmigosMapper am = new AmigosMapper(ds);
		return am.isFriend(amigo1, amigo2);
	}

	/**
	 * Indica si el usuario pasado como parámetro ha enviado una petición de
	 * ayuda al crucigrama pasado como parámtro.
	 * 
	 * @return
	 */
	public boolean isRequest(String nick, int crosswordId) {
		PeticionesMapper pm = new PeticionesMapper(ds);
		return pm.isRequest(nick, crosswordId);
	}

	/**
	 * Si se ha resuelto el crucigrama correctamente, finaliza el crucigrama.
	 */
	public void resolved(String user, Integer crosswordId) {
		HistorialMapper hm = new HistorialMapper(ds);
		hm.updateResolved(new HistorialId(user, crosswordId));

	}

	/**
	 * Elimina una petición entrante del usuario.
	 */
	public void deleteRequest(String helped, Integer crosswordId, String user) {
		PeticionesMapper pm = new PeticionesMapper(ds);
		List<Object[]> request = pm.findRequestByName(user);

		for (Object[] i : request) {
			String helpedNick = String.valueOf(i[0]);
			Integer id = (Integer) i[1];
			if (helped.equalsIgnoreCase(helpedNick)) {
				if (id == crosswordId)
					pm.delete(new Peticion(new PeticionId(helped, user,
							crosswordId)));
			}
		}
	}

	/**
	 * Cierra el dataSource
	 */
	public void close() {
		((ComboPooledDataSource) ds).close();
	}

}