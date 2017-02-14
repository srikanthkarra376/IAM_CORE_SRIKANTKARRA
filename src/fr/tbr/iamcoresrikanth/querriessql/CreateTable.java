package fr.tbr.iamcoresrikanth.querriessql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
	private static final String dbURL = "jdbc:derby:C:/Users/USER/MyDB;create=true";
	private static final String Driver="org.apache.derby.jdbc.EmbeddedDriver";
	
	private static String query = "Create table identitiesdata(IDENTITY_UID smallint generated always as IDENTITY(start with 100,increment by 1) primary key,IDENTITY_DISPLAYNAME varchar(10),IDENTITY_EMAIL varchar(20))";
	private static String query2 = "Create table userAuth(user_name varchar(10),pass varchar(10))";
	
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
			Class.forName(Driver).newInstance();
			Connection con = DriverManager.getConnection(dbURL);
			
			Statement st=con.createStatement();
			st.execute(query);
			st.execute(query2);
			
			System.out.println("Tables created succesfully");
		if(st!=null) st.close();
		if(con!=null)con.close();
		
	}
}


