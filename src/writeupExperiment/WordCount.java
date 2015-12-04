package writeupExperiment;

import providedCode.*;
import shake_n_bacon.HashTable_OA;
import shake_n_bacon.HashTable_SC;
import shake_n_bacon.StringComparator;
import shake_n_bacon.StringHasher;

import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

    // Implement a method that returns an array of DataCount objects
    // containing each unique word.
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

    private static long countWords(String file, DataCounter counter) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            long st = System.currentTimeMillis();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
            return System.currentTimeMillis() - st;
        } catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
        return -1;
    }

    private static void usage() {
        System.err
                .println("Usage: [-s | -o] <filename of document to analyze>");
        System.err.println("-s for hashtable with separate chaining");
        System.err.println("-o for hashtable with open addressing");
        System.exit(1);
    }

    /**
     * Entry of the program
     *
     * @param args the input arguments of this program
     */
    public static double[] main(String[] args) {
        if (args.length != 2) {
            usage();
        }

        String firstArg = args[0].toLowerCase();
        DataCounter counter = null;
        if (firstArg.equals("-s")) {
            counter = new HashTable_SC(new StringComparator(),
                    new StringHasher());
        } else if (firstArg.equals("-o")) {
            counter = new HashTable_OA(new StringComparator(),
                    new StringHasher());
        } else {
            usage();
        }

        double insert = countWords(args[1], counter);
        DataCount[] counts = getCountsArray(counter);

        long st = System.currentTimeMillis();
        for (DataCount dc : counts) {
            counter.getCount(dc.data);
        }
        double avgLookup = (System.currentTimeMillis() - st) / (double) counter.getSize();

        return new double[]{insert, avgLookup};
    }

}
