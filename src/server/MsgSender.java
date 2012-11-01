package server;

import java.io.IOException;
import java.net.DatagramPacket;
import data.SendData;
import data.SendDataList;

/**
 * This class is used for sending msg.
 * 
 * @author Lenny
 * @version 1.0
 *
 */
public class MsgSender implements Runnable {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		byte[] sendData = new byte[1024];
		SendData sd = null;
		
		while( true )
		{
			try {
				sd = SendDataList.getInstance().pop();
				sendData = sd.getData().getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, sd.getIP(), sd.getPort());
				
				Server.serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
		}
	}
}
