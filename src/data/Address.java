package data;

import java.net.InetAddress;
import java.net.UnknownHostException;

import server.Server;

public class Address {
	
	private InetAddress ip_address;
	private String ip_string;
	private int port;
	
	public Address( InetAddress ip, int port )
	{
		this.ip_address = ip;
		this.ip_string = convertIPToString(ip);
		this.port = port;		
	}
	
	public Address( String ip, int port )
	{
		this.ip_address = creatIPFromString(ip);
		this.ip_string = ip;
		this.port = port;		
	}
	
	public Address( InetAddress ip )
	{
		this.ip_address = ip;
		this.ip_string = convertIPToString(ip);
		this.port = -1;
	}
	
	public Address( String ip )
	{
		this.ip_address = creatIPFromString(ip);
		this.ip_string = ip;
		this.port = -1;
	}
	
	/**
	 * This method gets the ip address involve in an 
	 * <code>InetAddress</code> object.
	 * 
	 * @return An <code>InetAddress</code> object
	 * 
	 * @see Address#getIpString()
	 */
	public InetAddress	getIpAddress() 	{ return ip_address; }
	
	
	/**
	 * This method returns a <code>String</code> reprsenting the ip address.
	 * 
	 * @return a <code>String</code> reprsenting the ip address.
	 */
	public String		getIpString()	{ return ip_string; }
	
	
	public int 			getPort() 	{ return port; }
	
	
	/**
	 * This method translates an IP address in xxx.xxx.xxx.xxx form to
	 * an instance of <code>InetAddress</code>.
	 * 
	 * @param sIP The <code>String</code> represents an IP address in xxx.xxx.xxx.xxx form
	 * @return <code>InetAddress</code>
	 * @throws UnknownHostException
	 * @see InetAddress
	 * @see Server#convertIPToString(InetAddress)
	 * 
	 */
	public static InetAddress creatIPFromString( String sIP )
	{
		String[] items = sIP.split("\\.");
		byte[] bIp = new byte[4];
		for( int i = 0 ; i < 4 ; i++ )
		{
			bIp[i] = (byte)Integer.parseInt(items[i]);
		}
		try {
			return InetAddress.getByAddress(bIp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println( sIP + "is illegal IP address.");
			return null;
		}
	}
	
	
	/**
	 * This method translates an InetAddress instance to a <code>String</code>
	 * in xxx.xxx.xxx.xxx form that represents an IP address.
	 * 
	 * @param ip Instance of <code>InetAddress</code>
	 * @return <code>String</code> that represents an IP address.
	 * @see InetAddress
	 * @see Server#creatIPFromString(String)
	 */
	public static String convertIPToString(InetAddress ip)
	{
		byte[] bIp = ip.getAddress();
		int[] iIp = new int[4];
		for( int i = 0 ; i < 4 ; i++ )
		{
			iIp[i] = ((int) bIp[i]) & 0x000000FF;
		}
		return (new StringBuilder()).append(iIp[0]).append(".")
									.append(iIp[1]).append(".")
									.append(iIp[2]).append(".")
									.append(iIp[3]).toString();
	}

}
