package data;

import java.util.HashMap;
import java.util.Set;

public class OnlineUserList {
	
	private HashMap<String, OnlineUser> onlineUserList = new HashMap<String, OnlineUser>();
	private static OnlineUserList instance = new OnlineUserList();
	private OnlineUserList(){};
	
	public static OnlineUserList getInstance(){
		return instance;
	}
	
	public synchronized boolean isOnline(String ID) {
		// TODO Auto-generated method stub
		return onlineUserList.containsKey(ID);
	}
	
	public synchronized OnlineUser getUser(String ID)
	{
		return onlineUserList.get(ID);
	}
	
	public synchronized void login(OnlineUser olUser)
	{
		onlineUserList.put(olUser.getID(), olUser);
	}
	
	public synchronized void logout(String ID)
	{
		onlineUserList.remove(ID);
	}

	public void setStatus(String id, String newStatus) {
		// TODO Auto-generated method stub
		OnlineUser temp = onlineUserList.get(id);
		temp.setStatus(newStatus);
		onlineUserList.put(id, temp);
	}

	public String print() {
		// TODO Auto-generated method stub
		return onlineUserList.toString();
	}

	/**
	 * This method checks the use list to find out if there is any
	 * user timeout and log it out if there is.
	 */
	public void checkTimeOut() {
		// TODO Auto-generated method stub
		Set<String> onlineUserIDs = onlineUserList.keySet();
		for( String userID : onlineUserIDs )
		{
			OnlineUser user = getUser(userID);
			if( user.isTimeOut() )
			{
				String data = "<ID>" + user.getID() + "</><LOGOUT> </>";
				RcvData rd = new RcvData( new Address( user.getIP(), user.getPort() ), data);
				RcvDataList.getInstance().add(rd);
			}
		}
	}
	
}
