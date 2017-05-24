package com.java.accounts;

import java.io.Serializable;

/**
 * 
 */

/**
 * @author Joshua Jadulco
 * @date March 2, 2017
 *
 */
public interface Taxable extends Serializable{
	void calculateTax( double taxRate );
	double getTaxAmount( );
	String createTaxStatement( );
}
