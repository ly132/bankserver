package processor;

import server.Server;
import data.RcvData;
import data.SendData;
import data.SendDataList;

public class advinfo extends AbstractProcessor {

	public void processing(RcvData rd) {
		// jobno^advinfo^aid^pid
		//	rcvd login^1[0]
		
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		boolean isSuccess = false;
		String rs = "";
		try{
			if( CommonMethods.checkUid(data[2], data[1]) )
				isSuccess = true;
			else
				isSuccess = false;
		}catch( Exception e )
		{
			e.printStackTrace();
			isSuccess = false;
			rs = e.getMessage();
		}
		if( isSuccess )
			rs = "1";
		else
			rs = "0";
		SendDataList.getInstance().add(
				new SendData( rd, head + rs ));
	}

}
