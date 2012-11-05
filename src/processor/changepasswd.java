package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class changepasswd extends AbstractProcessor{

	public void processing(RcvData rd) {
		// rcvd changepasswd^pid^aid^passwd^newpasswd
		//			0		 1	 2		3		4
		// send changepasswd^succeeded/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		try{
		if( CommonMethods.checkPasswd(data[2], data[3]) )
		{
			String SQL_1 = "SELECT UID FROM SYSTEM.ACCOUNT WHERE AID='" + data[2] + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			if( rss.next() && rss.getString("UID").equals(data[1]) )
			{
				String SQL_up = "UPDATE SYSTEM.ACCOUNT SET PASSWD='" + data[4] + "' WHERE AID='" + data[2] + "';";
				if( UpdateProcessor.update(SQL_up) != 0 )
					isSuccess = true;		
			}
			else
			{
				String SQL_up  = "UPDATE SYSTEM.OPERATOR SET PASSWD='" + 
						data[4] + "' WHERE AID='" +data[2] + "' AND UID='" + data[1] + "';";
				if( UpdateProcessor.update(SQL_up) !=0 )
					isSuccess = true;
			}
		}}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		
		String rs = "";
		if( isSuccess )
		{
			rs = "success";
			Log.log(rd.getJobNumber(), data[2], data[0], "", 0, 0, 0);
		}
		else
			rs = "fail";
		SendDataList.getInstance().add(
				new SendData( rd, head+ rs));
		
		}
	}
