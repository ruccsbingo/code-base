import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by zhangbing on 15/11/5.
 */
public class OrderingTest {

    @Test
    public void testOrdering() throws Exception{
        Ordering<String> byLengthOrdering = Ordering.natural();
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("b");

        assertTrue(byLengthOrdering.isOrdered(list));
    }
}
