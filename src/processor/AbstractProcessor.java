package processor;

import java.sql.SQLException;

import data.RcvData;

public abstract class AbstractProcessor {

	public abstract void processing(RcvData rd);	
}
