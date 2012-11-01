package db;

import java.sql.ResultSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import server.Server;

/**
 * This class is used for doing database query.
 * 
 * @author Lenny
 * @version 1.0
 * 
 */
public class QueryProcessor implements Callable<ResultSet> {

	private String sql = null;
	
	public QueryProcessor(String sql)
	{
		this.sql = sql;
	}
	
	public static ResultSet query(String sql)
	{
		try {
			return Server.pool.submit(new QueryProcessor(sql)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public synchronized ResultSet call() throws Exception {
		// TODO Auto-generated method stub
		ResultSet rs = Server.db2con.connect().createStatement().executeQuery(sql);
		return rs;
	}
}
