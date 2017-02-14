package fr.tbr.iamcoresrikanth.querriessql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;

public class queryDB {

	private static final String dbURL = "jdbc:derby:C:/Users/USER/MyDB;create=true";
	public static final String str = "select * from Identities";
	
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = DriverManager.getConnection(dbURL);
		Statement st=con.createStatement();
		ResultSet rs = st.executeQuery(str);
		ResultSetMetaData rsm = rs.getMetaData();
		int cnt= rsm.getColumnCount();
		for(int x=1;x<=cnt;x++){
			System.out.format("%20s",rsm.getColumnName(x)+ " |");
			
		}
		while(rs.next()){ 
			System.out.println("");
			for(int x=1;x<=cnt;x++){
				System.out.format("%20s",rs.getString(x)+ "|");
				
				
			}
		}
		
		if(st!=null) st.close();
		if(con!=null)con.close();
		

	}

}