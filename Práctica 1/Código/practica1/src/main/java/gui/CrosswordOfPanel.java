package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.CrosswordController;
import es.ucm.abd.crossword.CrosswordPanel;

/**
 * Clase que representa la vista en la que se pueden observar los crucigramas
 * activos del usuario
 * 
 * @author Grupo 502
 *
 */
@SuppressWarnings("serial")
public class CrosswordOfPanel extends JPanel implements Observer {

	private JPanel _panelPrincipal; // Panel contenedor
	private JPanel _panelCentral; // Panel que contiene la lista de crucigramas
	private JTable crosswordTable; // Tabla en la que se añaden los crucigramas
	// del usuario
	private DefaultTableModel crosswordTableModel; // Modelo de la tabla
	private JPanel _actionPanel; // Panel que contiene los botones
	private JButton openCrosswordBtn; // Boton de abrir crucigrama
	private JButton findCrosswordBtn; // Boton para acceder a la busqueda
	private Integer crosswordId; // Id del crucigrama que se seleccione
	private FindCrossWordPanel findCrossword; // Panel de busqueda de
	// crucigramas
	private CrossPanel crosswordPanel; // Panel de resolucion de crucigramas
	private CrosswordController controller; // Controlador de la aplicacion

	/**
	 * Constructor de la clase
	 * 
	 * @param cont
	 *            El controlador de la aplicación
	 */
	public CrosswordOfPanel(CrosswordController cont) {

		findCrossword = new FindCrossWordPanel(cont);
		crosswordPanel = new CrossPanel(cont);

		this.controller = cont;
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

		crosswordTableModel = new DefaultTableModel(new Object[] { "Título",
		"Fecha" }, 0);

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
							.getValueAt(crosswordTable.getSelectedRow(), 0);
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
				controller.reset();
				controller.setCrossword(crosswordId);

			}
		});

		findCrosswordBtn = new JButton("Búsqueda de crucigrama");

		findCrosswordBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				findCrossword.setVisible(true);

			}

		});

		_actionPanel.add(openCrosswordBtn);
		_actionPanel.add(findCrosswordBtn);

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
		case "HISTORIAL_ADD":
			refreshHistorial();
			break;
		case "REFRESH_HISTORIAL":
			refreshHistorial();
			break;
		case "OPEN_CROSSWORD":
			crosswordId = null;
			refreshCrosswordPanel();
			break;
		}
	}

	/**
	 * Metodo que refresca el panel de crucigrama segun la seleccion
	 */
	private void refreshCrosswordPanel() {
		List<Word> lista = new LinkedList<Word>();
		List<Object[]> wordList = controller.getWordOf();
		List<Object[]> wordInfo = new LinkedList<Object[]>();
		for (Object[] i : wordList) {
			String orientacion = String.valueOf(i[0]);
			String name = String.valueOf(i[3]);
			Integer points = (Integer) (i[6]);
			Integer id = (Integer) (i[7]);
			name = name.toUpperCase(getDefaultLocale());
			String description = String.valueOf(i[4]);

			if (orientacion.equals("H"))
				lista.add(new Word((Integer) i[1], (Integer) i[2], name, true));
			else
				lista.add(new Word((Integer) i[1], (Integer) i[2], name, false));
			if (!controller.isRequestHelped())
				wordInfo.add(new Object[] { name, description, id, points,
						controller.isResolved(id) });
			else
				wordInfo.add(new Object[] { name, description, id, points,
						controller.isResolvedHelped(id) });
		}

		// Creamos el CrosswordPanel a partir de la lista.
		CrosswordPanel<Word> panel = new CrosswordPanel<Word>(null, lista);
		crosswordPanel.setWordInfo(wordInfo);
		crosswordPanel.setViewPort(panel);
		crosswordPanel.setList(lista);
		crosswordPanel.setVisible(true);

		crosswordTableModel.fireTableDataChanged();
		crosswordTable.setModel(crosswordTableModel);

	}

	/**
	 * Metodo que actualiza el historial del usuario
	 */
	private void refreshHistorial() {
		crosswordTableModel.setRowCount(0);

		List<Object> ids = controller.getCrosswordsOf();
		Object[] rowdata = new Object[2];
		for (int i = 0; i < ids.size(); i++) {
			rowdata[0] = controller.getCrosswordTitle(ids.get(i));
			rowdata[1] = (DateFormat.getDateInstance(DateFormat.SHORT))
					.format(controller.getCrosswordDate(ids.get(i)));
			crosswordTableModel.addRow(rowdata);
		}

		crosswordTableModel.fireTableDataChanged();
		crosswordTable.setModel(crosswordTableModel);
	}

}
