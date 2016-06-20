import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/3/2.
 */
public class KafkaReaderTest {

    KafkaReader kafkaReader;

    @Before
    public void init() throws Exception{
        kafkaReader = new KafkaReader();
    }

    @Ignore
    @Test
    public void testRead() throws Exception {
        kafkaReader.read();
    }
}