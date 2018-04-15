package com.cedex.numbers;

import java.util.Random;

public class Randomalizator {

	/**
	 * get random int value up to max Random Value
	 * 
	 * @param maxRandomValue
	 *            - max random value
	 * @return random int value
	 */
	public static int getRandomNumber(int maxRandomValue) {
		if (maxRandomValue > 0) {
			Random randomGenerator = new Random();
			return randomGenerator.nextInt(maxRandomValue);
		} else {
			return 1;
		}

	}
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param minRandomValue -
	 *                        Minimum value
	 * @param maxRandomValue - 
	 *                        Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see Random#nextInt(int)
	 */
	public static int getRandomNumberInRange(int minRandomValue,int maxRandomValue) {
		 // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((maxRandomValue - minRandomValue) + 1) + minRandomValue;

	    return randomNum;
	}
}
