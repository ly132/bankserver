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
		// rcvd changepasswd^pid^aid^newpasswd
		//			0		 1	  2		3
		// send changepasswd^succeeded/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		try{
			String SQL_up = "UPDATE SYSTEM.ACCOUNT SET PASSWD='" + data[3] + "' WHERE AID='" + data[2] + "';";
			if( UpdateProcessor.update(SQL_up) != 0 )
				isSuccess = true;		
			SQL_up  = "UPDATE SYSTEM.OPERATOR SET PASSWD='" + 
					data[3] + "' WHERE AID='" +data[2] + "' AND UID='" + data[1] + "';";
			if( UpdateProcessor.update(SQL_up) !=0 )
				isSuccess = true;
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		
		String rs = "";
		if( isSuccess )
		{
			rs = "Change Password Success";
			Log.log(rd.getJobNumber(), data[2], data[0], "", 0, 0, 0);
		}
		else
			rs = "Change Password Fail";
		SendDataList.getInstance().add(
				new SendData( rd, head+ rs));
		
		}
	}
