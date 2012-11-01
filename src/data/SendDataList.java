package data;

import java.util.LinkedList;

public class SendDataList {

	private LinkedList<SendData> dataList = new LinkedList<SendData>();
	private static SendDataList instance = new SendDataList();
	private SendDataList(){};
	
	public static SendDataList getInstance(){
		return instance;
	}
	
	public synchronized SendData pop() throws InterruptedException{
		SendData sd = null;
		while( (sd = dataList.poll()) == null )
				wait();				

		return sd;
	}
	
	public synchronized void add(SendData sd)
	{
		dataList.add(sd);
		notifyAll();
	}

	public synchronized String print() {
		// TODO Auto-generated method stub
		return dataList.toString();
	}
}
