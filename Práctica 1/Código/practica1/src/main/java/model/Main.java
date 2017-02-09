package model;

import gui.LoginPanel;
import controller.CrosswordController;

/**
 * Esta clase representa el punto de entrada de la aplicación.
 * 
 * @author Grupo 502
 *
 */

public class Main {

	public static void main(String[] args) {
		CrosswordModel model = new CrosswordModel(); // Iniciamos el modelo
		CrosswordController c = new CrosswordController(model); // Le pasamos el
		// modelo al controlador y iniciamos el controlador
		LoginPanel log = new LoginPanel(c); // Iniciamos la interfaz principal
		// de la aplicación
		log.setVisible(true); // Hacemos visible la interfaz principal de la
		// aplicación
	}

}
