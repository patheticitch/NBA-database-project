package db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.PieDataset;
import org.jfree.data.jdbc.JDBCPieDataset;

public class OracleConnect {
	private static final String DBNAME = "";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:oracle";
	private static final String USER = "s864398";
	private static final String PASSWORD = "Uno2Hid4";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private Connection con;
	private PreparedStatement prepStatement_SELECT_FROM_QUERYDATA;

	
	
	private Connection connect(String dbname) throws SQLException {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL + dbname, USER, PASSWORD);
		} catch (ClassNotFoundException ex) {
			System.exit(-1);
		}
		return con;
	}
	
	

	public void openDB() throws SQLException {
		con = this.connect(DBNAME);
		System.out.println("Connected to: " + con.getMetaData().getDatabaseProductName() + " "
				+ con.getMetaData().getDatabaseProductVersion());
		prepStatement_SELECT_FROM_QUERYDATA = con.prepareStatement(Query.SELECT_FROM_QUERYDATA);
	}

	/**
	 * This method opens a connection the DB and prints out the connection
	 * status.
	 */
	public void closeDB() throws SQLException {
		prepStatement_SELECT_FROM_QUERYDATA.close();
		con.close();
		System.out.println("Connection is closed: " + con.isClosed());
	}
	
	public void printTable() {
		
	}
	
	  public void printAllPersons() {
		  //String createTable=Person_SQL_Statements.CREATE_TABLE_PERSON;
		  String query = Query.SELECT_FROM_QUERYDATA;
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
	           String teamname = rs.getString(Query.TBL_TEAMNAME);
	           int count = rs.getInt(Query.TBL_QUERYCOUNT);
	           System.out.format("%-15s %-15d %n", 
	        		   teamname, count);
	       }
		   System.out.println("================================================================");
	   }
	  
	public JFreeChart createChart(String query, String title) {
		JFreeChart pieChart = null;
		try {
			PieDataset pieDataset = new JDBCPieDataset(this.connect(DBNAME), query);
			pieChart = ChartFactory.createPieChart(title, // chart title
					pieDataset, true, // legend displayed
					true, // tooltips displayed
					false); // no URLs

		} 
		catch (SQLException sqlEx) { // checked exception
			System.err.println("Error trying to acquire JDBCPieDataset.");
			System.err.println("Error Code: " + sqlEx.getErrorCode());
			System.err.println("SQLSTATE:   " + sqlEx.getSQLState());
			sqlEx.printStackTrace();
		}

		return pieChart;
	}
	public void drawChart(JFreeChart chart) {
		Plot plot = chart.getPlot();
		ChartFrame frame = new ChartFrame(chart.getTitle().getText(),chart,true);
		frame.setVisible(true);
		frame.setSize(599,400);
		
		
	}
	
	public void drawChartFromTeams(File file, int width, int height) {
		String query=Query.SELECT_FROM_QUERYDATA;
		String title="Wie oft wurde nach Temas der NBA in der Saison 2005/2006 gesucht?";
		JFreeChart chart = this.createChart(query, title);
		try {
			ChartUtilities.saveChartAsJPEG(file,chart,width,height);
		}
		catch(IOException e) {
			System.out.println("could not write file");
		}
		
		
	}

}
