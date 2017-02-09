package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.CrosswordController;

/**
 * Clase que representa la vista de la seccion de amigos
 * 
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class FriendsPanel extends JPanel implements Observer {

	private JPanel _panelPrincipal; // Contenedor principal
	private JPanel _panelCentral; // Panel central de la vista
	private DefaultListModel<String> model; // Modelo de la lista de amigos
	private JList<String> friendList; // La lista de amigos
	private JScrollPane scrollPanel; // Scroller donde se inserta la lista
	private JPanel _actionPanel; // Panel que contiene los botones
	private JButton addFriendBtn; // Boton de agregar amigo
	private JButton deleteFriendBtn; // Boton de borrar amigo
	private CrosswordController controller; // Controlador de la aplicacion
	private String selectedFriendNick; // El nick del amigo seleccionado

	/**
	 * Constructor de la clase
	 * 
	 * @param controller
	 *            El controlador de la aplicacion
	 */
	public FriendsPanel(CrosswordController controller) {
		this.controller = controller;
		controller.addObserver(this);
		buildUI();
	}

	/**
	 * Metodo de creacion de la interfaz
	 */
	private void buildUI() {
		_panelPrincipal = new JPanel();
		_panelPrincipal.setLayout(new BorderLayout());

		_panelCentral = new JPanel();

		model = new DefaultListModel<String>();
		friendList = new JList<String>(model);
		friendList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					selectedFriendNick = (String) friendList.getSelectedValue();
				} catch (ClassCastException cce) {
					cce.printStackTrace();
				}
			}
		});

		scrollPanel = new JScrollPane(friendList);
		scrollPanel.setPreferredSize(new Dimension(375, 275));

		_panelCentral.add(scrollPanel);

		_actionPanel = new JPanel();
		_actionPanel.setLayout(new FlowLayout());

		addFriendBtn = new JButton("Añadir amigo");

		addFriendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String friendName = JOptionPane
						.showInputDialog("Introduzca el nombre de su amigo:");
				if (friendName != null && !friendName.equalsIgnoreCase(""))
					controller.addFriend(friendName);
				else
					JOptionPane.showMessageDialog(null,
							"No se ha introducido un usuario.");

			}
		});

		deleteFriendBtn = new JButton("Eliminar");

		deleteFriendBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.deleteFriend(selectedFriendNick);
				selectedFriendNick = "";
			}

		});

		_actionPanel.add(addFriendBtn);
		_actionPanel.add(deleteFriendBtn);

		_panelPrincipal.add(_panelCentral, BorderLayout.CENTER);
		_panelPrincipal.add(_actionPanel, BorderLayout.SOUTH);

		this.add(_panelPrincipal);

		this.setVisible(true);

	}

	/**
	 * Metodo update
	 */
	@Override
	public void update(Observable o, Object arg) {
		String cadena = String.valueOf(arg);
		switch (cadena) {
		case "LOGIN":
			refreshFriendList();
			break;
		case "FRIEND_ADD":
			JOptionPane.showMessageDialog(null, "¡Amigo añadido con éxito!");
			refreshFriendList();
			break;
		case "FRIEND_ADD_FAILURE":
			JOptionPane.showMessageDialog(null,
					"Este usuario ya es tu amigo o no existe.");
			break;
		case "FRIEND_ADD_FAILURE2":
			JOptionPane.showMessageDialog(null, "Este usuario no existe.");
			break;
		case "FRIEND_DELETE":
			JOptionPane.showMessageDialog(null, "¡Amigo eliminado con éxito!");
			refreshFriendList();
			break;
		case "FRIEND_DELETE_FAILURE":
			JOptionPane
			.showMessageDialog(null,
					"Primero, tienes que seleccionar el amigo que quieres añadir.");
			break;
		}
	}

	/**
	 * Metodo que refresca la vista
	 */
	private void refreshFriendList() {
		model.clear();
		friendList.clearSelection();
		List<Object[]> friends = controller.listFriends();
		for (int i = 0; i < friends.size(); i++)
			if (friends.get(i)[0].equals(controller.getUser()))
				model.addElement((String) friends.get(i)[1]);
			else
				model.addElement((String) friends.get(i)[0]);
	}

}
