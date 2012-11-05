package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class addattorney extends AbstractProcessor{

	public void processing(RcvData rd) {
		//	addattorney^pid^aid^passwd^pid^passwd
		//		0		1	2	3		4	5
		//	addattorney^success/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
		if( CommonMethods.getUserTypeByAid(data[2]).equals("e") && CommonMethods.checkUid(data[1], data[2]) && CommonMethods.checkPasswd(data[2], data[3]) )
		{
			String SQL_pid = "SELECT * FROM SYSTEM.USER WHERE UID='" + data[4] + "';";
			ResultSet pidResult = QueryProcessor.query(SQL_pid);
			if( ! pidResult.next() )
			{
				//Condition 1, pID not exits.
				SendDataList.getInstance().add(
						new SendData( rd, head + "customer not esixt" ));
				return;
			}
			String SQL_add = "INSERT INTO SYSTEM.OPERATOR (AID,UID,PASSWD) VALUES ('" 
							+ data[2] + "', '" + data[4] + "', '" + data[5] + "');";
			if( UpdateProcessor.update(SQL_add) != 0 )
				isSuccess = true;
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess ){
			rs = "success";
			Log.log(rd.getJobNumber(), data[2], data[0], data[4], 0, 0, 0);
		}
		else
			rs = "failed";
		SendDataList.getInstance().add(
				new SendData(rd,head+rs));
	}

}
