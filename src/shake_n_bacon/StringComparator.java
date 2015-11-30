package shake_n_bacon;

import providedCode.*;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 * <p/>
 * <p/>
 * 1. This comparator is used by the provided code for both data-counters
 * and sorting. Because of how the output must be sorted in the case of
 * ties, your implementation should return a negative number when the
 * first argument to compare comes first alphabetically.
 * <p/>
 * 2. Do NOT use any String comparison provided in Java's standard
 * library; the only String methods you should use are length and charAt.
 * <p/>
 * 3. You can use ASCII character codes to easily compare characters
 * http://www.asciitable.com/
 * <p/>
 * 4. When you are unsure about the ordering, you can try
 * str1.compareTo(str2) to see what happens. Your
 * stringComparator.compare(str1, str2) should behave the same way as
 * str1.compareTo(str2). They don't have to return the same value, but
 * their return values should have the same sign (+,- or 0).
 */
public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        // case 1 they are the same
        // return 0
        // case 2 the beginning letters differ 'aab' and 'baadfafs'
        // return ascii value of a - b
        // case 3 the beginning letters are the same and the second word is longer 'ben' and 'benjamin'
        // return s1.length - s2.length
        // case 4 the beginning letters are the same and the first word is longer 'hotdog' and 'hot'
        // return s1.length - s2.length

        int smallestLength = s1.length();
        if (s2.length() < s1.length()) {
            smallestLength = s2.length();
        }

        for (int i = 0; i < smallestLength; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                // case 2
                return ((int) s1.charAt(i)) - ((int) s2.charAt(i));
            }
        }
        // case 1, 3, and 4
        return s1.length() - s2.length();
    }

}
