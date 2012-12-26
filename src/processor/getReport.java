package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;

public class getReport extends AbstractProcessor {

	public void processing(RcvData rd) {
		//	send getReport^start^end
		//	rcvd getReport^xxxxxx^xxxxxxxxxx
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		String t1 =  convertToDatetime(data[1]);
		String t2 = convertToDatetime2(data[2]);
		
		boolean isSuccess = false;
		String rs = head;
		String logs = "";
		try{
			String SQL_1 = "SELECT * FROM SYSTEM.LOG WHERE OPER='" + rd.getJobNumber() + "'" +
					" AND TIME>='" + t1 + "' AND TIME<='" + t2 + "';";
			ResultSet rss = QueryProcessor.query(SQL_1);
			while( rss.next() )
			{
				StringBuilder sb = new StringBuilder();
				sb.append(covertNull(rss.getString("TIME"))).append(",")
				.append(covertNull(rss.getString("TARGET"))).append(",")
				.append(covertNull(rss.getString("TYPE"))).append(",")
				.append(covertNull(rss.getString("SUBTARGET"))).append(",")
				.append(covertNull(rss.getString("INCOME"))).append(",")
				.append(covertNull(rss.getString("OUTCOME"))).append(",")
				.append(covertNull(rss.getString("BALANCE"))).append(",");
				logs = logs+sb.toString()+Server.token;
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
				new SendData( rd, rs + logs));
	}
	
	String covertNull(String s)
	{
		return s == null ? " " : s;
	}
	
	private String convertToDatetime(String s) {
		// TODO Auto-generated method stub
		String y = s.substring(0,4);
		String m = s.substring(4,6);
		String d = s.substring(6);
		return y+"-"+m+"-"+d+" 00:00:00";
	}
	
	private String convertToDatetime2(String s) {
		// TODO Auto-generated method stub
		String y = s.substring(0,4);
		String m = s.substring(4,6);
		String d = s.substring(6);
		return y+"-"+m+"-"+d+" 23:59:59";
	}
}