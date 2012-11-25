package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {

	public static void main(String[] args){
		
		Connection con = null;
		String dbURL = "jdbc:mysql://172.18.216.170:3306/System";
		String user = "root";
		String passwd = "root";
		
		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(dbURL,user,passwd);
			} catch( Exception e)
			{
				e.printStackTrace();
			}
	}
}
