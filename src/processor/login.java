package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;

public class login extends AbstractProcessor {

	public void processing(RcvData rd) {
		//	send login^job_number^passwd
		//	rcvd login^[01][^job_number^name]
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		String name = "";
		try{
			String SQL_1 = "SELECT PASSWD,NAME FROM SYSTEM.EMPLOYEE WHERE JOBNUM='" + data[1] + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			if( rss.next() && rss.getString("PASSWD").equals(data[2]) )
			{
				name = rss.getString("NAME");
				isSuccess = true;
			}
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
			rs = e.getMessage();
		}
		if( isSuccess )
		{
			rs = "1" + Server.token + data[1] + Server.token + name;
		}
		else if( rs.equals("") )
				rs = "failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}

}
