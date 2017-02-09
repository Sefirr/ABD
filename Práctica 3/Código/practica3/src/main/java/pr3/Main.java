package pr3;

public class Main {

	public static void main(String[] args) {
		AppPanel app = null ;
		
		
		DAO dao;
		try {
			dao = new DAO();
			app = new AppPanel(dao);
			app.cambiarResultados(dao.resultadosAnyo(2015));
			app.cambiarText(dao.informacionAnyo(2015));
			app.anadirAnyos(dao.anyosConsulta());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	
	app.setVisible(true);
	}

}
