package db;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import server.Server;

/**
 * This class is used for doing database update.
 * 
 * @author Lenny
 * @version 1.0
 *
 */
public class UpdateProcessor implements Callable<Integer> {
	
	private String sql = null;
	
	public UpdateProcessor(String sql)
	{
		this.sql = sql;
	}	

	public static int update(String updateSql) {
		// TODO Auto-generated method stub
		try {
			return Server.pool.submit(new UpdateProcessor(updateSql)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public synchronized Integer call() throws Exception
	{
		Integer result = Server.db2con.connect().createStatement().executeUpdate(sql);
		Server.db2con.close();
		return result;
		
	}
}
