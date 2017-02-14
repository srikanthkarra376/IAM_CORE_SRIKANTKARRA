package fr.tbr.iamcoresrikanth.service.authentication;

public class AuthenticationService {

	public boolean authenticate(String username, String password) {
		// FIXME implement this authentication method
		//return DerbyOperations.validateUser(username.trim(),password.trim());
	return ("adm".equals(username) && "pwd".equals(password));
	/*		return true;
		}else{
			return false;
		}*/
		
	}

}
