package info.goolap.beuth.jdbc.Storage.Person_DataAccessObject;

/**
 * @author Alexander L�ser
 * This class implements a single place of  RDBMS tables to java variables. 
 *
 */
public class Person_SQL_Statements {
	
	// mappings for simple example 
	final static String TBL_person = "PERSON"; 
	final static String COL_person_id ="person_id";
	final static String COL_first_name = "first_name";
	final static String COL_last_name = "last_name";
	final static String COL_born_date ="born_date";

	// example queries
	final static String CREATE_TABLE_PERSON = String.format("create table %s (%s LONG,%s varchar(50), %s varchar(50),%s DATE)",TBL_person,COL_person_id,COL_first_name,COL_last_name,COL_born_date);
	final static String SELECT_STAR_FROM_PERSONS =   "select * from "+ TBL_person;
	final static String SELECT_PERSONS_WITH_YEAR= "select * from "+TBL_person + "WHERE EXTRACT(YEAR FROM TO_DATE("+COL_born_date+", 'YYYYY-MM-DD'))";
	final static String	INSERT_PERSON = "insert into "+TBL_person
							+"("+ 
							COL_person_id 	+ 	", "+ 
							COL_first_name 	+ 	", "+ 
							COL_last_name 	+ 	", "+
							COL_born_date 	+	") values (?, ?, ?, ?)";
	
	final static String DELETE_PERSON = "delete  from "+TBL_person + " where  "+ COL_person_id + " = ? ";
}
