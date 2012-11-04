package data;

import java.net.InetAddress;
import java.util.HashMap;

public class RcvData {
	
	private Address address;
//	private HashMap<String,String> data;
	private String[] data;
	private String type;
	private boolean isValue = true;
	private String job_number = null;
	
	//the input data are like this, x^xx^...
	public RcvData(Address address, String data) {
		this.address = address;
		isValue = spiltData(data);
	}
	
	public InetAddress 				getIP()			{ return address.getIpAddress(); }
	public int						getPort()		{ return address.getPort(); }
	//public String 					getID()		{ return data[0]; }
	public String 					getID()			{ return "";}
	public String[]					getData()		{ return data; }
	public String					getType()		{ return this.type; }
	public String 					getJobNumber()	{ return this.job_number; }

	public boolean isValue() {
		// TODO Auto-generated method stub
		return isValue;
	}
	
	private boolean spiltData(String da)
	{		
		da = da.trim();
		try{
			this.job_number = da.substring(0,da.indexOf('^'));
			this.data = da.substring(da.indexOf('^')+1).split("\\^");
		}catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
		if( data.length < 2 )
			return false;
		this.type = data[0];		
		return true;			
	}
}
