package processor;

import db.UpdateProcessor;

public class Log {

	static void log( String job_number, String aid, String oper_type, String rs )
	{
		String SQL_log = "INSERT INTO SYSTEM.LOG (JOBNUM,OPAID,OPTYPE,DETAIL,TIME) VALUES ('" +
				job_number + "', '" + aid + "', '" + oper_type + "', '" + rs + "', NOW());";
		UpdateProcessor.update(SQL_log);
	}
}
