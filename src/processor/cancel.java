package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class cancel extends AbstractProcessor{

	public void processing(RcvData rd) {
		
		// rcvd cancel^aid
		//  		0	1	2	3	
		// send cancel^succeeded/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		String rs = "";
		double balance = 0;
		try{
	//	if( CommonMethods.checkPasswd(data[2], data[3]) && CommonMethods.checkUid(data[1],data[2]) )
//		{
			String SQL_1 = "SELECT USER.TYPE,BALANCE FROM SYSTEM.USER, SYSTEM.ACCOUNT WHERE " +
					"ACCOUNT.UID=USER.UID AND ACCOUNT.AID='" + data[1] + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			if( rss.next() )
			{
				String type = rss.getString("USER.TYPE");
				balance = rss.getDouble("BALANCE");
				String SQL_del = "DELETE FROM SYSTEM.ACCOUNT WHERE AID='" + data[1] + "';";
				if( balance >=0 && UpdateProcessor.update(SQL_del) != 0 ){
					if( type.equals("e") )
					{
						SQL_del = "DELETE FROM SYSTEM.OPERATOR WHERE AID='" + data[1] + "';";
						if( UpdateProcessor.update(SQL_del) != 0 )
							isSuccess = true;
					}
					else
						isSuccess = true;
				}
			}
	//	}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess )
		{
			rs = "Cancel Account " + data[1] + " Success\nWithdrawal Sum is " + balance;
			Log.log(rd.getJobNumber(),data[1],data[0],"", 0, balance, balance);
		}
		else
			rs = "Cancel Account Fail";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}
}
