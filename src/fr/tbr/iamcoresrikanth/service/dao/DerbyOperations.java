package fr.tbr.iamcoresrikanth.service.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.tbr.iamcoresrikanth.datamodel.Identity;
import fr.tbr.iamcoresrikanth.exception.DAODeleteException;
import fr.tbr.iamcoresrikanth.exception.DAOExceptionsMessages;
import fr.tbr.iamcoresrikanth.exception.DAOInitializationException;
import fr.tbr.iamcoresrikanth.exception.DAOSaveException;
import fr.tbr.iamcoresrikanth.exception.DAOUpdateException;

import java.sql.ResultSetMetaData;


public class DerbyOperations
{
    private static final String dbURL = "jdbc:derby:C:/Users/USER/MyDB;create=true";
  
    
    private static Connection conn = null;
    private static Statement stmt = null;

    
    //connecting to database
    public static  void createConnection() throws DAOInitializationException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (SQLException e)
        {
            DAOInitializationException die = new DAOInitializationException(DAOExceptionsMessages.UNABLETOCONNECT);
            die.initCause(e);
            throw die;
        }
		
    }

    //To insert a new identity
    public static boolean insertIdentity(Identity identity) throws DAOSaveException
    {
    	boolean status = false;
        try
        {
            stmt = conn.createStatement();
            stmt.execute("insert into IDENTITIESDATA(IDENTITY_DISPLAYNAME , IDENTITY_EMAIL) values('"+identity.getDisplayName()+"', '"+identity.getEmail()+"')");
            status = true;
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
        	DAOSaveException dse = new DAOSaveException("problem...");
			dse.initCause(sqlExcept);
			throw dse;
        }
        return status;
    }
    
    //To update an identity
    public static boolean updateIdentity(Identity identity) throws DAOUpdateException, InstantiationException, IllegalAccessException, ClassNotFoundException, DAOInitializationException
    {
    	boolean status = false;
    	try
        {
           stmt = conn.createStatement();
           stmt.execute("UPDATE IDENTITIESDATA SET IDENTITY_DISPLAYNAME ='"+identity.getDisplayName()+"' , IDENTITY_EMAIL = '"+identity.getEmail()+"' WHERE IDENTITY_UID = "+identity.getUid().trim());
          
            status = true;
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            DAOUpdateException due = new DAOUpdateException("Couldnot Update...Try Again");
            due.initCause(sqlExcept);
            throw due;
        }
        return status;
    }
    
    //To delete an identity
    public static boolean deleteIdentity(String identity) throws DAODeleteException
    {
    	boolean status = false;
        try
        {
            stmt = conn.createStatement();
            stmt.execute("DELETE FROM IDENTITIESDATA WHERE IDENTITY_UID = "+identity.trim());
            status = true;
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
           DAODeleteException dde = new DAODeleteException("Couldnot Delete..try again");
           dde.initCause(sqlExcept);
           throw dde;
        }
        return status;
    }
    //To select  identity
    public static boolean selectIdentities()
    {
        boolean status = false;
    	try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM IDENTITIESDATA");
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
            	status = true;
            	int id = results.getInt(1);
                String dname = results.getString(2);
                String email = results.getString(3);
                System.out.println(id + "\t\t" + dname + "\t\t" + email);
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    	return status;
    }
    
    //Display all identities
   public static void Display() throws SQLException{
    	stmt=conn.createStatement();
    	ResultSet rs = stmt.executeQuery("Select * from identitiesdata");
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
		
    	
    }
    
    public static List<Identity> Search(Identity i) throws SQLException {
   
    	List<Identity> iden = new ArrayList<>();
    	stmt=conn.createStatement();
    	ResultSet rs = stmt.executeQuery("Select * from identitiesdata where IDENTITY_EMAIL= '"+i.getEmail()+"'");
		
		while(rs.next()){ 
				String dn = rs.getString("IDENTITY_DISPLAYNAME");
				String em=rs.getString("IDENTITY_EMAIL");
				String ui = rs.getString("IDENTITY_UID");
				Identity id1 = new Identity(dn,em,ui);
				iden.add(id1);				
			} 
  
		return iden;
		
		}
    	
     
    //closing connections
    public static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }

    }
}