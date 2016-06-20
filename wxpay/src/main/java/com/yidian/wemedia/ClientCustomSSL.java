/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.yidian.wemedia;

import com.google.common.collect.Maps;
import com.yidian.wemedia.util.MD5Util;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

    public final static void main(String[] args) throws Exception {
//        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
        KeyStore trustStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("/Users/zhangbing/code/cert/apiclient_cert.p12"));
        try {
            trustStore.load(instream, "1344075701".toCharArray());
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(trustStore, "1344075701".toCharArray())
                .build();

        // Trust own CA and all self-signed certs
//        SSLContext sslcontext = SSLContexts.custom()
//                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
//                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

//        sendGet(httpclient);

        sendPost(httpclient);
    }

    public static void sendGet(CloseableHttpClient httpclient) throws IOException{

        try {

            HttpGet httpget = new HttpGet("https://localhost/");

            System.out.println("executing request" + httpget.getRequestLine());

            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                HttpEntity entity = response.getEntity();

                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    public static String MD5Sign(Map<String, Object> treeMap, String key) throws NoSuchAlgorithmException {
        StringBuffer buffer = new StringBuffer();
        for(Map.Entry<String, Object> entry : treeMap.entrySet()){
            if(!"sign".equals(entry.getKey())) {
                buffer.append(entry.getKey() + "=" + entry.getValue());
                buffer.append("&");
            }
        }
        buffer.append(key);
        String plainText =  buffer.toString();

        return MD5Util.MD5(plainText);
    }

    public static class Order{
        Integer amount;
        String checkName;
        String desc;
        String mchAppid;
        String mchid;
        String nonceStr;
        String openid;
        String partnerTradeNo;
        String realUserName;
        String spbillCreateIp;
        String sign;
    }

    public static Map<String, Object> generateData(Order order){
        Map<String, Object> treeMap = Maps.newTreeMap();
        treeMap.put("amount", order.amount);
        treeMap.put("check_name", order.checkName);
        treeMap.put("desc", order.desc);
        treeMap.put("mch_appid", order.mchAppid);
        treeMap.put("mchid", order.mchid);
        treeMap.put("nonce_str", order.nonceStr);
        treeMap.put("openid", order.openid);
        treeMap.put("partner_trade_no", order.partnerTradeNo);
        treeMap.put("re_user_name", order.realUserName);
        treeMap.put("spbill_create_ip", order.spbillCreateIp);
        treeMap.put("sign", order.sign);
        return treeMap;
    }

    public static String generateData(Map<String, Object> treeMap){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        for(Map.Entry<String, Object> entry : treeMap.entrySet()){
            buffer.append("<" + entry.getKey() + ">");
            buffer.append(entry.getValue());
            buffer.append("</" + entry.getKey() + ">");
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

    public static String generateData() throws NoSuchAlgorithmException {
        Order order = new Order();
        String key = "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9";
        Map<String, Object> map = generateData(order);
        String sign = MD5Sign(map, key);
        map.put("sign", sign);

        return generateData(map);
    }

    public static void sendPost(CloseableHttpClient httpclient) throws IOException {

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
        httpPost.setEntity(new StringEntity("<xml>" +
                "<amount>1000</amount>" +
                "<check_name>NO_CHECK</check_name>" +
                "<desc>test</desc>" +
                "<mch_appid>wx2c55358f2acf43f6</mch_appid>" +
                "<mchid>1344075701</mchid>" +
                "<nonce_str>3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS</nonce_str>" +
                "<openid>o5UqIxAVUcsZLBls3sMLDVYZKcBE</openid>" +
                "<partner_trade_no>100000982014120919623</partner_trade_no>" +
                "<re_user_name>张兵</re_user_name>" +
                "<spbill_create_ip>127.0.0.1</spbill_create_ip>" +
                "<sign>419523EBE31ED5B3ECF11CED54B30BDC</sign>" +
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
    }
}
