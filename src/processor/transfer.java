package processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class transfer extends AbstractProcessor{

	public void processing(RcvData rd) {
	//			0			1	2	3		4	5		6		7
		// rcvd transfer^p_id,a_id,passwd,name2,sum,target_a_id,target_name
		// send data[0]^failed / balance
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		String resultString = "";
		boolean isSuccess = false;		
		double from_balance = 0;
		double to_balance = 0;
		double sum = Double.parseDouble(data[5]);
		try{
		if( CommonMethods.checkPasswd(data[2], data[3]) )
		{
			String SQL_from = "SELECT * FORM SYSTEM.ACCOUNT,SYSTEM.USER WHERE " +
					"ACCOUNT.UID=USER.UID AND ACCOUNT.AID='" + data[2] + "';";
			String SQL_to = "SELECT * FORM SYSTEM.ACCOUNT,SYSTEM.USER WHERE " +
					"ACCOUNT.UID=USER.UID ADN ACCOUNT.AID='" + data[6] + "';";
			ResultSet rss_from = QueryProcessor.query(SQL_from);
			ResultSet rss_to = QueryProcessor.query(SQL_to);
			if( !rss_from.next() || !rss_to.next() )
				throw new Exception("failed");
			//check if the transfer is legal.
			//normal user cannot transfer out
			//transfer must be done between persional users or enterprise users
			//balance enough
			if( 
//				rss_from.getString("UID").equals(data[1])	&&
//				rss_from.getString("NAME").equals(data[4])	&&
				rss_to.getString("NAME").equals(data[7]) 	&&
				( (rss_from.getString("TYPE").equals("n") && rss_from.getString("UID").equals(rss_to.getString("UID"))) ||
				  (rss_from.getString("TYPE").equals("e") && rss_to.getString("TYPE").equals("e")) ||
				  (rss_from.getString("TYPE").equals("v") && !rss_to.getString("TYPE").equals("e"))
				)	&&
				(isBalanceEnoughForWithdrawal(rss_from,sum))
			  )
			{
				from_balance = rss_from.getDouble("BALANCE") - sum;
				to_balance = rss_to.getDouble("BALANCE") + sum;
				String SQL_update_from = "UPDATE SYSTEM.ACCOUNT SET BALANCE='" + from_balance + 
						"'WHERE AID='" + rss_from.getString("AID") + "';";
				String SQL_update_to = "UPDATE SYSTEM.ACCOUNT SET BALANCE='" + to_balance +
						"'WHERE AID='" + rss_to.getString("AID") + "';";
				if( UpdateProcessor.update(SQL_update_from) != 0 && UpdateProcessor.update(SQL_update_to) != 0 )
					isSuccess = true;
			}
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess )
		{
			resultString = "" + from_balance;
			Log.log(rd.getJobNumber(),data[2],data[0],"target:" + data[6] + resultString, 0, 0, 0);
		}
		else
			resultString = "failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + resultString ));
		
	}

	private boolean isBalanceEnoughForWithdrawal(ResultSet rss_from, double sum) throws Exception {
		// TODO Auto-generated method stub
		String user_type = rss_from.getString("TYPE");
		double balance = rss_from.getDouble("BALANCE");
		if( user_type.equals("n") )
			return (balance >= sum);
		if( user_type.equals("v") )
			return (balance >= sum-100000);
		if( user_type.equals("e") )
			return (balance >= sum && CommonMethods.getTotalBalanceByUid(rss_from.getString("UID")) >= sum+10000 );
		return false;
	}

}
