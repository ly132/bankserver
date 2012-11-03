package processor;

import java.sql.ResultSet;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class addem extends AbstractProcessor{

	public void processing(RcvData rd) {

		//  addem^pid^passwd^name^age^phone^address
		//	0		1	2		3	4	5		6
		//	addem^success/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
			String SQL_checkPid = "SELECT PID FROM SYSTEM.EMPLOYEE WHERE PID='" + data[1] + "';";
			if( QueryProcessor.query(SQL_checkPid).next() )
				throw new Exception("pid already exist");
			Integer em_type = Integer.parseInt(rd.getJobNumber().substring(0,1)); 
			String threshold1 = em_type-1 + "00000";
			String threshold2 = em_type + "00000";
			String SQL_getids = "SELECT MAX(JOBNUM) FROM SYSTEM.EMPLOYEE WHERE JOBNUM<'" + threshold2 + "AND JOBNUM>'" + threshold1 +"';";
			ResultSet rss = QueryProcessor.query(SQL_getids);
			String new_job_num = em_type-1 + "00001";
			if( rss.next() )
			{
				 new_job_num = Integer.parseInt((rss.getString("JOBNUM"))) + 1 + "";
			}
			String SQL_insert = "INSERT INTO SYSTEM.EMPLOYEE (JOBNUM,PID,PASSWD,NAME,AGE,PHONE,ADDRESS,BJOBNUM) VALUES ('" + 
								new_job_num + "', '" +
								data[1]		+ "', '" +
								data[2]		+ "', '" +
								data[3]		+ "', '" +
								data[4]		+ "', '" +
								data[5]		+ "', '" +
								data[6]		+ "', '" +
								rd.getJobNumber() + "');";
			if( UpdateProcessor.update(SQL_insert) != 0 )
				isSuccess = true;
		}catch (Exception e)
		{
			e.printStackTrace();
			isSuccess = false;
			rs = e.getMessage();
		}
		if( isSuccess )
		{
			rs = "success";
			Log.log(rd.getJobNumber(),"",data[0],data[1]+":"+rs);
		}
		else
			if(rs.equals(""))
				rs = "failed";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));		
	}

}
