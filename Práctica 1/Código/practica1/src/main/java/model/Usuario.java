package model;

import java.sql.Date;

/**
 * Clase que representa el usuario que participa activamente en la aplicación.
 * Posee cuatro atributos: Nick del usuario, Contraseña del usuario, Fecha de
 * nacimiento y imagen del perfil del usuario.
 * 
 * @author Grupo 502
 *
 */

public class Usuario {
	private String nick; // Nick del usuario (Identificador natural de la
	// entidad usuario)
	private String password; // Contraseña del usuario
	private Date date; // Fecha de nacimiento del usuario.
	private byte[] image; // Imagen de perfil del usuario.

	/**
	 * Constructor de cuatro parámetros de la clase Usuario.
	 * 
	 * @param nick
	 *            Nick del usuario
	 * @param pass
	 *            Contraseña del usuario
	 * @param date
	 *            Fecha de nacimiento del usuario
	 * @param image
	 *            Imagen de perfil del usuario
	 */
	public Usuario(String nick, String pass, Date date, byte[] image) {
		this.nick = nick;
		this.password = pass;
		this.date = date;
		this.image = image;
	}

	/**
	 * Método get del atributo nick de la clase Usuario.
	 * 
	 * @return Devuelve el nick del usuario
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Método set del atributo nick de la clase Usuario.
	 * 
	 * @param nick
	 *            Nick del usuario
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Método get del atributo password de la clase Usuario.
	 * 
	 * @return Devuelve la contraseña del usuario
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Método set del atributo password de la clase Usuario.
	 * 
	 * @param pass
	 *            Contraseña del usuario
	 */
	public void setPassword(String pass) {
		this.password = pass;
	}

	/**
	 * Método get del atributo date de la clase Usuario.
	 * 
	 * @return Devuelve la fecha de nacimiento del usuario
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Método set del atributo date de la clase Usuario.
	 * 
	 * @param date
	 *            Fecha de nacimiento del usuario
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Método get del atributo image de la clase Usuario.
	 * 
	 * @return Devuelve la imagen de perfil del usuario
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Método set del atributo image de la clase Usuario.
	 * 
	 * @param image
	 *            Imagen de perfil del usuario
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

}