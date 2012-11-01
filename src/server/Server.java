
package server;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.OnlineUserList;
import data.RcvDataList;
import data.SendDataList;
import db.db2Connection;

/**
 * This class is the main class of the server application of school talk. 
 * 
 * @author Lenny
 * @version 1.0
 *
 */

public class Server{
	
	public static ExecutorService pool = null;
	static DatagramSocket serverSocket = null;
	public static db2Connection db2con = new db2Connection();
	public static final String token = "^";
	
	public static void main(String[] args){
		
		try {
			serverSocket = new DatagramSocket(8243);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pool = Executors.newCachedThreadPool();
		
		//MSGReceive
		MsgReceiver msgRcv = new MsgReceiver();
		pool.execute(msgRcv);
		
		//MSGProcess
		MsgProcessor msgProcs1 = new MsgProcessor();
		pool.execute(msgProcs1);
		
		//LoginProcess
//		LoginProcessor loginProcs = new LoginProcessor();
//		pool.execute(loginProcs);
		
		//DBProcess
		
		//MSGSend
		MsgSender msgSender = new MsgSender();
		pool.execute(msgSender);
		
		//offline msg processor
//		OfflineMsgProcessor offlineMsgProcessor = new OfflineMsgProcessor();
//		pool.execute(offlineMsgProcessor);
		
		//TimeOut processor
		TimeOutProcessor timeOutProcessor = new TimeOutProcessor();
		pool.execute(timeOutProcessor);
		
		//Test module
		Scanner sc = new Scanner(System.in);
		while( true )
		{
			String cmd = sc.nextLine().trim();
			
			if( cmd.equals("rcv") )
				System.out.println(RcvDataList.getInstance().print());
			
//			if( cmd.equals("login") )
//				System.out.println(LoginList.getInstance().print());
			
			if( cmd.equals("send") )
				System.out.println(SendDataList.getInstance().print());
			
			if( cmd.equals("online") )
				System.out.println(OnlineUserList.getInstance().print());
			
//			if( cmd.equals("offlinemsg") )
//				System.out.println(OfflineDataList.getInstace().print());
			
			if( cmd.equals("quit") )
			{
				pool.shutdownNow();
				db2con.close();
//				serverSocket.close();
				System.exit(0);
			}
		}
		
	}

	/**
	 * Get time now.
	 * @return <code>String</code> represents current time in form of "YYYY-MM-DD HH:MM:SS "
	 */
	public static String getTime() {
		// TODO Auto-generated method stub
		Locale systime = Locale.CHINA;
        SimpleDateFormat timeformat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss ",systime);
		return timeformat.format(new Date());//求得本地机的系统时间; 
	}
}