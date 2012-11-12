package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;

public class getSubordinate extends AbstractProcessor {

	public void processing(RcvData rd) {
		//	send getSubordinate^
		//	rcvd getSubordinate^job_n:name^job_n:name^...
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = head;
		try{
			String SQL_1 = "SELECT * FROM SYSTEM.EMPLOYEE WHERE BJOBNUM='" + rd.getJobNumber() + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			while( rss.next() )
			{
				StringBuilder sb = new StringBuilder();
				sb.append(rss.getString("JOBNUM")).append(":")
				  .append(rss.getString("pid")).append(":")
				  .append(rss.getString("name")).append(":")
				  .append(rss.getString("age")).append(":")
				  .append(rss.getString("phone")).append(":")
				  .append(rss.getString("address")).append(":");
				rs = rs + sb.toString() + Server.token;
			}
			//rs = rs.substring(0,rs.length()-1);
			isSuccess = true;
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
			rs = e.getMessage();
		}
		SendDataList.getInstance().add(
				new SendData( rd, rs ));
	}
}
