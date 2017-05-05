package com.util;

import java.util.Random;
/***
 * 
 * @author opulent : Otp generator to generate the 5 digit otp
 *
 */
public class OTPGenerator {

	/**
	 * funtion return the 5 digit number that will randomly called
	 * @return the integer from 0 to last 5 digit number
	 */
	public static int gen() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }
}
