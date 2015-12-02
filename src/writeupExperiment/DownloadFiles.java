package writeupExperiment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by benawad on 12/1/15.
 */
public class DownloadFiles {
    public static void main(String[] args) {
        try {
            int filesToDownload = 100;
            Document doc = Jsoup.connect("http://www.textfiles.com/etext/FICTION/").get();
            // get all anchor tags on the page (all of them are book links)
            Elements as = doc.getElementsByTag("a");
            String link;
            String name;
            String base = "http://www.textfiles.com/etext/FICTION/";
            for (int i = 0; i < as.size(); i++) {
                if (i > filesToDownload) {
                    break;
                }
                name = as.get(i).attr("href");
                // get the link to the text
                link = base + name;
                if (!name.contains(".txt")) {
                    // make naming uniform
                    name += ".txt";
                }
                // download the text
                URL website = new URL(link);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                // save the text
                FileOutputStream fos = new FileOutputStream(name);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
