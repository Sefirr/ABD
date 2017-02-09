package pr3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;


public class AppPanel extends JFrame {

	private static final long serialVersionUID = 1L;

	private Container _panelPrincipal;
	
	private JPanel _panelSup;
	
	private JTabbedPane _panelCentral;
	
	private JComboBox<Integer> anyos;
	
	private DefaultTableModel model;
	private JTable table;
	private JTextPane text;
	private DAO dao;
	
	public AppPanel(DAO dao) {
		super("Practica 3");
		this.dao=dao;
		buildGUI();
	}

	private void buildGUI() {
	
		


		_panelPrincipal = new JPanel();
		_panelPrincipal.setLayout(new BorderLayout());
		
		_panelSup = new JPanel(new GridLayout(1,1));
		
		anyos=new JComboBox<Integer>();
		anyos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cambiarText(dao.informacionAnyo((Integer) anyos.getSelectedItem()));
				cambiarResultados(dao.resultadosAnyo((Integer) anyos.getSelectedItem()));
				
			}
		});
		model= new DefaultTableModel(new Object[]{"Puesto","Pais","Artista","Cancion","Puntos"}, 0);
		table = new JTable(model) {
        	/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            };
		 };
		
		_panelSup.add(anyos);
		_panelCentral = new JTabbedPane();
		JPanel resultados= new JPanel(new GridLayout(1,1));
		JPanel informacion= new JPanel(new GridLayout(1,1));
		text=new JTextPane();
		text.setContentType("text/html");
		text.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		JScrollPane scrollPane2 = new JScrollPane(text);
		
		resultados.add(scrollPane,BorderLayout.CENTER);
		informacion.add(scrollPane2,BorderLayout.CENTER);
		
		_panelCentral.addTab("Informacion", null, informacion,
		                  "Informacion de la edicion seleccionada.");
		
		_panelCentral.addTab("Resultados", null, resultados,
		                  "Resultados de la edicion seleccionada");


		
		_panelPrincipal.add(_panelSup, BorderLayout.NORTH);		
		_panelPrincipal.add(_panelCentral, BorderLayout.CENTER);
		
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(_panelPrincipal);	
		
	}
	
	public void anadirAnyos(List<Integer> anyos)
	{
		for(int i=0;i<anyos.size();i++)
			this.anyos.addItem(anyos.get(i));
			
	}
	
	public void cambiarResultados(List<String[]> l)
	{
		int a=model.getRowCount();
		for(int i=0;i<a;i++)
			model.removeRow(0);
		Object[] data = new Object[5];
		
		for(int i=0;i<l.size();i++)
		{
			data[0]=i+1;
			data[1]=l.get(i)[0];
			data[2]=l.get(i)[1];
			data[3]=l.get(i)[2];
			data[4]=l.get(i)[3];
			model.addRow(data);
		}
		
	}
	
	public void cambiarText(String s)
	{
		
		text.setText(s);
		
	}
	
	
}	