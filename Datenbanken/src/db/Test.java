package db;

import java.io.File;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws SQLException{
		OracleConnect connection = new OracleConnect();
		connection.openDB();
//		File frage = new File("./test/aufgabe_1.jpg");
//		connection.drawBarChartFromTeams(frage, 1920, 1080);
//		File frage_5 = new File("./test/aufgabe_5_achtel.jpg");
//		connection.drawBarChartFromEight(frage_5, 1920, 1080);
		File frage_5_viertel = new File("./test/frage_5_viertel.jpg");
		connection.drawBarChartFromQuarter(frage_5_viertel, 1920,1080);
//		File frage_5_halb = new File("./test/frage_5_halb.jpg");
//		connection.drawBarChartFromHalf(frage_5_halb, 1920,1080);
//		File frage_5_finale = new File("./test/frage_5_finale.jpg");
//		connection.drawBarChartFromFinal(frage_5_finale, 1920, 1080);
		
		connection.closeDB();
		
		
	}

}
