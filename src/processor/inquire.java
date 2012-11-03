package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;

public class inquire extends AbstractProcessor{

	public void processing(RcvData rd) {
		// rcv  4^p_id^a_id^passwd^start^end
		// start, end: YYYYMMDD
		// send 4^balance^xxxxxxxx^xxxxxxx^...
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		String t1 =  convertToDatetime(data[4]);
		String t2 = convertToDatetime(data[5]);
		double balance = 0;
		String rs = "";
		boolean isSuccess = false;
		String logs = "";
		try{
			if( CommonMethods.checkPasswd(data[2], data[3]) )
			{
				balance = CommonMethods.getBalance(data[2]);
				String SQL_time = "SELECT * FROM SYSTEM.LOG WHERE LOG.TIME>'" + t1 + "' AND LOG.TIME<'" + t2 + "';";
				ResultSet rss = QueryProcessor.query(SQL_time);
				while( rss.next() )
				{
					logs = logs + rss.getString("TYPE") + "  " + rss.getString("RESULT") + 
							"  " + rss.getTimestamp("TIME") + Server.token;
				}
				isSuccess = true;
			}
			else
			{
				rs = "passwd not correct";
			}
		}catch( Exception e )
		{
			isSuccess = false;
			rs = "failed";
		}
		if( isSuccess )
			SendDataList.getInstance().add(
					new SendData(rd,head+balance+Server.token+logs));
		else
			SendDataList.getInstance().add(
					new SendData(rd,head+rs));
	}

	private String convertToDatetime(String s) {
		// TODO Auto-generated method stub
		String y = s.substring(0,4);
		String m = s.substring(4,6);
		String d = s.substring(6);
		return y+"-"+m+"-"+d+" 00:00:00";
	}

}
