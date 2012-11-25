package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class chem extends AbstractProcessor{

	public void processing(RcvData rd) {
		//	send	chem^passwd^info_type^new_info
		//	rcvd	chem^success/fail
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		boolean isSuccess = false;
		try{
			
		String SQL_get = "SELECT PASSWD FROM SYSTEM.EMPLOYEE WHERE JOBNUM='" + rd.getJobNumber() + "';";
		ResultSet rss = QueryProcessor.query(SQL_get);
		if( rss.next() && rss.getString("PASSWD").equals(data[1]) )
		{
			String type;
			if( data[2].equals("Passwd") )
				type = "PASSWD";
			else if( data[2].equals("Age") )
				type = "AGE";
			else if( data[2].equals("Phone") )
				type = "PHONE";
			else if( data[2].equals("Address") )
				type = "ADDRESS";
			else
				throw new Exception();
			String SQL_set = "UPDATE SYSTEM.EMPLOYEE SET " + type + "='" + data[3] + "'" +
					" WHERE JOBNUM='" + rd.getJobNumber() + "';";
			if( UpdateProcessor.update(SQL_set) != 0 )
				isSuccess = true;
		}
		}catch( Exception e)
		{
			e.printStackTrace();
			isSuccess = false;
		}
		String rs;
		if( isSuccess )
			rs = "Change Employee Information Success";
		else
			rs = "Change Employee Information Fail";
		SendDataList.getInstance().add(
				new SendData(rd,head+rs));
	}
}
