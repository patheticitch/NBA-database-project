package info.goolap.beuth.jdbc;

import info.goolap.beuth.jdbc.Storage.Person_DataAccessObject.Person_Storage;
import info.goolap.beuth.jdbc.logic.Person.Person_Logic;

import java.sql.Date;
import java.sql.SQLException;


/**
 * @author Alexander L�ser
 * 
 *	This class demonstrates persisting java objects in and reading relational information from a RDBMS.  
 */
public class Person_DEMO_Client {
 
	
	public static void main(String[] args) throws SQLException {
		
		// New DB Connection Instance
		Person_Storage myPerson_Storage = new Person_Storage();
		
		// Open DB
		myPerson_Storage.openDB();
		
		// SELECT * FROM "Person"
		myPerson_Storage.printAllPersons();
		// SELECT * FROM "Person" WHERE YEAR(born_date) = ? 
		myPerson_Storage.getPerson_withYear(new Date(Date.valueOf("1976-01-01").getTime()));

		
		// Insert new "Java" Person 
		// INSERT INTO person (person_id, first_name, last_name, born_date) VALUES  (?,?,?,?)
		Person_Logic myPerson = new Person_Logic("Alexander", "L�ser",  new Date(Date.valueOf("1976-04-20").getTime()));
		// Generate DB inpdependet key for new person
		System.out.println(myPerson.getPerson_id());
		
		// Transform "Java" Person instance into relational "person" table
 		// INSERT INTO person (person_id, first_name, last_name, born_date) VALUES  (?,?,?,?)
		myPerson.persist(myPerson_Storage);
		
		// SELECT * FROM "Person" WHERE YEAR(born_date) = ? 
		myPerson_Storage.getPerson_withYear(new Date(Date.valueOf("1976-01-01").getTime()));

		// close DB
		myPerson_Storage.closeDB();
	}
}
