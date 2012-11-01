package data;

import java.net.InetAddress;

public class OnlineUser {
	
	private Address address;
	private String ID;
	private String status;	
	private long lastActiveTime;
	
	public OnlineUser( Address address, String ID, String status )
	{
		this.address = address;
		this.ID = ID;
		this.setStatus(status);
		this.lastActiveTime = System.currentTimeMillis();
	}
	
	public InetAddress	getIP() 	{ return address.getIpAddress(); }	
	public int 			getPort()	{ return address.getPort();	}	
	public String 		getID() 	{ return ID; }
	public String 		getStatus() { return status; }
	
	public void	setStatus(String status) {
			this.status = status;
	}
	
	/**
	 * This method active this user, usually being called
	 * when recieved a msg from the user having this id.
	 */
	public synchronized void active() {
		lastActiveTime = System.currentTimeMillis();
	}
	
	/**
	 * This method check if a client stop sending msg to server
	 * for a specific time, mostly 30 minutes.
	 * 
	 * @return true if interval is to long, false if not
	 */
	public synchronized boolean isTimeOut() {
		if( (System.currentTimeMillis() - lastActiveTime) > 30*60*1000 )
			return true;
		return false;
	}
	
}
