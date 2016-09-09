import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhangbing on 16/9/3.
 */
public class Push {

    Logger LOG = LoggerFactory.getLogger(Push.class);

    String url = "https://api.jpush.cn/v3/push";
    String masterSecret = "6988248bccd8fe4ee3eceee1";
    String appKey = "94d86805008315b110d333d2";

    public Push() {
    }

    public void push(String alias, String title, String type, String id){

        JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

        // For push, all you need do is to build PushPayload object.
        PushPayload payload = buildPushObject_android_tag_alertWithTitle(alias, title, type, id);

        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            LOG.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            LOG.error("Should review the error, and fix the request", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alias, String title, String type, String id) {
        Map<String, String> map = Maps.newHashMap();
        map.put("type", type);
        map.put("id", id);
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(
                                IosNotification.newBuilder()
                                        .setAlert(title)
                                        .autoBadge()
                                        .setSound("default")
                                        .addExtras(map)
                                        .build()
                        ).build()
                ).build();
    }
}
