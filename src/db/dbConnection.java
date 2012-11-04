package db;
import	java.sql.*;

public class dbConnection {
	
	Connection con = null;
	String dbURL = "jdbc:mysql://172.18.216.170:3306/bank";
	String user = "root";
	String passwd = "root";
	
	public dbConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection connect(){
		try {
			con = DriverManager.getConnection(dbURL,user,passwd);
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
