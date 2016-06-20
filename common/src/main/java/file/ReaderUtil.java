package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by zhangbing on 15/11/6.
 */
public class ReaderUtil {
    public static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        return reader;
    }
}
