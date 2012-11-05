package processor;

import db.UpdateProcessor;

public class Log {

	//deposit,withdrawal,transfer
	static void log( String job_number, String aid, String oper_type, String subaid, double income, double outcome, double balance )
	{
		String SQL_log;
		if( income != 0 )
			SQL_log = "INSERT INTO SYSTEM.LOG (OPER,TARGET,TYPE,SUBTARGET,INCOME,BALANCE,TIME) VALUES ('" +
					job_number + "', '" + aid + "', '" + oper_type + "', '" + subaid + "', '" + income + 
					"', '" + balance + "', NOW());";
		else if( outcome != 0 )
			SQL_log = "INSERT INTO SYSTEM.LOG (OPER,TARGET,TYPE,SUBTARGET,OUTCOME,BALANCE,TIME) VALUES ('" +
					job_number + "', '" + aid + "', '" + oper_type + "', '" + subaid + "', '" + outcome + 
					"', '" + balance + "', NOW());";
		else
			SQL_log = "INSERT INTO SYSTEM.LOG (OPER,TARGET,TYPE,SUBTARGET,TIME) VALUES ('" +
					job_number + "', '" + aid + "', '" + oper_type + "', '" + subaid + "', NOW());";
		UpdateProcessor.update(SQL_log);
	}

}
