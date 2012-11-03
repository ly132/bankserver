package processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import server.Server;

import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class deposit extends AbstractProcessor{

	@Override
	public void processing(RcvData rd) {
		// TODO Auto-generated method stub
		//	rcv			2^a_id^passwd^balance
		//	send		2^balance or 2^failed
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		double balance = -1;
		boolean isSuccess = false;
		if( CommonMethods.checkPasswd(data[1], data[2]) )
		{
			try {
				balance = CommonMethods.getBalance(data[1]);
				balance += Double.parseDouble(data[3]);
				String SQL_updateBlanace = "UPDATE SYSTEM.ACCOUNT SET BALANCE='" + 
						balance + "' WHERE AID='" + data[1] + "';";
				if( UpdateProcessor.update(SQL_updateBlanace) != 0 )
					isSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String send = "";
		if( isSuccess )
		{
			send += balance;
			Log.log( rd.getJobNumber(), data[1], data[0], send );
		}
		else
			send += "failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + send ));
	}

}
