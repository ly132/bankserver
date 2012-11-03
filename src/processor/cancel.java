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
		
		// rcvd cancel^pid^aid^passwd
		//  		0	1	2	3	
		// send cancel^succeeded/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		String rs = "";
		try{
		if( CommonMethods.checkPasswd(data[2], data[3]) && CommonMethods.checkUid(data[1],data[2]) )
		{
			String SQL_1 = "SELECT TYPE FROM SYSTEM.USER, SYSTEM.ACCOUNT WHERE " +
					"ACCOUNT.UID=USER.UID AND ACCOUNT.AID='" + data[2] + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			if( rss.next() )
			{
				String type = rss.getString("TYPE");
				String SQL_del = "DELETE FROM SYSTEM.ACCOUNT WHERE AID='" + data[2] + "';";
				if( UpdateProcessor.update(SQL_del) != 0 ){
					if( type.equals("e") )
					{
						SQL_del = "DELETE FROM SYSTEM.OPERATOR WHERE AID='" + data[2] + "';";
						if( UpdateProcessor.update(SQL_del) != 0 )
							isSuccess = true;
					}
				}
			}
		}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
		}
		if( isSuccess )
		{
			rs = "successed";
			Log.log(rd.getJobNumber(),data[2],data[0],rs);
		}
		else
			rs = "failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}
}
