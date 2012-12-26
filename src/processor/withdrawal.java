package processor;

import java.sql.SQLException;

import server.Server;

import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.UpdateProcessor;

public class withdrawal extends AbstractProcessor{

	public void processing(RcvData rd) {
		//	rcvd	withdrawal^a_id^passwd^balance
		//	send	3^balance or 3^failed
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		double sum = Double.parseDouble(data[3]);
		double balance1 = -1;
		double total_balance = -1;
		boolean isSuccess = false;
		double baseLine1 = 0;
		double baseLine2 = 0;
		try{
		if( CommonMethods.checkPasswd(data[1], data[2]) )
		{
			String user_type = CommonMethods.getUserTypeByAid( data[1] );
			total_balance = balance1 = CommonMethods.getBalance(data[1]);
			if( user_type.equals("v") )
				baseLine2 = baseLine1 = -100000;
			if( user_type.equals("e") )
			{
				baseLine2 = 10000;
				String uid = CommonMethods.getUidByAid( data[1] );
				total_balance = CommonMethods.getTotalBalanceByUid( uid );
			}
			if( ((balance1-sum) >= baseLine1) && ((total_balance-sum) >= baseLine2)  )
			{
				String SQL_updateBlanace = "UPDATE SYSTEM.ACCOUNT SET BALANCE='" + 
						(balance1-sum) + "' WHERE AID='" + data[1] + "';";
				if( UpdateProcessor.update(SQL_updateBlanace) != 0 )
					isSuccess = true;			
			}
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		String rs;
		if( isSuccess )
		{
			rs = "Withdrawal Success\nCurrent Balance:" + (balance1-sum);
			Log.log( rd.getJobNumber(), data[1], data[0], "", 0, sum, (balance1-sum) );
		}
		else
			rs = "Withdrawal Failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}
}
