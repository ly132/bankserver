package server;

import data.OnlineUserList;

/**
 * This class is used for checking timeout user every 5 seconds.
 * @author Lenny
 *
 */
public class TimeOutProcessor implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while( true )
		{
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				break;
			}
			
			OnlineUserList.getInstance().checkTimeOut();
		}
	}

}
