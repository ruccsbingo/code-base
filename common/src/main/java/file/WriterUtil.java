package file;

import java.io.*;

/**
 * Created by zhangbing on 15/11/6.
 */
public class WriterUtil {
    public static BufferedWriter getBufferedWriter(String fileName) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        return writer;
    }
}
