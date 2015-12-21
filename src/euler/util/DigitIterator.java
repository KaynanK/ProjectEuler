package euler.util;
/**
 * DigitIterator.java 
 * 
 * Version: 
 *     $Id$ 
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * @author Josh Wight
 *
 */
public class DigitIterator {

	private int[] digits;

	public DigitIterator(int num) {
		digits = DigitCounter.digits(num);

		// sort digits with selection sort (copied from below, mostly)
		for (int i = 0; i < digits.length - 1; i++) {
			int minInd = i;
			for (int j = i + 1; j < digits.length; j++) {
				if (digits[j] < digits[minInd]) {
					minInd = j;
				}
			}
			int tmp = digits[i];
			digits[i] = digits[minInd];
			digits[minInd] = tmp;
		}

	}

	public DigitIterator(LargeInt num) {
		digits = new int[num.digits.size()];
		for (int i = 0; i < digits.length; i++) {
			digits[i] = num.digits.get(i);
		}

		// sort digits with selection sort (copied from below, mostly)
		for (int i = 0; i < digits.length - 1; i++) {
			int minInd = i;
			for (int j = i + 1; j < digits.length; j++) {
				if (digits[j] < digits[minInd]) {
					minInd = j;
				}
			}
			int tmp = digits[i];
			digits[i] = digits[minInd];
			digits[minInd] = tmp;
		}

	}

	private void calculateNext() {
		int loc = -1;
		int min = 0;
		// find the last index that has a digit which is followed somewhere by a
		// larger digit
		for (int i = digits.length - 1; i >= 0; i--) {
			boolean found = false;
			for (int j = i + 1; j < digits.length; j++) {
				if (digits[j] > digits[i]) {
					found = true;
					loc = i;
					min = j;
				}
			}
			if (found) {
				break;
			}
		}
		if (loc != -1) // if there are permutations left
		{
			// swap the digit at the found index with the smallest digit
			// following it and greater than it.
			for (int i = loc + 1; i < digits.length; i++) {
				if (digits[i] < digits[min] && digits[i] > digits[loc]) {
					min = i;
				}
			}
			int temp = digits[loc];
			digits[loc] = digits[min];
			digits[min] = temp;

			// sort the remaining digits with selection sort (come on, it's a
			// small set)
			for (int i = loc + 1; i < digits.length - 1; i++) {
				int minInd = i;
				for (int j = i + 1; j < digits.length; j++) {
					if (digits[j] < digits[minInd]) {
						minInd = j;
					}
				}
				int tmp = digits[i];
				digits[i] = digits[minInd];
				digits[minInd] = tmp;
			}

		}

	}

	public int next() {
		int num = 0;
		int scale = 1;
		for (int i = digits.length - 1; i >= 0; i--) {
			num += digits[i] * scale;
			scale *= 10;
		}
		calculateNext();
		return num;
	}

	public double nextDouble() {
		double num = 0;
		double scale = 1;
		for (int i = digits.length - 1; i >= 0; i--) {
			num += digits[i] * scale;
			scale *= 10;
		}
		calculateNext();
		return num;
	}

	public LargeInt nextLargeInt() {
		LargeInt i = new LargeInt(0);
		Integer[] d = new Integer[digits.length];
		for (int j = 0; j < digits.length; j++) {
			d[d.length - j - 1] = digits[j];
		}
		i.setDigits(d);
		i.trimZeroes();
		calculateNext();
		return i;
	}

}
