package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controller.CrosswordController;

/**
 * Clase que representa la vista para hacer login
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JFrame implements Observer {

	private Container _panelPrincipal; // Contenedor principal
	private JPanel _panelLogin; // Panel del login
	private JLabel userLabel; // Etiqueta de usuario
	private JLabel passwordLabel; // Etiqueta de contraseña
	private JTextField userText; // Espacio para insertar el usuario
	private JPasswordField passwordText; // Espacio para insertar la contraseña
	private JButton loginButton; // Boton de login
	private JButton registerButton; // Boton de registrar usuario
	private JFrame appPanel; // Panel principal de la aplicacion
	private CrosswordController controller; // Controlador de la aplicacion

	/**
	 * Constructor de la clase
	 * 
	 * @param controller
	 *            Controlador de la aplicacion
	 */
	public LoginPanel(CrosswordController controller) {
		super("Bienvenido");
		this.controller = controller;
		controller.addObserver(this);
		this.appPanel = new AppPanel(controller);

		buildGUI();
	}

	/**
	 * Metodo que crea la interfaz
	 */
	private void buildGUI() {

		userLabel = new JLabel("Nombre de usuario: ");

		userText = new JTextField();

		passwordLabel = new JLabel("Contraseña:   ");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		passwordText = new JPasswordField();

		loginButton = new JButton("Aceptar");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}

		});

		registerButton = new JButton("Nuevo usuario");

		registerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!userText.getText().equalsIgnoreCase("")) {
					char[] pass = passwordText.getPassword();
					String finalpass = new String();
					for (int i = 0; i < pass.length; i++)
						finalpass += pass[i];
					if (controller.addUser(userText.getText(), finalpass))
						login();
				} else
					JOptionPane.showMessageDialog(null,
							"No se ha introducido un usuario.");
			}

		});

		_panelPrincipal = new JPanel();
		_panelPrincipal.setLayout(new BorderLayout());

		_panelLogin = new JPanel();
		_panelLogin.setLayout(new GridLayout(2, 2));
		_panelLogin.setBorder(new EmptyBorder(20, 20, 20, 20));

		_panelLogin.add(userLabel);
		_panelLogin.add(userText);
		_panelLogin.add(passwordLabel);
		_panelLogin.add(passwordText);

		JPanel _panelButton = new JPanel();
		_panelButton.setLayout(new FlowLayout());
		_panelButton.add(loginButton);
		_panelButton.add(registerButton);

		_panelPrincipal.add(_panelLogin, BorderLayout.NORTH);
		_panelPrincipal.add(_panelButton, BorderLayout.SOUTH);

		this.add(_panelPrincipal);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Metodo update
	 */
	@Override
	public void update(Observable o, Object arg) {
		String cadena = String.valueOf(arg);
		switch (cadena) {
		case "LOGIN_FAILURE":
			JOptionPane.showMessageDialog(null,
					"Usuario o contraseña incorrecto.");
			break;
		case "EXISTING_USER":
			JOptionPane.showMessageDialog(null, "Usuario existente.");
			break;

		case "USER_CREATED":
			JOptionPane.showMessageDialog(null, "Usuario creado con exito.");
			break;

		}
	}

	/**
	 * Metodo que realiza el login
	 */
	private void login() {
		String user = userText.getText();
		char[] pass = passwordText.getPassword();
		String finalpass = new String();
		for (int i = 0; i < pass.length; i++)
			finalpass += pass[i];

		if (controller.login(user, finalpass)) {
			setVisible(false);
			appPanel.setVisible(true);
			controller.refresh();
		}
	}
}