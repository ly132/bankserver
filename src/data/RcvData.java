package data;

import java.net.InetAddress;
import java.util.HashMap;

public class RcvData {
	
	private Address address;
//	private HashMap<String,String> data;
	private String[] data;
	private String type;
	private boolean isValue = true;
	
	//the input data are like this, x^xx^...
	public RcvData(Address address, String data) {
		this.address = address;
		isValue = spiltData(data);
	}
	
	public InetAddress 				getIP()		{ return address.getIpAddress(); }
	public int						getPort()	{ return address.getPort(); }
	//public String 					getID()		{ return data[0]; }
	public String 					getID()		{ return "";}
	public String[]					getData()	{ return data; }
	public String					getType()	{ return this.type; }

	public boolean isValue() {
		// TODO Auto-generated method stub
		return isValue;
	}
	
	private boolean spiltData(String da)
	{		
		da = da.trim();		
		this.data = da.split("^");
		if( data.length < 2 )
			return false;
		this.type = data[0];		
		return true;
			
	}
}
