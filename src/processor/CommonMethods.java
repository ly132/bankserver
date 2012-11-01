package processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.QueryProcessor;

public class CommonMethods {

	static boolean checkPasswd( String aid, String passwd )
	{
		String SQL_passwd = "SELECT PASSWD FROM SYSTEM.ACCOUNT WHERE AID='" + aid + "';";
		ResultSet rss = QueryProcessor.query(SQL_passwd);
		try {
			if( rss.next() && rss.getString("PASSWD").equals(passwd) )
				return true;
			SQL_passwd = "SELECT PASSWD FROM SYSTEM.OPERATOR WHERE AID='" + aid + "';";
			rss = QueryProcessor.query(SQL_passwd);
			while( rss.next() )
				if( rss.getString("PASSWD").equals(passwd) )
					return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
		return false;
	}
	
	static double getBalance( String aid ) throws Exception
	{
		String SQL_getBalance = "SELECT BALANCE FORM SYSTEM.ACCOUNT WHERE AID=" + aid + "';";
		ResultSet rss = QueryProcessor.query(SQL_getBalance);
		if( rss.next() )
			return rss.getDouble("BALANCE");
		else
			throw new Exception("failed");
	}
}
