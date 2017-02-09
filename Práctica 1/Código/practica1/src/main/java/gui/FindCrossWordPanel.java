package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.CrosswordController;

/**
 * Clase que representa la vista de la busqueda de crucigramas
 * 
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class FindCrossWordPanel extends JFrame implements Observer {

	private Container _panelPrincipal; // El contendor principal
	private JPanel _panelSup; // El panel superior
	private JTextField find; // Textfield donde se introducira la busqueda
	private JButton findCrosswordBtn; // Boton para iniciar la busqueda
	private JButton addCrosswordBtn; // Boton de añadir
	private JPanel _panelCentral; // El panel central
	private JScrollPane scrollPanel; // Panel donde estara contenida la lista de
	// crucigramas
	private JList<String> CrosswordList; // La lista donde se almacenaran los
	// crucigramas
	private DefaultListModel<String> model; // El modelo de la lista
	private CrosswordController controller; // El controlador de la aplicacion
	private String crosswordSelected; // El crucigrama seleccionado

	/**
	 * Constructor de la clase
	 * 
	 * @param controller
	 *            El controlador de la aplicacion
	 */
	public FindCrossWordPanel(CrosswordController controller) {
		super("Busqueda de crucigramas");
		this.controller = controller;
		controller.addObserver(this);

		buildUI();
	}

	/**
	 * Metodo de creacion de la interfaz
	 */
	private void buildUI() {
		_panelPrincipal = this.getContentPane();
		_panelPrincipal.setLayout(new BorderLayout());

		_panelSup = new JPanel(new FlowLayout());

		find = new JTextField();
		find.setPreferredSize(new Dimension(160, 20));
		findCrosswordBtn = new JButton("Búsqueda");
		findCrosswordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				model.clear();
				CrosswordList.clearSelection();
				List<Object> ids = controller.findCrosswordsByTitle(find
						.getText());
				for (int i = 0; i < ids.size(); i++)
					model.addElement((String) controller.getCrosswordTitle(ids
							.get(i)));

			}

		});

		addCrosswordBtn = new JButton("Añadir");
		addCrosswordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crosswordSelected = CrosswordList.getSelectedValue();
				if (crosswordSelected != null) {
					Object id = (controller
							.findCrosswordsByTitle(crosswordSelected).get(0));
					controller.addCrosswordToUser(id);
				} else {
					controller.addCrosswordToUser(null);
				}
				CrosswordList.clearSelection();
				crosswordSelected = "";
			}

		});

		_panelSup.add(find);
		_panelSup.add(findCrosswordBtn);
		_panelSup.add(addCrosswordBtn);

		_panelCentral = new JPanel();
		model = new DefaultListModel<String>();
		CrosswordList = new JList<String>(model);
		CrosswordList.setPreferredSize(new Dimension(300, 300));

		scrollPanel = new JScrollPane(CrosswordList);
		scrollPanel.setPreferredSize(new Dimension(300, 300));

		_panelCentral.add(scrollPanel);

		_panelPrincipal.add(_panelSup, BorderLayout.NORTH);
		_panelPrincipal.add(_panelCentral, BorderLayout.CENTER);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}

	/**
	 * Metodo update
	 */
	@Override
	public void update(Observable o, Object arg) {
		String cadena = String.valueOf(arg);
		switch (cadena) {
		case "HISTORIAL_ADD":
			this.setVisible(false);
			break;
		case "HISTORIAL_ADD_FAILURE":
			JOptionPane.showMessageDialog(null, "Crucigrama ya en historial");
			break;
		case "HISTORIAL_ADD_FAILURE2":
			JOptionPane
			.showMessageDialog(null,
					"Primero, tienes que seleccionar el crucigrama que quieres añadir.");
			break;

		}

	}

}