package Strings;
import java.util.Random;

// https://en.wikipedia.org/wiki/Knuth–Morris–Pratt_algorithm
public class Kmp {

	public static int[] prefixFunction(String s) {
		int[] p = new int[s.length()];
		int k = 0;
		for (int i = 1; i < s.length(); i++) {
			while (k > 0 && s.charAt(k) != s.charAt(i))
				k = p[k - 1];
			if (s.charAt(k) == s.charAt(i))
				++k;
			p[i] = k;
		}
		return p;
	}

	public static int kmpMatcher(String s, String pattern) {
		int m = pattern.length();
		if (m == 0)
			return 0;
		int[] p = prefixFunction(pattern);
		for (int i = 0, k = 0; i < s.length(); i++) {
			while (k > 0 && pattern.charAt(k) != s.charAt(i))
				k = p[k - 1];
			if (pattern.charAt(k) == s.charAt(i))
				++k;
			if (k == m)
				return i + 1 - m;
		}
		return -1;
	}

	// random tests
	public static void main(String[] args) {
		Random rnd = new Random(1);
		for (int step = 0; step < 10_000; step++) {
			String s = getRandomString(rnd, 100);
			String pattern = getRandomString(rnd, 5);
			int pos1 = kmpMatcher(s, pattern);
			int pos2 = s.indexOf(pattern);
			if (pos1 != pos2)
				throw new RuntimeException();
		}
	}

	static String getRandomString(Random rnd, int maxlen) {
		int n = rnd.nextInt(maxlen);
		char[] s = new char[n];
		for (int i = 0; i < n; i++)
			s[i] = (char) ('a' + rnd.nextInt(3));
		return new String(s);
	}
}
