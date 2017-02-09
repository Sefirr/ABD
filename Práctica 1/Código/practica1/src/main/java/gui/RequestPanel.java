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

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CrosswordController;

/**
 * Clase que representa la vista del panel de peticiones
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class RequestPanel extends JPanel implements Observer {

	private JPanel _panelPrincipal; // Panel principal de la vista
	private JPanel _panelCentral; // Panel central
	private DefaultTableModel crosswordTableModel; // Modelo de la tabla de
	// peticiones
	private JTable crosswordTable; // Tabla de las peticiones
	private JPanel _actionPanel; // Panel que contiene los botones
	private JButton openCrosswordBtn; // Boton de abrir crucigrama
	private JButton deleteRequestBtn; // Boton de eliminar peticion
	private Integer crosswordId; // Id del crucigrama seleccionado
	private String helped; // Nick del usuario al que se va a ayudar
	private CrosswordController controller; // Controlador de la aplicacion

	/**
	 * Cosntructor de la clase
	 * 
	 * @param cont
	 *            Controlador de la aplicacion
	 */
	public RequestPanel(CrosswordController cont) {
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

		_panelCentral = new JPanel();

		crosswordTableModel = new DefaultTableModel(new Object[] { "Usuario",
		"Crucigrama" }, 0);

		crosswordTable = new JTable(crosswordTableModel) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			};
		};
		crosswordTable.setRowSelectionAllowed(true);
		crosswordTable.setEnabled(true);

		crosswordTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					String selectedCrosswordTitle = (String) crosswordTable
							.getValueAt(crosswordTable.getSelectedRow(), 1);
					helped = (String) crosswordTable.getValueAt(
							crosswordTable.getSelectedRow(), 0);
					crosswordId = (Integer) controller.findCrosswordsByTitle(
							selectedCrosswordTitle).get(0);
				} catch (ClassCastException cce) {
					cce.printStackTrace();
				}
			}
		});

		JScrollPane crosswordListScrollable = new JScrollPane(crosswordTable);
		crosswordListScrollable.setPreferredSize(new Dimension(375, 275));

		_panelCentral.add(crosswordListScrollable);

		_actionPanel = new JPanel();
		_actionPanel.setLayout(new FlowLayout());

		openCrosswordBtn = new JButton("Abrir crucigrama");

		openCrosswordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crosswordTable.clearSelection();
				controller.setCrosswordHelp(crosswordId, helped);
			}
		});

		deleteRequestBtn = new JButton("Descartar petición");

		deleteRequestBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (crosswordTable.getSelectedRow() >= 0) {
					String selectedCrosswordTitle = (String) crosswordTable
							.getValueAt(crosswordTable.getSelectedRow(), 1);
					helped = (String) crosswordTable.getValueAt(
							crosswordTable.getSelectedRow(), 0);
					crosswordId = (Integer) controller.findCrosswordsByTitle(
							selectedCrosswordTitle).get(0);

					if (helped.equals(controller.getUser()))
						controller.deleteRequest(controller.getUser(),
								crosswordId);
					else
						controller.deleteRequest(helped, crosswordId);
				} else
					JOptionPane.showMessageDialog(null,
							"No se ha seleccionado ninguna peticion.");
			}

		});

		_actionPanel.add(openCrosswordBtn);
		_actionPanel.add(deleteRequestBtn);

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
			refreshRequest();
			break;
		case "REQUEST_ADD":
			refreshRequest();
			break;
		case "REFRESH_REQUEST":
			refreshRequest();
			break;
		}
	}

	/**
	 * Metodo que refresca la vista
	 */
	private void refreshRequest() {
		crosswordTableModel.setRowCount(0);

		List<Object[]> request = controller.listRequest();
		Object[] rowdata = new Object[2];
		for (int i = 0; i < request.size(); i++) {
			rowdata[0] = request.get(i)[0];
			rowdata[1] = request.get(i)[1];
			if (!rowdata[0].equals(controller.getUser()))
				crosswordTableModel.addRow(rowdata);
		}

		crosswordTableModel.fireTableDataChanged();
		crosswordTable.setModel(crosswordTableModel);
	}

}
