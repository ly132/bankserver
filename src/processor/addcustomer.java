package processor;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.UpdateProcessor;

public class addcustomer extends AbstractProcessor{

	public void processing(RcvData rd) {
		
		// rcvd addcustomer^id^name^n/v/e
		// send addcustomer^succeeded/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		String rs = "";
		try{
			String SQL_insert = "INSERT INTO SYSTEM.USER (UID,NAME,TYPE) VALUES ('" + 
					data[1] + "', '" + data[2] + "', '" + data[3] + "');";
			if( UpdateProcessor.update(SQL_insert) != 0 )
				isSuccess = true;
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess )
		{
			rs = "Add Customer " + data[1] + " Success";
			Log.log(rd.getJobNumber(),"",data[0],data[1], 0, 0, 0);
		}
		else
			rs = "Add Customer " + data[1] + " Fail";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}

}
