package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.CrosswordController;

/**
 * Clase que representa el panel principal de la aplicación
 * 
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class AppPanel extends JFrame implements Observer {

	private CrosswordController controller; // Controlador de la aplicación
	private Container _panelPrincipal; // Panel principal que contiene el resto
	// de paneles
	private JPanel _panelSup; // Panel que contiene la parte de arriba de la
	// ventana
	private JPanel _panelSupI; // Panel que contiene el avatar de usuario
	private JPanel _panelSupD; // Panel que contiene la informacion del usuario
	private ImageIcon img; // Avatar del usuario
	private JButton imageButton; // Boton que contiene el avatar
	private JLabel _userLabel; // Nick del usuario
	private JLabel _ageLabel; // Edad del usuario
	private JLabel _pointsLabel; // Puntos del usuario
	private Integer points; // Entero que representa los puntos dle usuario
	private JTabbedPane _panelCentral; // Panel que contiene las distintas
	// pestañas de la aplicacion
	private UserPanel userPanel; // Panel de edicion de la informacion

	/**
	 * Constructor de la clase
	 * 
	 * @param controller
	 *            Controlador de la aplicacion
	 */
	public AppPanel(CrosswordController controller) {
		super("APP Crucigramas");
		this.controller = controller;
		controller.addObserver(this);
		this.userPanel = new UserPanel(controller);
		buildGUI();
	}

	/**
	 * Metodo que inicializa todos los elementos de la vista
	 */
	private void buildGUI() {

		_panelSup = new JPanel();
		_panelSup.setLayout(new GridLayout(1, 1));
		_panelSupI = new JPanel();
		_panelSupI.setLayout(new FlowLayout());
		img = new ImageIcon("src/main/resources/avatar.png");
		imageButton = new JButton();
		imageButton.setMargin(new Insets(0, 0, 0, 0));
		imageButton.setIcon(img);
		imageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userPanel.setVisible(true);
			}

		});

		_panelSupI.add(imageButton);
		_panelSupD = new JPanel();
		_panelSupD.setLayout(new BoxLayout(_panelSupD, BoxLayout.Y_AXIS));
		_panelSupD.setBorder(new EmptyBorder(img.getIconHeight() / 3, 0, 0, 0));
		_userLabel = new JLabel();
		_userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_ageLabel = new JLabel();
		_ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_pointsLabel = new JLabel();
		_pointsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		_pointsLabel.setForeground(Color.ORANGE);
		points = 0;
		_panelSupD.add(_userLabel);
		_panelSupD.add(_ageLabel);
		_panelSupD.add(_pointsLabel);
		_panelSup.add(_panelSupI);
		_panelSup.add(_panelSupD);
		_panelPrincipal = new JPanel();
		_panelPrincipal.setLayout(new BorderLayout());
		_panelCentral = new JTabbedPane();

		JPanel crosswordPanel = new CrosswordOfPanel(controller);
		JPanel friendsPanel = new FriendsPanel(controller);
		JPanel requestPanel = new RequestPanel(controller);

		_panelCentral
		.addTab("Crucigramas", null, crosswordPanel,
				"En esta pestaña aparece la lista de tus crucigramas activos. ");

		_panelCentral.addTab("Amigos", null, friendsPanel,
				"En esta pestaña aparece la lista de tus amigos. ");

		_panelCentral.addTab("Peticiones de ayuda", null, requestPanel,
				"En esta pestaña aparece la lista de peticiones de ayuda que te han "
						+ "enviado tus amigos. ");

		_panelPrincipal.add(_panelSup, BorderLayout.NORTH);
		_panelPrincipal.add(_panelCentral, BorderLayout.CENTER);

		this.setSize(400, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(_panelPrincipal);

	}

	/**
	 * Metodo que convierte un array de bits a img
	 * 
	 * @param array
	 *            El array de bits
	 * @return La imagen
	 */
	private BufferedImage ArrayByteToImage(byte[] array) {

		InputStream in = null;
		BufferedImage image = null;

		if (array == null) {
			ImageIcon ic = new ImageIcon("src/main/resources/avatar.png");
			BufferedImage bi = new BufferedImage(ic.getIconWidth(),
					ic.getIconHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics g = bi.getGraphics();
			ic.paintIcon(null, g, 0, 0);
			g.dispose();

			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			try {
				ImageIO.write(bi, "png", byteStream);
			} catch (IOException e) {

				e.printStackTrace();
			}
			array = byteStream.toByteArray();
		}

		try {
			in = new ByteArrayInputStream(array);
			image = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;

	}

	/**
	 * Metodo que actualiza el usuario de la aplicacion
	 */
	private void updateUser() {

		points = controller.getPoints();
		img.setImage(ArrayByteToImage(controller.getImage()));
		_panelSupD.setBorder(new EmptyBorder(img.getIconHeight() / 3, 0, 0, 0));
		_userLabel.setText(controller.getUser());
		if ((controller.getDate() == null) == false) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(controller.getDate());

			GregorianCalendar calendarActual = new GregorianCalendar();

			int edad = calendarActual.get(Calendar.YEAR)
					- calendar.get(Calendar.YEAR);
			_ageLabel.setText(String.valueOf(edad) + " años");
		} else {
			_ageLabel.setText("Edad desconocida");
		}
		_pointsLabel.setText(points + " puntos");

	}

	/**
	 * Metodo update
	 */
	@Override
	public void update(Observable o, Object arg) {
		String cadena = String.valueOf(arg);

		switch (cadena) {
		case "LOGIN":
			updateUser();
			break;
		case "UPDATE_USER":
			updateUser();
			break;
		case "CHANGE_POINTS":
			points = controller.getPoints();
			updateUser();
			break;
		}

	}

}