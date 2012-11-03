package server;

import processor.AbstractProcessor;

import data.OnlineUserList;
import data.RcvData;
import data.RcvDataList;

/**
 * A class that processes the message received.
 * 
 * @author Lenny
 * @version 1.0
 *
 */
public class MsgProcessor implements Runnable {
	
	
	public void run()
	{		
		while(true)
		{
			try {
				RcvData rd = RcvDataList.getInstance().pop();
//				tryActive(rd.getID());
			
				AbstractProcessor ap = (AbstractProcessor) Class.forName(
						"processor." + rd.getType()).newInstance();				
				ap.processing(rd);				
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}
	
//
//	/**
//	 * This method processes a message according to message type.
//	 * 
//	 * @param rd <code>RcvData</code> that is to be processed
//	 * 
//	 * @throws Exception
//	 */
//
//	private void processAccordingToMsgType(RcvData rd) throws Exception {
//
//		HashMap<String, String> data = rd.getData();
//		String head = "<ID>" + rd.getID() + "</>";
//		for( String type : data.keySet() )
//		{
//			//Log in process
//			if( type.equals("LOGIN") )
//				LoginList.getInstance().add(rd);			
//			
//			//Log out process
//			else if( type.equals("LOGOUT") )
//				logout(rd, head);
//			
//			//Register
//			else if( type.equals("REGISTER") )
//			{
//				String name = data.get("NICKNAME");
//				String passwd = data.get("PASSWD");
//				String photo = data.get("PHOTO");
//				register(rd,name,passwd,photo);
//			}
//			
//			//Change status process
//			else if( type.equals("CHSTATUS") )
//			{				
//				String newStatus = data.get(type);
//				changeInfo(rd, head, "CHSTATUS", newStatus);
//			}
//			
//			//Change nickname process
//			else if( type.equals("CHNICKNAME") )
//			{
//				String newNickName = data.get(type);
//				changeInfo(rd, head, "CHNICKNAME", newNickName);
//			}
//			
//			//Change photo
//			else if( type.equals("CHPHOTO") )
//			{
//				String newPhoto = data.get(type);
//				changeInfo(rd, head, "CHPHOTO", newPhoto);
//			}
//			
//			//Call a friend
//			else if( type.equals("CALLFRIEND") )
//			{
//				String friendID = data.get(type);
//				callFriend(rd, head, friendID);
//			}
//			
//			//Add a friend
//			else if( type.equals("ADDFRIEND") )
//			{
//				String friendID = data.get(type);
//				addFriend(rd, head, friendID);				
//			}
//			
//			//Delete a friend
//			else if( type.equals("DELFRIEND") )
//			{
//				String friendID = data.get(type);
//				delFriend(rd, head, friendID);
//			}
//			
//			//Agree to add friend
//			else if( type.equals("AGREEFRIEND") )
//			{
//				String friendID = data.get(type);
//				String agreeOrNot = data.get("AGREEORNOT");
//				agreeFriend(rd, head, friendID, agreeOrNot);
//			}
//			
//			//Search friend
//			else if( type.equals("SEARCHUSER"))
//			{
//				String friendID = data.get(type);
//				searchUser(rd, head, friendID);
//			}
//			
//			//Speech to offline friend
//			else if( type.equals("SPEECHID") )
//			{
//				String userID = data.get("ID");
//				String friendID = data.get(type);
//				String time = data.get("TIME");
//				String content = data.get("CONTENT");
//				speechToOfflineFriend(userID, friendID, time, content);
//			}
//		}
//	}
//
//	private void register(RcvData rd, String name, String passwd, String photo) 
//			throws SQLException {
//		// TODO Auto-generated method stub
//		if( name == null || passwd == null || photo == null )
//			return;
//		
//		String ip = "0.0.0.0";
//		Integer port = 50000;
//		String status = "OFFLINE";
//		String id = "00";
//		
//		String idSql = "SELECT MAX(UID) AS MUID FROM SYSTEM.STUSER;";
//		ResultSet idSqlResult = QueryProcessor.query(idSql);
//		if( idSqlResult.next() )
//		{
//			id = idSqlResult.getString("MUID");
//		}
//		
//		Integer tid = Integer.parseInt(id);
//		tid ++;		
//		id = tid.toString();
//		
//		StringBuilder registerToDB = new StringBuilder();
//		registerToDB.append("INSERT INTO SYSTEM.STUSER VALUES('")
//					.append(id).append("', '")
//					.append(name).append("', '")
//					.append(passwd).append("', '")
//					.append(ip).append("', ")
//					.append(port).append(", '")
//					.append(status).append("', '")
//					.append(photo).append("');");
//		int registerResult = UpdateProcessor.update(registerToDB.toString());
//		if( registerResult != 0 )
//		{
//			//register success
//			String registerSuccess = "<ID>0</><REGISTER>SUCCESS</><UID>" + 
//									 id + "</><NICKNAME>" + name + "</>";
//			SendDataList.getInstance().add(
//					new SendData(rd, registerSuccess));
//		}
//		else
//		{
//			SendDataList.getInstance().add(
//					new SendData(rd, "<ID>0</><REGISTER>FAILED></>"));
//		}
//	
//	}
//
//
//	private void logout(RcvData rd, String head) {
//		SendDataList.getInstance().add(new SendData(rd, head + "<LOGOUT>SUCCESS</>"));
//		
//		//Update my status to db2
//		String logoutSql = "UPDATE SYSTEM.STUSER SET STATUS='OFFLINE' WHERE UID='" + rd.getID() + "';";
//		int updateResult = UpdateProcessor.update(logoutSql);
//		OnlineUserList.getInstance().logout(rd.getID());
//		
//		//Tell all the online friend i'm offline
//		updateFriendStatus(rd, "OFFLINE");
//		
//		System.out.println(Server.getTime() + " " + rd.getID() + " Logout\n" + "Update " + updateResult + "\n");
//	}
//
//	private void changeInfo(RcvData rd, String head, String actionType, String newInfo)
//			throws SQLException {
//		String infoType = actionType.substring(2);
//		
//		if( infoType.equals("NICKNAME") )
//			infoType = "NAME";
//		
//		String changeInfoSql = "UPDATE SYSTEM.STUSER SET " + 
//								infoType + "='" + newInfo + "' WHERE UID='" + rd.getID() + "';";
//		int updateResult = UpdateProcessor.update(changeInfoSql);
//		
//		if( updateResult == 0 )
//		{
//			SendDataList.getInstance().add(
//					new SendData(rd, head + "<" + actionType + ">FAILED</>"));
//		}
//		else
//		{
//			SendDataList.getInstance().add(
//					new SendData(rd, head + "<" + actionType + ">SUCCESS</><" + infoType + ">" + 
//							newInfo + "</>"));
//			
//			if( actionType.equals("CHSTATUS") )
//			{
//				OnlineUserList.getInstance().setStatus(rd.getID(), newInfo);
//				
//				//Tell all the online friends the new status if it's not in HIDE mode
//				updateFriendStatus(rd, newInfo);
//			}
//		}		
//	}
///*/	
//	private void changeStatus(RcvData rd, String head, String newStatus)
//			throws SQLException {
//		
//		//Update
//		String changeStatusSql = "UPDATE SYSTEM.STUSER SET STATUS='" + 
//									newStatus + "' WHERE UID='" + rd.getID() + "';";
//		int updateResult = UpdateProcessor.update(changeStatusSql);
//		
//		if( updateResult == 0 )
//		{
//			//Update failed, return CHSTATUS:FAILED
//			SendDataList.getInstance().add(new SendData(rd, head + "<CHSTATUS>FAILED</>"));
//		}
//		else
//		{
//			//Update success, return CHSTATUS:SUCCESS
//			SendDataList.getInstance().add(new SendData(rd, 
//					head + "<CHSTATUS>SUCCESS</><STATUS>" + newStatus + "</>"));
//			OnlineUserList.getInstance().setStatus(rd.getID(), newStatus);
//			
//			//Tell all the online friends the new status if it's not in HIDE mode
//			updateFriendStatus(rd, newStatus);
//		}
//	}
//	
//	private void changeNickname(RcvData rd, String head, String newNickName) {
//		
//		//Update db2, but not tell the online friends this changes.
//		//They can get such update either login again or make a call for him.
//		
//		String changeStatusSql =
//			"UPDATE SYSTEM.STUSER SET NAME='" + 
//				newNickName + "' WHERE UID='" + rd.getID() + "';";
//		int updateResult = UpdateProcessor.update(changeStatusSql);
//		if( updateResult == 0 )
//		{
//			SendDataList.getInstance().add(
//					new SendData(rd, head + "<CHNICKNAME>FAILED</>"));
//		}
//		else
//		{
//			SendDataList.getInstance().add(
//					new SendData(rd, head + "<CHNICKNAME>SUCCESS</><NICKNAME>" + 
//							newNickName + "</>"));
//		}
//	}
////*/
//	
//	private void callFriend(RcvData rd, String head, String friendID) {
//		if( OnlineUserList.getInstance().isOnline(friendID) )
//		{
//			//friend online
//			//return ip,port to creat a connection between them
//			OnlineUser olUser = OnlineUserList.getInstance().getUser(friendID);
//			StringBuilder sb = new StringBuilder();
//			
//			sb.append(head)
//			  .append("<CALLFRIEND>SUCCESS</><ID>").append(olUser.getID())
//			  .append("</><IP>").append(Address.convertIPToString(olUser.getIP()))
//			  .append("</><PORT>").append(olUser.getPort()).append("</>");
//			
//			SendDataList.getInstance().add(new SendData(rd, sb.toString()));
//		}
//		else
//		{
//			//friend offline
//			SendDataList.getInstance().add(new SendData(
//					rd, head + "<CALLFRIEND>OFFLINE</><FRIEND>" + friendID + "</>"));
//			//then the client should send all the msg to the server,
//			//save all the online msg in the server sign
//			//see else if( type.equals("SPEECHID") )
//		}
//	}
//
//	private void addFriend(RcvData rd, String head, String friendID) {
//		
//		//before the client can add a friend, he should search whether his friend exits.		
//		String searchSql = "SELECT * FROM SYSTEM.STUSER WHERE UID='" + friendID + "';";
//		ResultSet searchResult = QueryProcessor.query(searchSql);		
//		try {
//			if( ! searchResult.next() )
//			{
//				//Condition 1, ID not exits.
//				SendDataList.getInstance().add(
//						new SendData( rd, head + "<ADDFRIEND>NOTEXIT</>" ));
//				return;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//Condition 2, ID already being a friend.
//		if( isFriend( rd.getID(), friendID ) )
//		{
//			SendDataList.getInstance().add(
//					new SendData( rd, head + "<ADDFRIEND>REPEAT</>"));
//			return;
//		}
//		
//		//Condition 3, waiting for agreement.
//		SendDataList.getInstance().add(new SendData(rd, head + "<ADDFRIEND>WAIT</>"));
//		String askMsg = "<ID>" + friendID + "</><ASKFRIEND>" + rd.getID() + "</>";		
//		if( OnlineUserList.getInstance().isOnline(friendID) )
//		{
//			//friend online, send msg to ask for add him as friend
//			OnlineUser olUser = OnlineUserList.getInstance().getUser(friendID);
//			SendDataList.getInstance().add(
//					new SendData(new Address( olUser.getIP(), olUser.getPort() ), olUser.getID(), askMsg));
//		}
//		else
//		{
//			//save offline msg
//			OfflineDataList.getInstace().addData(new OfflineData( friendID, askMsg ));
//		}
//	}
//
//	public boolean isFriend(String id, String friendID) {
//		String checkFriend = "SELECT * FROM SYSTEM.STFRIEND WHERE " +
//								"SID='" + id + "' AND FID='" + friendID + "';";
//		ResultSet resultSet = QueryProcessor.query(checkFriend);
//		try {
//			if( resultSet.next() )
//				return true;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	private void delFriend(RcvData rd, String head, String friendID) {
//		String delFriendSql = "DELETE FROM SYSTEM.STFRIEND WHERE (SID='" + rd.getID() + "' AND FID='" + friendID +
//							  "') OR (SID='" + friendID + "' AND FID='" + rd.getID() + "');";
//		int delResult = UpdateProcessor.update(delFriendSql);
//		if( delResult != 0 )
//		{
//			//update success, send msg to both
//			// <ID>..</><DELFRIEND>the friend's id</>
//			SendDataList.getInstance().add(new SendData(rd, head + "<DELFRIEND>" + friendID + "</>" ));
//			String delMsg = "<ID>" + friendID + "</><DELFRIEND>" + rd.getID() + "</>";
//			
//			//if the other one online
//			if( OnlineUserList.getInstance().isOnline(friendID) )
//			{
//				OnlineUser olUser = OnlineUserList.getInstance().getUser(friendID);
//				// <ID>...</><DELFRIEND>the id of the one ask to del</>
//				SendDataList.getInstance().add(
//						new SendData(new Address( olUser.getIP(), olUser.getPort() ), olUser.getID(), delMsg));
//			}
//			else
//			{
//				//offline msg
//				OfflineDataList.getInstace().addData(new OfflineData( friendID, delMsg ));
//			}
//		}
//		else
//		{
//			//del failed
//			SendDataList.getInstance().add(new SendData(rd, head + "<DELFRIEND>FAILED</>"));
//		}
//	}
//
//	private void agreeFriend(RcvData rd, String head, String friendID, String agreeOrNot)
//			throws SQLException {
//		
//		if( agreeOrNot.equals("DISAGREE") )
//		{
//			String disagreeMsg = "<ID>" + friendID + "</><ADDFRIEND>DISAGREE</>";
//			if( OnlineUserList.getInstance().isOnline(friendID) )
//			{
//				OnlineUser olUser = OnlineUserList.getInstance().getUser(friendID);
//				SendDataList.getInstance().add(	new SendData( 
//						new Address( olUser.getIP(), olUser.getPort() ), 
//						olUser.getID(), 
//						disagreeMsg));
//			}
//			else
//			{
//				OfflineDataList.getInstace().addData(new OfflineData(friendID, disagreeMsg));
//			}
//			return;
//		}
//		
//		String addFriendSql = "INSERT INTO SYSTEM.STFRIEND VALUES ('" + 
//				rd.getID() + "', '" + friendID + "'),('" + friendID + "', '" + rd.getID() + "');";
//		
//		int addResult = UpdateProcessor.update(addFriendSql);
//		if( addResult != 0 )
//		{
//			//send adding msg to both
//			//SendDataList.getInstance().add(new SendData( rd, "AGREEFRIEND:SUCCESS<>ID:"+friendID ));
//			String infoSql = "SELECT * FROM SYSTEM.STUSER WHERE UID='" + 
//							 rd.getID() + "' OR UID='" + friendID + "';";
//			ResultSet infoResult = QueryProcessor.query(infoSql);
//			while( infoResult.next() )
//			{
//				StringBuilder sb = new StringBuilder();
//				if( infoResult.getString("UID").equals(rd.getID()) )
//				{
//					//msg to the asking one
//					//<ID>self id</><AGREEFRIEND>SUCCESS</><FRIEND>ID,NAME,STATUS</>
//					sb.append("<ID>").append(friendID).append("</><ADDFRIEND>SUCCESS</><FRIEND>")
//					  .append(rd.getID()).append(",")
//					  .append(infoResult.getString("NAME")).append(",")
//					  .append(infoResult.getString("STATUS")).append(",")
//					  .append(infoResult.getString("PHOTO")).append("</>");
//					
//					if( OnlineUserList.getInstance().isOnline(friendID) )
//					{
//						OnlineUser olUser = OnlineUserList.getInstance().getUser(friendID);
//						SendDataList.getInstance().add( new SendData( 
//								new Address( olUser.getIP(), olUser.getPort() ), 
//								olUser.getID(), 
//								sb.toString() ));
//					}
//					else
//					{
//						OfflineDataList.getInstace().addData(new OfflineData( friendID, sb.toString() ));
//					}
//				}
//				else
//				{
//					//send back to the agree one
//					sb.append(head).append("<AGREEFRIEND>SUCCESS</><FRIEND>")
//					  .append(friendID)
//					  .append(",")
//					  .append(infoResult.getString("NAME"))
//					  .append(",")
//					  .append(infoResult.getString("STATUS")).append(",")
//					  .append(infoResult.getString("PHOTO")).append("</>");;
//					SendDataList.getInstance().add(new SendData( rd, sb.toString() ));
//				}
//			}
//		}
//	}
//
//	private void searchUser(RcvData rd, String head, String friendID)
//			throws SQLException {
//		String searchSql = "SELECT * FROM SYSTEM.STUSER WHERE UID='" + friendID + "';";
//		ResultSet searchResult = QueryProcessor.query(searchSql);				
//		
//		if( searchResult.next() )
//		{
//			StringBuilder sb = new StringBuilder();
//			sb.append(head).append("<SEARCHUSER>SUCCESS</>")					
//			  .append("<STRAGER>")
//			  .append(searchResult.getString("UID"))
//			  .append(",")
//			  .append(searchResult.getString("NAME"))
//			  .append(",")
//			  .append(searchResult.getString("STATUS")).append("</>");
//			SendDataList.getInstance().add( new SendData(rd, sb.toString()) );
//		}
//		else
//		{
//			SendDataList.getInstance().add( new SendData(rd, head + "<SEARCHUSER>FAILED</>") );
//		}
//	}
//
//	private void speechToOfflineFriend(String userID,
//			String friendID, String time, String content) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("<ID>").append(friendID)
//		  .append("</><SPEECHID>").append(userID)
//		  .append("</><TIME>").append(time)
//		  .append("</><CONTENT>").append(content)
//		  .append("</>");
//		OfflineDataList.getInstace().addData(new OfflineData(friendID, sb.toString()));
//	}
//
//	private void updateFriendStatus(RcvData rd, String newStatus) {
//		String friendsSql = "Select FID From SYSTEM.STFRIEND Where SID='" + rd.getID() + "';";
//		ResultSet friendSqlResult = QueryProcessor.query(friendsSql);
//		
//		try {
//			while( friendSqlResult.next() )
//			{
//				String fid = friendSqlResult.getString("FID");
//				if( OnlineUserList.getInstance().isOnline(fid) )
//				{
//					OnlineUser olUser = OnlineUserList.getInstance().getUser(fid);
//					String s = "<ID>" + rd.getID() + "</><STATUS>" + newStatus + "</>";
//					System.out.println(s);
//					SendDataList.getInstance().add(new SendData(new Address( olUser.getIP(), olUser.getPort() )
//							, olUser.getID(),s));
//				}
//				
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void tryActive(String id) {
		// TODO Auto-generated method stub
		if( OnlineUserList.getInstance().isOnline(id) )
			OnlineUserList.getInstance().getUser(id).active();
	}

}
