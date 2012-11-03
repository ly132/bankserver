package processor;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;
import db.QueryProcessor;
import db.UpdateProcessor;

public class delem extends AbstractProcessor {

	public void processing(RcvData rd) {
		//		delem^pid
		//	delem^success/failed
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
			String SQL_checkPid = "SELECT PID FROM SYSTEM.EMPLOYEE WHERE PID='" + data[1] + "';";
			if( QueryProcessor.query(SQL_checkPid).next() )
			{
				String SQL_del = "DELETE FROM SYSTEM.EMPLOYEE WHERE PID='" + data[1] + "';";
				if( UpdateProcessor.update(SQL_del) != 0 )
					isSuccess = true;
			}
			else
				throw new Exception("pid not exist");
		}catch( Exception e )
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
