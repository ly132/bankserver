package data;

import java.net.InetAddress;

public class SendData {
	
	private Address address;
	private String ID;
	private String data;
	
	public SendData( Address address, String ID, String data )
	{
		this.address = address;
		this.ID = ID;
		this.data = data;
	}
	
	//Creat SendData from a RcvData and a String to be send.
	public SendData( RcvData rd, String data )
	{
		this.address = new Address( rd.getIP(), rd.getPort() );
		this.ID = rd.getID();
		this.data = data;
	}
	
	public InetAddress	getIP()		{ return address.getIpAddress(); }
	public int			getPort()	{ return address.getPort(); }
	public String		getID()		{ return ID; }
	public String		getData()	{ return data; }
}
