import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Zheyuan Xu
 * @userid zxu322
 * @GTID 903132413
 * @version 1.2
 */
public class PatternMatching {
	/**
	 * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
	 * called failure function). Works better with small alphabets.
	 *
	 * Make sure to implement the failure table before implementing this method.
	 *
	 * @throws IllegalArgumentException if the pattern is null or of length 0
	 * @throws IllegalArgumentException if text or comparator is null
	 * @param pattern the pattern you are searching for in a body of text
	 * @param text the body of text where you search for pattern
	 * @param comparator you MUST use this for checking character equality
	 * @return list containing the starting index for each match found
	 */
	public static List<Integer> kmp(final CharSequence pattern,
		    final CharSequence text,
		    final CharacterComparator comparator) {
		if (pattern == null) {
			throw new IllegalArgumentException("pattern is null.");
		}
		if (pattern.length() == 0) {
			throw new IllegalArgumentException("pattern has length 0");
		}
		if (text == null) {
			throw new IllegalArgumentException("text is null.");
		}
		if (comparator == null) {
			throw new IllegalArgumentException("comparator is null.");
		}
		List<Integer> kmp = new ArrayList<>();
		if (pattern.length() > text.length()) {
			return kmp;
		}
		int[] table = buildFailureTable(pattern, comparator);
		int i = 0;
		int j = 0;
		while (i <= text.length() - pattern.length()) {
			while (j < pattern.length() && comparator.compare(text.charAt(i
					+ j), pattern.charAt(j)) == 0) {
				j++;
			}
			if (j == 0) {
				i++;
			} else {
				if (j == pattern.length()) {
					kmp.add(i);
				}
				int nextAlignment = table[j - 1];
				i = i + j - nextAlignment;
				j = nextAlignment;
			}
		}
		return kmp;
	}

	/**
	 * Builds failure table that will be used to run the Knuth-Morris-Pratt
	 * (KMP) algorithm.
	 *
	 * The table built should be the length of the input text.
	 *
	 * Note that a given index i will be the largest prefix of the pattern
	 * indices [0..i] that is also a suffix of the pattern indices [1..i].
	 * This means that index 0 of the returned table will always be equal to 0
	 *
	 * Ex. ababac
	 *
	 * table[0] = 0
	 * table[1] = 0
	 * table[2] = 1
	 * table[3] = 2
	 * table[4] = 3
	 * table[5] = 0
	 *
	 * If the pattern is empty, return an empty array.
	 *
	 * @throws IllegalArgumentException if the pattern or comparator is null
	 * @param pattern a {@code CharSequence} you're building a failure table for
	 * @param comparator you MUST use this for checking character equality
	 * @return integer array holding your failure table
	 */
	public static int[] buildFailureTable(final CharSequence pattern,
			final CharacterComparator comparator) {
		if (pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null");
		}
		if (comparator == null) {
			throw new IllegalArgumentException("Comparator cannot be null");
		}
		int[] failureTable = new int[pattern.length()];
		if (pattern.length() == 0) {
			return failureTable;
		}
		int i = 0;
		int j = 1;

		while (j != failureTable.length) {
			if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
				failureTable[j++] = i++ + 1;
			} else {
				if (i == 0) {
					failureTable[j++] = i;
				} else {
					i--;
				}
			}
		}
		return failureTable;
	}

	/**
	 * Boyer Moore algorithm that relies on last occurrence table. Works better
	 * with large alphabets.
	 *
	 * Make sure to implement the last occurrence table before implementing this
	 * method.
	 *
	 * @throws IllegalArgumentException if the pattern is null or of length 0
	 * @throws IllegalArgumentException if text or comparator is null
	 * @param pattern the pattern you are searching for in a body of text
	 * @param text the body of text where you search for the pattern
	 * @param comparator you MUST use this for checking character equality
	 * @return list containing the starting index for each match found
	 */
	public static List<Integer> boyerMoore(final CharSequence pattern,
			final CharSequence text, final CharacterComparator comparator) {
		if (pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null");
		}
		if (pattern.length() == 0) {
			throw new IllegalArgumentException("Pattern cannot have length 0");
		}
		if (text == null || comparator == null) {
			throw new IllegalArgumentException("Text/comparator cannot be null.");
		}
		List<Integer> bm = new ArrayList<>();
		if  (pattern.length() > text.length()) {
			return bm;
		}
		Map<Character, Integer> lastTable = buildLastTable(pattern);
		int i = pattern.length() - 1;
		int j = pattern.length() - 1;
		while (i < text.length()) {
			char tc = text.charAt(i);
			char pc = pattern.charAt(j);
			if (comparator.compare(tc, pc) != 0) {
				i += pattern.length() - Math.min(j,
						lastTable.getOrDefault(tc, -1) + 1);
				j = pattern.length() - 1;
			} else {
				if (j != 0) {
					i--;
					j--;
				} else {
					bm.add(i);
					i += pattern.length();
					j = pattern.length() - 1;
				}

			}
		}
		return bm;
	}

	/**
	 * Builds last occurrence table that will be used to run the Boyer Moore
	 * algorithm.
	 *
	 * Note that each char x will have an entry at table.get(x).
	 * Each entry should be the last index of x where x is a particular
	 * character in your pattern.
	 * If x is not in the pattern, then the table will not contain the key x,
	 * and you will have to check for that in your Boyer Moore implementation.
	 *
	 * Ex. octocat
	 *
	 * table.get(o) = 3
	 * table.get(c) = 4
	 * table.get(t) = 6
	 * table.get(a) = 5
	 * table.get(everything else) = null, which you will interpret in
	 * Boyer-Moore as -1
	 *
	 * If the pattern is empty, return an empty map.
	 *
	 * @throws IllegalArgumentException if the pattern is null
	 * @param pattern a {@code CharSequence} you are building last table for
	 * @return a Map with keys of all of the characters in the pattern mapping
	 *         to their last occurrence in the pattern
	 */
	public static Map<Character, Integer> buildLastTable(final CharSequence pattern) {
		if (pattern == null) {
			throw new IllegalArgumentException("Pattern cannot be null!");
		}
		Map<Character, Integer> lastTable = new HashMap<>();
		for (int i = 0; i < pattern.length(); i++) {
			lastTable.put(pattern.charAt(i), i);
		}
		return lastTable;
	}

	/**
	 * Prime base used for Rabin-Karp hashing.
	 * DO NOT EDIT!
	 */
	private static final int BASE = 137;

	/**
	 * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
	 * pattern and compares this hash to substrings of the text before doing
	 * character by character comparisons.
	 *
	 * When the hashes are equal and you do character comparisons, compare
	 * starting from the beginning of the pattern to the end, not from the end
	 * to the beginning.
	 *
	 * You must use the Rabin-Karp Rolling Hash for this implementation. The
	 * formula for it is:
	 *
	 * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
	 * value of the current character, and i is the index of the character
	 *
	 * For example: Hashing "bunn" as a substring of "bunny" with base 137 hash
	 * = b * 137 ^ 3 + u * 137 ^ 2 + n * 137 ^ 1 + n * 137 ^ 0 = 98 * 137 ^ 3 +
	 * 117 * 137 ^ 2 + 110 * 137 ^ 1 + 110 * 137 ^ 0 = 254203747
	 *
	 * Note that since you are dealing with very large numbers here, your hash
	 * will likely overflow, and that is fine for this implementation.
	 *
	 * Another key step for this algorithm is that updating the hashcode from
	 * one substring to the next one must be O(1). To update the hash:
	 *
	 *  remove the oldChar times BASE raised to the length - 1, multiply by
	 *  BASE, and add the newChar.
	 *
	 * For example: Shifting from "bunn" to "unny" in "bunny" with base 137
	 * hash("unny") = (hash("bunn") - b * 137 ^ 3) * 137 + y * 137 ^ 0 =
	 * (254203747 - 98 * 137 ^ 3) * 137 + 121 * 137 ^ 0 = 302928082
	 *
	 * Keep in mind that calculating exponents is not O(1) in general, so you'll
	 * need to keep track of what BASE^{m - 1} is for updating the hash.
	 *
	 * @throws IllegalArgumentException if the pattern is null or of length 0
	 * @throws IllegalArgumentException if text or comparator is null
	 * @param pattern a string you're searching for in a body of text
	 * @param text the body of text where you search for pattern
	 * @param comparator the comparator to use when checking character equality
	 * @return list containing the starting index for each match found
	 */
	public static List<Integer> rabinKarp(final CharSequence pattern,
			final CharSequence text, final CharacterComparator comparator) {
		if (pattern == null || pattern.length() == 0) {
			throw new IllegalArgumentException();
		}
		if (text == null || comparator == null) {
			throw new IllegalArgumentException();
		}
		List<Integer> list = new ArrayList<>();
		if (pattern.length() > text.length()) {
			return list;
		}

		int patternHash = generateHash(pattern, pattern.length());
		int textHash = generateHash(text, pattern.length());
		int ii = 0;
		while (ii <= text.length() - pattern.length()) {
			if (patternHash == textHash) {
				int j = 0;
				while (j < pattern.length() && comparator.compare(text.charAt(ii
						+ j), pattern.charAt(j)) == 0) {
					j++;
				}
				if (j == pattern.length()) {
					list.add(ii);
				}
			}

			if (ii + 1 <= text.length() - pattern.length()) {
				textHash = updateHash(textHash, pattern.length(),
						text.charAt(ii), text.charAt(ii + pattern.length()));
			}
			ii++;
		}
		return list;
	}

	/**
	 * This helper methods generates hash for Rabin-Karp.The formula is:
	 * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
	 * value of the current character, and i is the index of the character.
	 * 
	 * @param sub substring you are generating hash for
	 * @param len the length of substring you want to generate hash from
	 * @return hash of the substring parsed
	 */
	private static int generateHash(final CharSequence sub, final int len) {
		if (sub == null) {
			throw new IllegalArgumentException("nurrent is null");
		}
		if (len < 0 || len > sub.length()) {
			throw new IllegalArgumentException("length is wrong");
		}
		int hash = 0;
		for (int i = 0; i < len; i++) {
			hash += sub.charAt(i) * pow(BASE, len - 1 - i);
		}
		return hash;
	}

	/**
	 * This helper method updates hash for better reuse of existing code.
	 * The updating rule is as follows:
	 * remove the oldChar times BASE raised to the length - 1, multiply by
	 *  BASE, and add the newChar.
	 * For example: Shifting from "bunn" to "unny" in "bunny" with base 137
	 * hash("unny") = (hash("bunn") - b * 137 ^ 3) * 137 + y * 137 ^ 0 =
	 * (254203747 - 98 * 137 ^ 3) * 137 + 121 * 137 ^ 0 = 302928082
	 * @throws IllegalArgumentException if length is negative
	 * @param prevHash hash generated before
	 * @param length length of substring
	 * @param prevChar previous character that is removed from hashed string
	 * @param toAdd new character that is intended to be added
	 * @return updated hash of the substring
	 */
	private static int updateHash(final int prevHash,
			final int length, final char prevChar,
			final char toAdd) {
		if (length < 0) {
			throw new IllegalArgumentException("negative length");
		}
		return (prevHash - prevChar * pow(BASE, length - 1)) * BASE + toAdd;
	}

	/**
	 * power function for the Rabin-Karp
	 * @throws IllegalArgumentException if both base and exponential are 0
	 * @throws IllegalArgumentException if exponential is negative
	 * @param base
	 * @param exp
	 * @return result of the calculation
	 */
	private static int pow(final int base, 
			final int exp) {
		if (exp < 0) {
			throw new 
			IllegalArgumentException("negative exponential!");
		} else if (base == 0 && exp == 0) {
			throw new IllegalArgumentException(
					"base and exponent cannot be both 0");
		} else if (exp == 0) {
			return 1;		
		} else if (exp == 1) {
			return base;
		}
		int exphalf = pow(base, exp / 2);
		if (exp % 2 == 0) {
			return exphalf * exphalf;
		} else {
			return exphalf * pow(base, (exp / 2) + 1);
		}
	}
}