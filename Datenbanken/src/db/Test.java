package db;

import java.io.File;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws SQLException{
		OracleConnect connection = new OracleConnect();
		connection.openDB();
		File jpg= new File("./test/first.jpg");
		//connection.drawPieChartFromTeams(jpg,500,400);
		connection.drawBarChartFromTeams(jpg, 500, 400);
		connection.closeDB();
		
	}

}
