package writeupExperiment;

import providedCode.FileWordReader;

import java.io.*;

public class TimeTest {

    public static void main(String[] args) {

        // get the names of all the text files
        // the files are downloaded with DownloadFiles.java
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();
        String name;
        PrintWriter writer = null;

        try {
            writer = new PrintWriter("data.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println("File|SC Insert (ms)|SC Get (ms)|OA Insert (ms)|OA Get (ms)|Word Count");

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                name = listOfFiles[i].getName();
                if (name.contains(".txt") && !name.equals("data.txt")) {
                    // run the test on every text file
                    writer.println(test(name));
                }
            }
        }

        writer.close();

    }

    // count the number of words in file
    private static int wordCount(String file) {
        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            int count = 0;
            while (word != null) {
                count++;
                word = reader.nextWord();
            }

            return count;
        } catch (IOException e) {
            System.err.println("Error processing " + file + " " + e);
            System.exit(1);
        }
        return -1;
    }

    private static String test(String file) {
        // test both tables and record the time
        double[] chaining = time(new String[]{"-s", file});
        double[] openAddr = time(new String[]{"-o", file});
        return file + "|" + chaining[0] + "|" + chaining[1] + "|" + openAddr[0] + "|" + openAddr[1] + "|" + wordCount(file);
    }

    private static double[] time(String[] args) {
        double[] totalTime = {0, 0};
        int tests = 6;
        int warmup = 1;
        double[] times;
        for (int i = 0; i < tests; i++) {
            times = WordCount.main(args);
            if (warmup <= i) {
                totalTime[0] += times[0];
                totalTime[1] += times[1];
            }
        }

        totalTime[0] /= (tests - warmup);
        totalTime[1] /= (tests - warmup);

        return totalTime;
    }

}
