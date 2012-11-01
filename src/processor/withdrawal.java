package processor;

import java.sql.SQLException;

import server.Server;

import data.RcvData;
import data.SendData;
import data.SendDataList;

public class withdrawal extends AbstractProcessor{

	public void processing(RcvData rd) {
		//	rcvd	3^a_id^passwd^balance
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
				total_balance = CommonMethods.getWholeBalanceByUid( uid );
			}
			if( ((balance1-sum) > baseLine1) && ((total_balance-sum) > baseLine2)  )
				isSuccess = true;			
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		String rs;
		if( isSuccess )
			rs = head + (balance1-sum);
		else
			rs = head + "failed";
		SendDataList.getInstance().add(
				new SendData( rd, rs ));
	}
}
