package info.goolap.beuth.jdbc.Storage.Person_DataAccessObject;
 
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * @author Alexander L�ser
 * This class implements methods for RDBMS connectivity and methods for reading and writing from table person. 
 *
 */
public class Person_Storage {
 
   /* These variable values are used to setup the Connection object */
	
   private static final String DBNAME = "";
   private static final String URL = "jdbc:oracle:thin:@localhost:1521:oracle";
   private static final String USER = "";
   private static final String PASSWORD = "";
   private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
   private Connection con;
   private PreparedStatement prepStatement_SELECT_PERSONS_WITH_YEAR;
   private PreparedStatement prepStatement_INSERT_PERSON;
   
 
   /* 
    This method is used to create a connection using 
   the values listed above. Notice the throws clause 
   in the method signature. This allows the calling method 
   to deal with the exception rather than catching it in 
   both places. The ClassNotFoundException must be caught 
   because the forName method requires it. */

   /**
    * @param dbname
    * @return
    * @throws SQLException
    *     
    *  This method is used to create a connection using 
    *  the values listed above. Notice the throws clause 
    *  in the method signature. This allows the calling method 
    *  to deal with the exception rather than catching it in 
    *  both places. The ClassNotFoundException must be caught 
    *  because the forName method requires it. 
    */
   private Connection connect(String dbname) throws SQLException {
      Connection con = null;
      try {
         Class.forName(DRIVER); 
         con = DriverManager.getConnection(URL+dbname, USER, PASSWORD);
      }
      catch(ClassNotFoundException ex) {
         System.exit(-1);
      }
      return con;
   }
 
   /** This method prints out the records of table person. Note the try 
    *  and catch. Virtually all JDBC methods throw a 
    *  SQLException that must be tended to. The connection 
    *  object is used to create a statement object. 
    *  The executeQuery method is used to submit a 
    *  SELECT SQL query. The executeQuery method returns a ResultSet object. 
    */
   public void printAllPersons() {
	  //String createTable=Person_SQL_Statements.CREATE_TABLE_PERSON;
	  String query = Person_SQL_Statements.SELECT_STAR_FROM_PERSONS;
	  ResultSet rs = null;
      try {
    	 
         Statement s = con.createStatement();
		 long begin = System.currentTimeMillis();
         rs = s.executeQuery(query);
		 long executiontime = System.currentTimeMillis()-begin;
         this.printResultSet2Shell(rs,query,executiontime);
         rs.close();
         s.close();
      }
      catch(SQLException ex) {
    		// handle any errors
    		System.out.println("SQLException: " + ex.getMessage());
    		System.out.println("SQLState: " + ex.getSQLState());
    		System.out.println("VendorError: " + ex.getErrorCode());        
    		System.exit(-1);
      }
   }

   /**
    * @param date
    * This method reads records from the table person for a given date. It executes a prepared statement query. 
    */
   public void getPerson_withYear(Date date) {
	   ResultSet rs = null;
	   try {
		   PreparedStatement pst = prepStatement_SELECT_PERSONS_WITH_YEAR;
		   pst.setDate(1, date);
		   long begin = System.currentTimeMillis();
		   rs = pst.executeQuery();
		   long executiontime = System.currentTimeMillis()-begin;
		   this.printResultSet2Shell(rs, rs.getStatement().toString(), executiontime);
		   rs.close();
	   }
	   catch(SQLException ex) {
	    		// handle any errors
	    		System.out.println("SQLException: " + ex.getMessage());
	    		System.out.println("SQLState: " + ex.getSQLState());
	    		System.out.println("VendorError: " + ex.getErrorCode());        
	    		System.exit(-1);
	   }
	}   

   /** This method is used to open the DB and to print out 
   * the connection status.  
 * @param prepStatement_INSERT_PERSON 
   */
   public void openDB() throws SQLException {
	   con  = this.connect(DBNAME);
	   System.out.println("Connected to: " +
			   con.getMetaData().getDatabaseProductName() + " " +
			   con.getMetaData().getDatabaseProductVersion() 
		);
	   prepStatement_INSERT_PERSON = con.prepareStatement(Person_SQL_Statements.INSERT_PERSON);
	   prepStatement_SELECT_PERSONS_WITH_YEAR = con.prepareStatement(Person_SQL_Statements.SELECT_PERSONS_WITH_YEAR);
	}

   /** This method opens a connection the DB and prints out 
    * the connection status.  
    */
   public void closeDB() throws SQLException {
	   prepStatement_INSERT_PERSON.close();
	   prepStatement_SELECT_PERSONS_WITH_YEAR.close();
	   con.close();
	   System.out.println("Connection is closed: "+ con.isClosed());
   }

   
   /**
    * @param rs
    * @param query
    * @param executiontime
    * @throws SQLException
    * 
    * This method navigates through the records 
    * in the ResultSet object (the next method for 
    * example moves the cursor to the next row; it 
    * returns false when it runs out of rows) as well 
    * as methods to access fields in those rows. 
    * Notice that the person_id  fields is of  the  data 
    * type 'long', fields name and location are strings
    * and field born_date is a date. The ResultSet 
    * object provides methods to deal with most common data 
    * types. Please  review how other java data types align 
    * with database data types. The report is 
    * formatted using the format method introduced in Java 5.  
    * */
   private void printResultSet2Shell(ResultSet rs, String query, long executiontime) throws SQLException
   {
	   System.out.println("===============================================================");
	   System.out.println(query);
	   System.out.println("Execution Time: " + executiontime +"ms");
	   System.out.println("===============================================================");
	   System.out.format("%-20s %-15s %-15s %10s%n", 
           "PERSON_ID", "FIRST_NANE", "LAST_NAME", "BORN_DATE");
       System.out.format("%-20s %-15s %-15s %10s%n", 
           "-------------------", "---------------", 
			"------------", "----------");

       while(rs.next()) {
           long person_id = rs.getLong(Person_SQL_Statements.COL_person_id);
           String first_name = rs.getString(Person_SQL_Statements.COL_first_name);
           String last_name = rs.getString(Person_SQL_Statements.COL_last_name);
           Date born_date = rs.getDate(Person_SQL_Statements.COL_born_date);
           System.out.format("%-20d %-15s %-15s %10s %n", 
        		   person_id, first_name, last_name, born_date);
       }
	   System.out.println("================================================================");
   }

	/**
	 * 
	 * @param person_id
	 * @param first_name
	 * @param last_name
 	 * @param born_date
 	 * This method demonstrates an insert record operation. The method implements this functionality with 
 	 * a  prepared statement and an executeUpdate. Eventually, we can extend the method for update 
 	 * and delete operations as well.
 	 */
   public void insertPerson(long person_id, String first_name, String last_name, Date born_date) 
   {
	   try {
		   PreparedStatement pstmt = prepStatement_INSERT_PERSON; 
				 pstmt.setLong(1,person_id);
				 pstmt.setString(2, first_name);
				 pstmt.setString(3, last_name);
				 pstmt.setDate(4, born_date);
				 System.out.println("===== Executing Insert ========================================="); 
				 System.out.println(pstmt.toString());
				 System.out.println("Update executed with code:" +  pstmt.executeUpdate()); 
	   }
		   catch(SQLException ex) {
		   		// handle any errors
		   		System.out.println("SQLException: " + ex.getMessage());
		   		System.out.println("SQLState: " + ex.getSQLState());
		   		System.out.println("VendorError: " + ex.getErrorCode());        
		   		System.exit(-1);
		   }
   }
   
   /**
    * @param query
    * This method is currently not used. It demonstrates, given a query, how we can read meta information about tables 
    * in the query from the data dictionary.  
    */
   private void getColumnNamesAndTypes(String query) 
   {
	   ResultSet rs = null;
	   try {
		   Statement stmt = con.createStatement();     			// Create a Statement object
		   rs = stmt.executeQuery(query); 
		   ResultSetMetaData rsmtadta = rs.getMetaData();      // Create a ResultSetMetaData object  
		   int colCount = rsmtadta.getColumnCount();                                   
		   for (int i=1; i<= colCount; i++) 
		   {                                          
			   String colName = rsmtadta.getColumnName(i);    // Get column name
			   String colType = rsmtadta.getColumnTypeName(i);
			   System.out.println("Column = " + colName + " is data type " + colType);  // Print the column value
		   } 
		   stmt.close();
	   }
	   catch(SQLException ex) {
   		// handle any errors
   		System.out.println("SQLException: " + ex.getMessage());
   		System.out.println("SQLState: " + ex.getSQLState());
   		System.out.println("VendorError: " + ex.getErrorCode());        
   		System.exit(-1);
	   }
   }

}