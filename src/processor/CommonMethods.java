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

	public static String getUserTypeByAid(String aid) throws Exception{
		String SQL = "SELECT TYPE FROM SYSTEM.TYPE, SYSTEM.USER WHERE USER.UID=ACCOUNT.UID AND ACCOUNT.AID='" + aid + "';";
		ResultSet rss = QueryProcessor.query(SQL);
		if( rss.next() )
			return rss.getString("TYPE");
		else
			throw new Exception("failed");
	}

	public static String getUidByAid(String aid) throws Exception {
		String SQL = "SELECT UID FORM SYSTEM.ACCOUNT WHERE AID='" + aid + "';";
		ResultSet rss = QueryProcessor.query(SQL);
		if( rss.next() )
			return rss.getString("UID");
		else
			throw new Exception("failed");
	}
	

	public static double getTotalBalanceByUid(String uid) throws Exception {
		
		double total_balance = 0;
		try{
			String SQL = "SELECT BALANCE FORM SYSTEM.OPERATOR, SYSTEM.ACCOUNT WHERE OPERATOR.UID='" 
							+ uid + "' AND ACCOUNT.AID=USER.AID;";
			ResultSet rss = QueryProcessor.query(SQL);
			while( rss.next() )
				total_balance += rss.getDouble("BALANCE");
		}catch( Exception e )
		{
			throw e;
		}
		return total_balance;
	}

	public static boolean checkUid(String uid, String aid) throws Exception {
		String SQL_1 = "SELECT * FROM SYSTEM.ACCOUNT,SYSTEM.USER WHERE ACCOUNT.UID=USER.UID AND AID='" + aid +  "';";
		ResultSet rss = QueryProcessor.query(SQL_1);
		if( rss.next() )
		{
			String user_type = rss.getString("TYPE");
			if( user_type.equals("e") )
			{
				if( rss.getString("UID").equals(uid) )
					return true;
				else
				{
					SQL_1 = "SELECT UID FROM SYSTEM.OPERATOR WHERE AID='" + aid + "';";
					ResultSet rss2 = QueryProcessor.query(SQL_1);
					while(rss2.next())
						if( rss2.getString("UID").equals(uid) )
							return true;
				}
			}
			else
			{
				if( rss.getString("UID").equals(uid) )
					return true;
			}
		}
		return false;			
	}
}
