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
		//	addattorney^pid^aid^pid^passwd
		//		0		1	2	3		4	5
		//	addattorney^success/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
		String SQL_1 = "SELECT UID FROM SYSTEM.ACCOUNT WHERE AID='" + data[2] + "';";
		ResultSet SQL_1_Result = QueryProcessor.query(SQL_1);
		if( SQL_1_Result.next() && SQL_1_Result.getString("UID").equals(data[1]) )
		{
		if( CommonMethods.getUserTypeByAid(data[2]).equals("e") )
		{
			String SQL_pid = "SELECT * FROM SYSTEM.USER WHERE UID='" + data[3] + "';";
			ResultSet pidResult = QueryProcessor.query(SQL_pid);
			if( ! pidResult.next() )
			{
				//Condition 1, pID not exits.
				SendDataList.getInstance().add(
						new SendData( rd, head + "Attorney Information Not Exist\nAdd Customer First" ));
				return;
			}
			String SQL_add = "INSERT INTO SYSTEM.OPERATOR (AID,UID,PASSWD) VALUES ('" 
							+ data[2] + "', '" + data[3] + "', '" + data[4] + "');";
			if( UpdateProcessor.update(SQL_add) != 0 )
				isSuccess = true;
		}
		}
		else
		{
			SendDataList.getInstance().add(
					new SendData( rd, head + "Add Addorney Fail\nAccount Administrator is Needed" ));
			return;
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess ){
			rs = "Add Addorney Success";
			Log.log(rd.getJobNumber(), data[2], data[0], data[4], 0, 0, 0);
		}
		else
			rs = "Add Addorney Fail";
		SendDataList.getInstance().add(
				new SendData(rd,head+rs));
	}

}
