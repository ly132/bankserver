package db;
import	java.sql.*;

public class db2Connection {
	
	Connection con = null;
	String db2URL = "jdbc:db2:STALK";
	
	public db2Connection(){
		try {
			Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection connect(){
		try {
			con = DriverManager.getConnection(db2URL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	
	public void close(){
		try {
			if( con != null )
				con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
