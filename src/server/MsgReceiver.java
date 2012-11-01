

package server;

import java.io.IOException;
import java.net.DatagramPacket;
import data.Address;
import data.RcvData;
import data.RcvDataList;

/**
 * This class is used for receiving msg.
 * 
 * @author Lenny
 * @version 1.0
 */
public class MsgReceiver implements Runnable {	
	
	
	public void run(){
		
		while(true)
		{
			byte[] rcvData = new byte[1024];
			DatagramPacket rcvPacket = new DatagramPacket(rcvData, rcvData.length);
			try {
				Server.serverSocket.receive(rcvPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String data = new String(rcvPacket.getData());
			System.out.println(data);
			
			Address address = new Address( rcvPacket.getAddress(), rcvPacket.getPort() );
			
			RcvDataList.getInstance().add( new RcvData(address,data) );
		}		
	}
}
