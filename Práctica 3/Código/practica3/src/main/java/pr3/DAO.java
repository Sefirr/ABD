package pr3;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQItem;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.w3c.dom.NamedNodeMap;

import net.xqj.exist.ExistXQDataSource;

public class DAO {
	
	private XQDataSource xqs;
	
	
	/**
	 * Aqui se debe inicializar el pool de conexiones, mediante
	 * la creacion de un XQDataSource, que deberá ser asignado a
	 * la variable xqs.
	 */
	public DAO(){
		xqs = new ExistXQDataSource();
		try {
			xqs.setProperty("serverName", "localhost");
			xqs.setProperty("port", "8899");
			xqs.setProperty("user", "admin");
			xqs.setProperty("password", "eXist");
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Integer> anyosConsulta() {	
		List<Integer> ret = new LinkedList<Integer>();
		InputStream is = getClass().getResourceAsStream("/Eurovision1.xquery");
		XQConnection con;
		try {
			con = xqs.getConnection();
		XQPreparedExpression exp = con.prepareExpression(is);
		XQResultSequence rs = exp.executeQuery();
		while (rs.next()) {
			String s=rs.getItemAsString(null);
			int number=Integer.parseInt(s);
			ret.add(number);
			}
			rs.close();
			exp.close();
			con.close();
	} catch (XQException e) {
			
			e.printStackTrace();
		}
		
		return ret;
	
	}
	
	public List<String[]> resultadosAnyo(int anyo) {	
		InputStream is = getClass().getResourceAsStream("/Eurovision2.xquery");
		List<String[]> ret = new LinkedList<String[]>();
		XQConnection con;
		try {
			con = xqs.getConnection();
		XQPreparedExpression exp = con.prepareExpression(is);
		exp.bindInt(new QName("anyo"), (Integer) anyo, null);
		XQResultSequence rs = exp.executeQuery();
		while (rs.next()) {
			XQItem i=rs.getItem();
			NamedNodeMap m=i.getNode().getAttributes();
			String[] s = new String[4];
			s[0]=m.getNamedItem("pais").getNodeValue();
			s[1]=m.getNamedItem("artista").getNodeValue();
			s[2]=m.getNamedItem("cancion").getNodeValue();
			s[3]=m.getNamedItem("puntos").getNodeValue();
			ret.add(s);
			}
			rs.close();
			exp.close();
			con.close();
	} catch (XQException e) {
			
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public String informacionAnyo(int anyo)
	{	String ret ="";
		InputStream is = getClass().getResourceAsStream("/Eurovision3.xquery");
		try {
			XQConnection con = xqs.getConnection();
		XQPreparedExpression exp = con.prepareExpression(is);
		exp.bindInt(new QName("anyo"), anyo, null);
		XQResultSequence rs = exp.executeQuery();
		while (rs.next()) {
			ret=rs.getItemAsString(null);
			}
			rs.close();
			exp.close();
			con.close();
	} catch (XQException e) {
			
			e.printStackTrace();
		}
		
		return ret;
	
	}
	

}
