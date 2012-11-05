package processor;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
		String logs = "TIME\t\t\tOPER\tTYPE\tINCOME\tOUTCOME\tBALANCE" + Server.token;
		try{
			if( CommonMethods.checkPasswd(data[2], data[3]) )
			{
				balance = CommonMethods.getBalance(data[2]);
				String SQL_last_open_time = "SELECT MAX(TIME) AS MTIME FROM SYSTEM.LOG WHERE TYPE='OPEN' AND TARGET='" + data[2] + "';";
				ResultSet last_open_time_rss = QueryProcessor.query(SQL_last_open_time);
				String last_open_time = null;
				if( last_open_time_rss.next() )
				{
					last_open_time = last_open_time_rss.getString("MTIME");
		//			last_open_time = last_open_time == null ? "00000000" : last_open_time;
				}
				if( last_open_time == null )
					last_open_time = convertToDatetime("00000000");
				String SQL_time = "SELECT * FROM SYSTEM.LOG WHERE LOG.TIME>'" + t1 + 
						"' AND LOG.TIME<'" + t2 + "' AND LOG.TIME>='" + last_open_time + "' AND TARGET='" + data[2] +  "';";
				ResultSet rss = QueryProcessor.query(SQL_time);
				
				String[] types = {"open","deposit","withdrawal","transfer"};
				
				List<String> allow_type = Arrays.asList(types);
				while( rss.next()  )//&& allow_type.contains(rss.getString("TYPE"))
				{
					// time operator type income outcome balance
					String income = rss.getString("INCOME") == null ? "" : rss.getString("INCOME");
					String outcome = rss.getString("OUTCOME") == null ? "" : rss.getString("OUTCOME");
					String Balance = rss.getString("BALANCE") == null ? "" : rss.getString("BALANCE");
					String type = rss.getString("TYPE");
					if( !allow_type.contains(type) )
						continue;
					type = type.length() > 7 ? type.substring(0,7) : type;
					logs = logs + rss.getString("TIME") + "\t" + rss.getString("OPER") + 
							"\t" + type + "\t" + income + "\t" + outcome + "\t" + Balance + Server.token;
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
