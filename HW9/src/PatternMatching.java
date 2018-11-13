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
 * @version 2.0
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
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null pattern, invalid!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Null text, invalid!");
        }
        List<Integer> arr = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return arr;
        }
        int[] t = buildFailureTable(pattern, comparator);
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
                    arr.add(i);
                }
                int next = t[j - 1];
                i = i + j - next;
                j = next;
            }
        }
        return arr;
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
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern, invalid!");
        }
        int[] t = new int[pattern.length()];
        if (pattern == null || pattern.length() == 0) {
            return t;
        }
        int i = 0;
        int j = 1;
        t[0] = 0;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                i++;
                t[j] = i;
                j++;
            } else if (i == 0) {
                t[j] = 0;
                j++;
            } else {
                i = t[i - 1];
            }
        }
        return t;
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
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null pattern, invalid!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Null text, invalid!");
        }
        List<Integer> arr = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return arr;
        }
        int i = 0;
        int j;
        int shift;
        Map<Character, Integer> last = buildLastTable(pattern);
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            Character key = text.charAt(i + j);
            while (j >= 0 && comparator.compare(key, pattern.charAt(j)) == 0) {
                j--;
                if (j >= 0) {
                    key = text.charAt(i + j);
                }
            }
            if (j == -1) {
                arr.add(i);
                i++;
            } else {
                if (last.containsKey(key)) {
                    shift = last.get(key);
                } else {
                    shift = -1;
                }
                if (shift < j) {
                    i += (j - shift);
                } else {
                    i++;
                }
            }
        }
        return arr;
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
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Null pattern, invalid!");
        } else {
            HashMap<Character, Integer>
                table = new HashMap<Character, Integer>();
            for (int i = 0; i < pattern.length(); i++) {
                table.put(pattern.charAt(i), i);
            }
            return table;
        }
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

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
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow. However, you will not need to handle this case.
     * You may assume there will be no overflow.
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("Null pattern, invalid!");
        }
        if (text == null) {
            throw new IllegalArgumentException("Null text, invalid!");
        }
        List<Integer> arr = new ArrayList<>();
        if (pattern.length() > text.length()) {
            return arr;
        }
        int pHash = hash(pattern, pattern.length());
        int tHash = hash(text, pattern.length());
        int i = 0;
        while (i <= text.length() - pattern.length()) {
            if (pHash == tHash) {
                int j = 0;
                while (j < pattern.length() && comparator.compare(text.charAt(i
                        + j), pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    arr.add(i);
                }
            }
            if (i + 1 <= text.length() - pattern.length()) {
                tHash = update(tHash, text.charAt(i),
                    pattern.length(),
                    text.charAt(i + pattern.length()));
            }
            i++;
        }
        return arr;
    }
    
    /**
     * helper method that updates the hash value as the matching
     * goes on
     * @param old --the old hash value of the text
     * @param oldChar --the old character sequence already checked
     * @param length --length of the pattern
     * @param newChar --the unchecked part of the character sequence
     * @return the updated hash value
     */
    public static int update(int old, char oldChar, int length,
        char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("Negative length!");
        }
        return (old - oldChar * pow(BASE, length - 1)) * BASE + newChar;
    }

    /**
     * The helper method to generate a hash code based on the character
     * sequence and length
     * @param c --the character sequence parsed in
     * @param length --the length of the character sequence
     * @return the hash code generated
     */
    public static int hash(CharSequence c, int length) {
        if (c == null) {
            throw new IllegalArgumentException("Null character sequence!");
        }
        if (length < 0 || length > c.length()) {
            throw new IllegalArgumentException("invalid length");
        }
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash += c.charAt(i) * pow(BASE, length - 1 - i);
        }
        return hash;
    }

    /**
     * helper method of hash, helps generate a hash code
     * @param b --the exponential base
     * @param e --the exponential power
     * @return the calculate result of exponentiation
     */
    private static int pow(int b, int e) {
        if (e < 0) {
            throw new IllegalArgumentException("Negative exponent, invalid!");
        } else if (e == 0) {
            return 1;
        } else if (e == 1) {
            return b;
        }
        int sqrt = pow(b, e / 2);
        if (e % 2 == 0) {
            return sqrt * sqrt;
        } else {
            return sqrt * pow(b, (e / 2) + 1);
        }
    }
}
