package processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import db.QueryProcessor;
import server.Server;

import data.Address;
import data.OfflineData;
import data.OfflineDataList;
import data.OnlineUser;
import data.OnlineUserList;
import data.RcvData;
import data.SendData;
import data.SendDataList;

public class open extends AbstractProcessor {

	@Override
	public
	void processing(RcvData rd) throws SQLException{
		// TODO Auto-generated method stub
		// open^pid^account_type(S/T)^passwd^balance
		String data[] = rd.getData();
		String head = data[0] + Server.token;
		
		String SQL_pid = "SELECT * FROM SYSTEM.USER WHERE UID='" + data[1] + "';";
		ResultSet pidResult = QueryProcessor.query(SQL_pid);
		if( ! pidResult.next() )
		{
			//Condition 1, pID not exits.
			SendDataList.getInstance().add(
					new SendData( rd, head + "customer not esixt" ));
			return;
		}
		if( pidResult.getString("TYPE").equals("V") && Double.parseDouble(data[4]) < 1000000 )
		{
			//init balance for vip should be large than 1000000
			SendDataList.getInstance().add(
					new SendData( rd, head + "not enough balance for VIP"));
			return;
		}
		String SQL_aid = "SELECT MAX(AID) AS MAID FROM SYSTEM.ACCOUNT;";
		ResultSet aidResult = QueryProcessor.query(SQL_aid);
		String newAid;
		if( aidResult.next()  )
			newAid = ((Integer)(Integer.parseInt(aidResult.getString("MAID")) + 1)).toString();
		else
			newAid = "1000";
		
		String SQL_createAccout = "INSERT INTO SYSTEM.ACCOUNT (AID, UID, PASSWD, BALANCE, TYPE) " +
				"VALUES ('" + newAid + "', '" + data[1] + "', '" + 
				data[3] + "', '" + data[4] + "', '" + data[2] + "';";
		
		
		String friendID = data.get("ADDFRIEND");
		addFriend(rd, head, friendID);
	}

}
