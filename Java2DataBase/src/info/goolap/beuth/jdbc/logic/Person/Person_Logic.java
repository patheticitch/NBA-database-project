package info.goolap.beuth.jdbc.logic.Person;

import info.goolap.beuth.jdbc.Storage.Person_DataAccessObject.Person_Storage;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;

/**
 * @author Alexander Löser
 * 
 * This class implements a Person Object with methods to set and get attributes first_name, last_name, born_date. 
 * The attribute person_id is read onlyy, It is computed in method generatePerson_ID(...) when the user instantiates a new 
 * instance of this object. The generation computes a unique sha-1 hash from all attributes + the system time.  
 */
public class Person_Logic {

	private 	long	person_id;
	private 	String 	first_name;
	private 	String	last_name;
	private 	Date 	born_date;

	
	
	public Person_Logic(String first_name, String last_name, Date born_date) {
		this.person_id = generatePerson_ID(first_name, last_name, born_date);
		this.first_name = first_name;
		this.last_name = last_name;
		this.born_date = born_date;
	}

	public long getPerson_id() {
		return person_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getBorn_date() {
		return born_date;
	}
	public void setBorn_date(Date born_date) {
		this.born_date = born_date;
	}

	/**
	 * @param myPerson_Storage
	 * This method persists "this" instance and calls in Person_Storage the methid insertPerson (...) 
	 */
	public void persist(Person_Storage myPerson_Storage) {
		myPerson_Storage.insertPerson(person_id, first_name, last_name, born_date);
	}

	
	/**
	 * @param first_name
	 * @param last_name
	 * @param born_date
	 * @return sha1
	 * 
	 * This method invokes the java crypt library with attributes first_name, last_name, born_date. 
	 * The method outputs a sha1 id which is unique for each person.  
	 */
	private long generatePerson_ID(String first_name, String last_name, Date born_date) 
	{
		long sha1 = 0;
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        String phrase = first_name + last_name + born_date.toString()+ Long.toString(System.currentTimeMillis());
	        crypt.update(phrase.getBytes("UTF-8"));
	        sha1 = byteToLong(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    if (sha1 < 0) 
	    	sha1 = sha1*-1;  
	    return sha1;
	}

	private static long byteToLong(byte[]  by)
	{
		long value = 0;
		for (int i = 0; i < by.length; i++)
		{
		   value += ((long) by[i] & 0xffL) << (8 * i);
		}	
		return value;
	}

}
