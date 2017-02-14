/**
 * 
 */
package fr.tbr.iamcoresrikanth.exception;

/**
 * 
 *
 */
public class DAOUpdateException extends Exception {
	
	private  String update;

	public DAOUpdateException(String message) {
		
		this.update = message;
}
	public String getUpdate() {
		return update;
	}
	
}
