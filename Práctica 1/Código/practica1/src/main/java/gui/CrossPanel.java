package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.CrosswordController;
import es.ucm.abd.crossword.CrosswordPanel;
import es.ucm.abd.crossword.CrosswordPanelEventListener;

/**
 * Clase que representa la pantalla de resolucion de crucigramas
 * 
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class CrossPanel extends JFrame implements Observer {

	private CrosswordController controller; // Controlador de la aplicacion
	private JScrollPane jScrollPane; // Panel que contiene el crucigrama
	private CrosswordPanel<Word> panel; // El crucigrama
	private ImageIcon img; // Imagen de la palabra
	private JLabel imgLabel; // Contenedor de la imagen
	private JTextArea txt; // Texto de la palabra
	private JScrollPane ScrollWording; // Area donde se ve la informacion de la
	// palabra
	private JPanel _panelInferior; // Panel que contiene la parte de abajo de la
	// ventana
	private JPanel _panel1; // Panel que contiene la imagen y la informacion
	private JPanel _panel2; // Panel que contiene los botones,el numero de
	// letras y el area de respuesta
	private JLabel wordLabel; // Contiene el numero de letras de la palabra
	private JTextField answerField; // Area de respuesta
	private JButton acceptBtn; // Boton de aceptar
	private JButton sendFriendBtn; // Boton de enviar a amigo
	private List<Object[]> wordInfo;// Array que contiene la siguiente
	// informacion sobre la palabra
	// {nombre,descripcion,id,puntos,resuelta}
	private List<Word> list; // Lista de las palabras del crucigrama

	/**
	 * Constructor de la clase
	 * 
	 * @param cont
	 *            Controlador de la aplicacion
	 */
	public CrossPanel(CrosswordController cont) {
		this.controller = cont;
		controller.addObserver(this);
		buildUI();
	}

	/**
	 * Metodo de creacion de la interfaz
	 */
	private void buildUI() {
		// Creamos la lista inicial con tres palabras
		jScrollPane = new JScrollPane();
		panel = new CrosswordPanel<Word>();
		jScrollPane.setAutoscrolls(true);

		// Registramos los manejadores de eventos del CrosswordPanel
		panel.addEventListener(listenerInitialization());
		img = new ImageIcon("src/main/resources/noimagen.jpg");
		imgLabel = new JLabel(img);
		txt = new JTextArea();
		txt.setColumns(10);
		txt.setWrapStyleWord(true);
		txt.setLineWrap(true);
		txt.setEditable(false);
		ScrollWording = new JScrollPane();
		ScrollWording.setViewportView(txt);
		ScrollWording.setPreferredSize(new Dimension(200, 100));

		_panelInferior = new JPanel();
		_panelInferior.setBorder(new EmptyBorder(10, 0, 10, 4));
		_panelInferior
		.setLayout(new BoxLayout(_panelInferior, BoxLayout.Y_AXIS));
		_panel1 = new JPanel();
		_panel1.setLayout(new GridLayout(1, 2));

		wordLabel = new JLabel("");
		answerField = new JTextField(10);
		acceptBtn = new JButton("Aceptar");
		acceptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkInput();
			}

		});
		sendFriendBtn = new JButton("Enviar a amigo");
		sendFriendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String friendName = JOptionPane
						.showInputDialog("Introduzca el nombre del amigo:");
				controller.addRequest(friendName, controller.getCrossword());
				if (controller.isRequest()) {
					answerField.setEditable(false);
					acceptBtn.setEnabled(false);
				} else {
					answerField.setEditable(true);
					acceptBtn.setEnabled(true);
				}
			}

		});

		_panel1.add(imgLabel);
		_panel1.add(ScrollWording);

		_panel2 = new JPanel();
		_panel2.setLayout(new FlowLayout());

		_panel2.add(wordLabel);
		_panel2.add(answerField);
		_panel2.add(acceptBtn);
		_panel2.add(sendFriendBtn);

		_panelInferior.add(_panel1);
		_panelInferior.add(_panel2);

		this.add(jScrollPane, BorderLayout.CENTER);
		this.add(_panelInferior, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setSize(500, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

	}

	/**
	 * 
	 * @return La lista de palabras del crucigrama
	 */
	public List<Word> getList() {
		return list;
	}

	/**
	 * Metodo set para la lista
	 * 
	 * @param list
	 *            La lista de palabras
	 */
	public void setList(List<Word> list) {
		this.list = list;
	}

	/**
	 * Metodo que comprueba la informacion introducida por el usuario
	 */
	protected void checkInput() {
		if (panel.getSelectedWord() != null) {
			String input = panel.getSelectedWord().getWord();
			if (answerField.getText().equalsIgnoreCase(input)) {
				panel.showWord(panel.getSelectedWord());
				correctWord(input);
				controller.addPoints(getPointsFromWord(input));
				controller.addAnswer(true, answerField.getText(),
						getIdFromWord(input));
				if (isCompleted()) {
					controller.resolved();
					sendFriendBtn.setEnabled(false);
				}
			}

			else {
				JOptionPane.showMessageDialog(null, "Respuesta Incorrecta");
				controller.addPoints(-10);
				controller.addAnswer(false, answerField.getText(),
						getIdFromWord(input));
			}
		} else
			JOptionPane.showMessageDialog(null,
					"No se ha seleccionado palabra a responder");

	}

	/**
	 * Metodo que comprueba si se ha resuelto el crucigrama
	 * 
	 * @return True si esta completo, false en el otro caso
	 */
	private boolean isCompleted() {
		boolean ret = true;
		for (Object[] i : wordInfo) {
			Boolean correct = (Boolean) i[4];
			if (!correct)
				ret = false;

		}
		return ret;
	}

	/**
	 * Metodo que pone como correcta la palabra acertada por el usuario
	 * 
	 * @param input
	 */
	private void correctWord(String input) {

		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(input))
				i[4] = true;
		}
		answerField.setEditable(false);
		acceptBtn.setEnabled(false);

	}

	/**
	 * Metodo que devuelve el id del palabra seleccionada
	 * 
	 * @param word
	 *            La palabra seleccionada
	 * @return El id de la palabra
	 */
	private Integer getIdFromWord(String word) {
		Integer info = 0;
		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(word))
				info = (Integer) i[2];
		}
		return info;
	}

	/**
	 * Metodo que inicializa el controlador para el panel del crucigrama
	 * 
	 * @return El controlador que se utilizara
	 */
	private CrosswordPanelEventListener<Word> listenerInitialization() {
		return new CrosswordPanelEventListener<Word>() {
			public void wordSelected(CrosswordPanel<Word> source, Word newWord) {
				if (newWord != null) {
					String size = "";
					size += newWord.getWord().length() + " letras";
					wordLabel.setText(size);
					txt.setText(getDescriptionFromWord(newWord.getWord()));
					setImgFromWord(newWord.getWord());

					if (isResolved(newWord.getWord())) {
						answerField.setEditable(false);
						acceptBtn.setEnabled(false);
					} else {
						answerField.setEditable(true);
						acceptBtn.setEnabled(true);
					}
				}

				if (controller.isRequestHelped()) {
					sendFriendBtn.setEnabled(false);
					acceptBtn.setEnabled(true);
				} else if (controller.isRequest()) {
					acceptBtn.setEnabled(false);
					sendFriendBtn.setEnabled(true);
				}

			}

			public void cellSelected(CrosswordPanel<Word> source, Point newCell) {
			}

			public void deselected(CrosswordPanel<Word> source) {
			}
		};
	}

	/**
	 * Metodo que comprueba su una palabra esta resuelta
	 * 
	 * @param word
	 *            La palabra a comprobar
	 * @return True si esta resuelta, false en otro caso
	 */
	protected boolean isResolved(String word) {
		Boolean info = false;
		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(word))
				info = (Boolean) i[4];
		}
		return info;
	}

	/**
	 * Metodo que devuelve la descripcion de una palabra
	 * 
	 * @param word
	 *            La palabra seleccionada
	 * @return La informacion sobre la palabra
	 */
	protected String getDescriptionFromWord(String word) {
		String info = "";
		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(word))
				info = (String) i[1];
		}
		return info;
	}

	/**
	 * Metodo que comprueba los puntos asignados a una palabra
	 * 
	 * @param word
	 *            La palabra que se comprueba
	 * @return Los puntos asignados
	 */
	protected Integer getPointsFromWord(String word) {
		Integer info = 0;
		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(word))
				info = (Integer) i[3];
		}
		return info;
	}

	/**
	 * Metodo que carga la imagen asignada a una palabra
	 * 
	 * @param word
	 *            La palabra deseada
	 */
	protected void setImgFromWord(String word) {

		for (Object[] i : wordInfo) {
			String name = (String) i[0];
			if (name.equals(word)) {
				img.setImage(getImgFromWord((String) i[0]));
			}
		}

	}

	/**
	 * Metodo que consigue la imagen asignada a una palabra
	 * 
	 * @param name
	 *            El nombre de la palabra
	 * @return La imagen
	 */
	private Image getImgFromWord(String name) {
		return ArrayByteToImage(controller.getImageFromWord(name));

	}

	/**
	 * Metodo set del ViewPort
	 * 
	 * @param newPanel
	 *            El nuevo crucigrama
	 */
	public void setViewPort(CrosswordPanel<Word> newPanel) {
		panel = newPanel;
		jScrollPane.setViewportView(panel);
		panel.addEventListener(listenerInitialization());
		if (isCompleted()) {
			controller.resolved();
			sendFriendBtn.setEnabled(false);
		}
	}

	/**
	 * Metodo update
	 */
	@Override
	public void update(Observable o, Object arg) {
		String cadena = String.valueOf(arg);

		switch (cadena) {
		case "OPEN_CROSSWORD_FAILURE":
			JOptionPane.showMessageDialog(null,
					"Primero, tienes que seleccionar el crucigrama.");
			break;
		case "OPEN_CROSSWORD":
			acceptBtn.setEnabled(true);
			sendFriendBtn.setEnabled(true);
			answerField.setEditable(true);
			answerField.setText("");

			if (controller.isRequest()) {
				acceptBtn.setEnabled(false);
				sendFriendBtn.setEnabled(true);
			} else if (controller.isRequestHelped()) {
				acceptBtn.setEnabled(true);
				sendFriendBtn.setEnabled(false);
			}
			this.setTitle(controller.getCurrentCrosswordTitle());
			loadSolved();
			break;
		case "REQUEST_ADD":
			JOptionPane.showMessageDialog(null, "¡Petición añadida con exito!");
			break;
		case "REQUEST_ADD_FAILURE2":
			JOptionPane.showMessageDialog(null,
					"No eres amigo de este usuario o no existe.");
			break;
		}

	}

	/**
	 * Metodo que carga las palabras ya resueltas
	 */
	private void loadSolved() {
		for (Object[] i : wordInfo) {
			if ((boolean) i[4])
				showWord((String) i[0]);
		}

	}

	/**
	 * Metodo que hace aparecer en el crucigrama una palabra
	 * 
	 * @param word
	 *            La palabra a ver
	 */
	private void showWord(String word) {
		for (Word w : list)
			if (w.getWord().equalsIgnoreCase(word))
				panel.showWord(w);

	}

	/**
	 * Metodo set del atributo wordInfo
	 * 
	 * @param wordInfo
	 *            El nuevo wordInfo
	 */
	public void setWordInfo(List<Object[]> wordInfo) {
		this.wordInfo = wordInfo;

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
			ImageIcon ic = new ImageIcon("src/main/resources/noimagen.jpg");
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

}