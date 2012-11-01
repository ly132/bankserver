package data;

import java.util.LinkedList;

//饿汉式单例模式
public class RcvDataList {
	
	private LinkedList<RcvData> dataList = new LinkedList<RcvData>();
	private static RcvDataList instance = new RcvDataList();
	private RcvDataList(){};
	
	public static RcvDataList getInstance(){
		return instance;
	}
	
	public synchronized RcvData pop() throws InterruptedException{
		RcvData rd = null;
		while( (rd = dataList.poll()) == null )
			this.wait();
		System.out.println("rcvdatalist retrun");
		return rd;
	}
	
	public synchronized void add(RcvData rd)
	{
		if( rd.isValue() )
		{
			dataList.add(rd);
			this.notifyAll();
		}
		System.out.println("rcvdatalist notify");
	}

	public synchronized String print() {
		// TODO Auto-generated method stub
		return dataList.toString();
	}
}
