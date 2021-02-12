package com.warzone.team08.exceptions;

/**
 * Exception to show that map file is invalid.
 * @author CHARIT
 */
public class InvalidMapException extends Exception{

	/**
	 * Constructor to set error message.
	 * @param p_str error message.
	 */
	public InvalidMapException(String p_str)
	{
		super(p_str);
	}
}
