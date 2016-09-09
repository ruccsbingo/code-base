import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/9/5.
 */
public class PushTest {
    @Test
    public void push() throws Exception {
        Push push = new Push();
        String alias = "c46197f7-a22c-4cf8-be7f-b25b00edec21";
        String title = "您有一个新订单";
        String type = "1";
        String id = "1234";
//        push.push(MD5Util.MD5(alias).toUpperCase(), title, type, id);
        push.push("82ECD602473152B0FC0C67388D0DB5A6", title, type, id);

        System.out.println(MD5Util.MD5(alias).toString().toUpperCase());
    }

    @Test
    public void buildPushObject_android_tag_alertWithTitle() throws Exception {

        String alias = "60b75252-70b2-4d0d-8f66-da93eee3780c";
        String title = "1e31a0b8-17cb-411e-9f58-7ab0b4ece5f0";
        String type = "1";
        String id = "1234";

        Push push = new Push();

        System.out.println(push.buildPushObject_android_tag_alertWithTitle(alias, title, type, id));
    }


}