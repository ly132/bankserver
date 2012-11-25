package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;

public class acclogin extends AbstractProcessor {

	public void processing(RcvData rd) {
		// jobnum^acclogin^aid^pwd
		//	rcvd login^[01]
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
			if( CommonMethods.checkPasswd(data[1], data[2]) )
				isSuccess = true;
			else
				isSuccess = false;
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
			rs = e.getMessage();
		}
		if( isSuccess )
			rs = "1";
		else
			rs = "0";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}

}
