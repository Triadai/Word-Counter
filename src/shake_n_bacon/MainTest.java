package shake_n_bacon;

import providedCode.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class MainTest {

    private static void countWords(String file, DataCounter counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
    }

    private static DataCount[] getCountsArray(DataCounter counter) {
        SimpleIterator si = counter.getIterator();
        DataCount[] data = new DataCount[counter.getSize()];
        int count = 0;
        while (si.hasNext()) {
            data[count] = si.next();
            count++;
        }
        return data;
    }

    private static <E> void insertionSort(E[] array, Comparator<E> comparator) {
        for (int i = 1; i < array.length; i++) {
            E x = array[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (comparator.compare(x, array[j]) >= 0) {
                    break;
                }
                array[j + 1] = array[j];
            }
            array[j + 1] = x;
        }
    }

    public static void main(String[] args) {
        // Test StringComparator
        StringComparator sc = new StringComparator();
        String s1;
        String s2;
        for (int i = 0; i < 100000; i++) {
            s1 = nextSessionId();
            s2 = nextSessionId();
            if (!(sc.compare(s1, s2) == s1.compareTo(s2))) {
                System.out.println(s1 + " " + s2);
                System.out.println(sc.compare(s1, s2));
                System.out.println(s1.compareTo(s2));
            }
        }

        testDataCounters("romeo.txt");
        testDataCounters("hamlet.txt");
        testDataCounters("the-new-atlantis.txt");
    }

    //  test that both DataCounters get the same result
    private static void testDataCounters(String testFile) {
        DataCounter sepChain = new HashTable_SC(new StringComparator(),
                new StringHasher());
        DataCounter openAddr = new HashTable_OA(new StringComparator(),
                new StringHasher());
        countWords(testFile, sepChain);
        countWords(testFile, openAddr);
        DataCount[] counts = getCountsArray(sepChain);
        DataCount[] counts2 = getCountsArray(openAddr);
        insertionSort(counts, new DataCountStringComparator());
        insertionSort(counts2, new DataCountStringComparator());

        if (counts.length != counts2.length) {
            System.out.println("Not same size");
        }

        for (int i = 0; i < counts.length; i++) {
            if (!counts[i].data.equals(counts2[i].data)) {
                System.out.println(counts[i].data + " != " + counts2[i].data);
            }
            if (counts[i].count != counts2[i].count) {
                System.out.println(counts[i].count + " != " + counts2[i].count);
            }
        }
    }

    private static SecureRandom random = new SecureRandom();
    private static Random r = new Random();

    public static String nextSessionId() {
        return new BigInteger(130, random).toString(r.nextInt(20));
    }
}
