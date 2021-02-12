package com.warzone.team08.exceptions;

import java.io.IOException;

/**
 * Exception to show required tag is absent in .map file
 * @author CHARIT
 *
 */
public class AbsentTagException extends IOException{

	/**
	 * parameterized constructor to call parent class constructor.
	 * @param p_str
	 */
	public AbsentTagException(String p_str) {
		super(p_str);
	}
}
