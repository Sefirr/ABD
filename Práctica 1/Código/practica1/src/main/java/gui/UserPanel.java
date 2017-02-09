package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import controller.CrosswordController;

/**
 * Clase que representa la vista del panel de usuario
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class UserPanel extends JFrame implements Observer {

	private CrosswordController controller; // Controlador de la aplicacion
	private JPanel _panelPrincipal; // Panel principal
	private JPanel _panelSup; // Panel superior
	private JLabel passwordLabel; // Etiqueta de contraseña
	private JTextField passwordField; // Espacio para la contraseña
	private JLabel birthLabel; // Etiqueta de la fecha de nacimiento
	private JDateChooser DateChooser; // Selector de fecha
	private JPanel _panelCentral; // El panel central
	private JLabel imageLabel; // Etiqueta de imagen
	private JPanel _imagePanel; // Panel que contiene la imagen
	private ImageIcon img; // Avatar por defecto
	private ImageIcon imgModificada; // Avatar elegido por el usario
	private JLabel imgLabel; // Etiqueta que contiene la imagen
	private JButton switchImageBtn; // Boton para cambiar la imagen
	private JPanel _panelInf; // Panel inferior
	private JButton AcceptBtn; // Boton de aceptar
	private JButton CancelBtn; // Boton de cancelar
	private File f; // Archivo seleccionado como nuevo avatar

	/**
	 * Constructor de la clase
	 * 
	 * @param cont
	 *            El controlador de la aplicacion
	 */
	public UserPanel(CrosswordController cont) {
		super("Editar contacto");
		this.controller = cont;

		controller.addObserver(this);

		buildUI();
	}

	/**
	 * Metodo que crea la interfaz
	 */
	private void buildUI() {

		_panelPrincipal = new JPanel();
		_panelPrincipal.setLayout(new BorderLayout());
		_panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

		_panelSup = new JPanel();
		_panelSup.setLayout(new GridLayout(2, 2));
		passwordLabel = new JLabel("Contraseña: ");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		passwordField = new JTextField();

		birthLabel = new JLabel("Fecha de nacimiento: ");
		birthLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		DateChooser = new JDateChooser();

		_panelCentral = new JPanel();
		_panelCentral.setLayout(new GridLayout(1, 1));

		imageLabel = new JLabel("Foto: ");
		imageLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		_imagePanel = new JPanel();
		_imagePanel.setLayout(new BoxLayout(_imagePanel, BoxLayout.Y_AXIS));
		_imagePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		img = new ImageIcon();
		imgLabel = new JLabel(img);
		switchImageBtn = new JButton("Cambiar imagen");

		switchImageBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser selector = new JFileChooser();
				FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter(
						"JPG, PNG & GIF", "jpg", "png", "gif");
				selector.setFileFilter(filtroImagen);
				int r = selector.showOpenDialog(null);

				if (r == JFileChooser.APPROVE_OPTION) {
					try {
						f = selector.getSelectedFile();
						imgModificada = new ImageIcon(f.toURI().toURL());
						imgLabel.setIcon(imgModificada);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}

		});

		_imagePanel.add(imgLabel);
		_imagePanel.add(new JLabel(" "));
		_imagePanel.add(switchImageBtn);

		_panelCentral.add(imageLabel);
		_panelCentral.add(_imagePanel);

		_panelInf = new JPanel();
		_panelInf.setLayout(new FlowLayout());

		AcceptBtn = new JButton("Aceptar");

		AcceptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				java.util.Date d = DateChooser.getDate();
				java.sql.Date date = null;
				long tamañoKB;
				if (f == null)
					tamañoKB = 0;
				else
					tamañoKB = f.length() / 1024;

				if (d != null)
					date = new java.sql.Date(d.getTime());

				try {
					if (f != null && tamañoKB > 64)
						throw new Exception("Tamaño de archivo excedido");
					if (imgModificada != null
							&& (imgModificada.getIconHeight() > 100 || imgModificada
									.getIconWidth() > 100))
						throw new Exception(
								"El tamaño máximo de las imágenes es 100x100");

					controller.modifyPassword(passwordField.getText());
					controller.modifyImage(toByteArray(imgModificada));
					controller.modifyDate(date);

					setVisible(false);
				} catch (Exception e1) {

					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}

		});

		CancelBtn = new JButton("Cancelar");

		CancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);

			}

		});

		_panelInf.add(AcceptBtn);
		_panelInf.add(CancelBtn);
		_panelSup.add(passwordLabel);
		_panelSup.add(passwordField);
		_panelSup.add(birthLabel);
		_panelSup.add(DateChooser);

		_panelPrincipal.add(_panelSup, BorderLayout.NORTH);
		_panelPrincipal.add(_panelCentral, BorderLayout.CENTER);
		_panelPrincipal.add(_panelInf, BorderLayout.SOUTH);

		this.setSize(400, 325);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.add(_panelPrincipal);

	}

	/**
	 * Metodo que convierte una imagen a un array de bytes
	 * 
	 * @param ic
	 *            La imagen a convertir
	 * @return El array resultante de la conversion
	 */
	private byte[] toByteArray(ImageIcon ic) {

		if (ic == null)
			return null;

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
		byte[] array = byteStream.toByteArray();

		return array;

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
	 * Metodo que actualiza el usuario
	 */
	private void updateUser() {
		img.setImage(ArrayByteToImage(controller.getImage()));
		imgLabel = new JLabel(img);
		passwordField.setText(controller.getPassword(controller.getUser()));

		if (controller.getDate() != null) {
			DateChooser.setDate(controller.getDate());
		} else {
			DateChooser.setDate(null);
		}
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
		}

	}

}
