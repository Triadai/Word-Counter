package shake_n_bacon;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by benawad on 11/28/15.
 */
public class Main {
    public static void main(String[] args) {
        StringComparator sc = new StringComparator();
        String s1 = "";
        String s2 = "";
        for (int i = 0; i < 100000; i++) {
            s1 = nextSessionId();
            s2 = nextSessionId();
            if (!(sc.compare(s1, s2) == s1.compareTo(s2))) {
                System.out.println(s1 + " " + s2);
                System.out.println(sc.compare(s1, s2));
                System.out.println(s1.compareTo(s2));
            }
        }
    }

    private static SecureRandom random = new SecureRandom();
    private static Random r = new Random();

    public static String nextSessionId() {

        return new BigInteger(130, random).toString(r.nextInt(20));
    }
}
