package db;

public class Query {
	
	final static String TBL_person = "AuFGABE_1"; 
	final static String TBL_TEAMNAME ="TEAMNAME";
	final static String TBL_QUERYCOUNT = "QUERYCOUNT";
	final static String TBL_TIMES ="TIMES";
	
	final static String SELECT_FROM_QUERYDATA = "select * from "+TBL_person;
	final static String SELECT_HALF ="select TEAMNAME,ANZAHL_ANFRAGEN FROM "+TBL_TIMES+" WHERE TURNIERVERLAUF like 'halbfinale'";
	final static String SELECT_QUARTER ="select TEAMNAME,anzahl_anfragen FROM "+TBL_TIMES+" where turnierverlauf like 'viertelfinale'";
	final static String SELECT_EIGHT ="select TEAMNAME,anzahl_anfragen FROM "+TBL_TIMES+" where turnierverlauf like 'achtelfinale'";
	final static String SELECT_FINAL ="select TEAMNAME,anzahl_anfragen FROM "+TBL_TIMES+" where turnierverlauf like 'finale'";
}
