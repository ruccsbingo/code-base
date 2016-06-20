package com.yidian.wemedia;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangbing on 16/5/25.
 */
public class NativeScanCode {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpPost.setEntity(new StringEntity("<xml>" +
                    "<appid>wx2c55358f2acf43f6</appid>" +
                    "<attach>支付测试</attach>" +
                    "<body>扫码支付测试</body>" +
                    "<mch_id>1344075701</mch_id>" +
                    "<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>" +
                    "<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>" +
                    "<out_trade_no>1415659994</out_trade_no>" +
                    "<spbill_create_ip>14.23.150.211</spbill_create_ip>" +
                    "<total_fee>1</total_fee>" +
                    "<trade_type>NATIVE</trade_type>" +
                    "<sign>D1F0FAFD513CD571E545E95B2F04039B</sign>" +
                    "</xml>", "UTF-8"));
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                String result = null;
                if (entity2 != null) {
                    System.out.println("Response content length: " + entity2.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity2.getContent()));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        System.out.println(text);
                        //连接成一个字符串
                        if (result != null)
                            result += text;
                        else
                            result = text;
                    }
                }
                EntityUtils.consume(entity2);
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
