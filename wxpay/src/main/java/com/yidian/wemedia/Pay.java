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

import com.yidian.wemedia.bean.PayRequestBean;
import com.yidian.wemedia.bean.PayResponseBean;
import com.yidian.wemedia.convertor.PayResponseConvertor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class Pay {

    public static CloseableHttpClient getHttpClient()
            throws UnrecoverableKeyException,
            NoSuchAlgorithmException,
            KeyStoreException,
            KeyManagementException,
            IOException,
            CertificateException {
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
        return httpclient;
    }

    public static void sendPost(CloseableHttpClient httpclient) throws IOException, NoSuchAlgorithmException, IllegalAccessException {

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
        PayRequestBean payRequestBean = new PayRequestBean("1000",
                "NO_CHECK",
                "test",
                "wx2c55358f2acf43f6",
                "1344075701",
                "3PG2J4ILTKCH16CQ2502SI8ZNMTM67VS",
                "o5UqIxAVUcsZLBls3sMLDVYZKcBE",
                "100000982014120919626",
                "张兵",
                "127.0.0.1",
                "1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9");
        httpPost.setEntity(new StringEntity(payRequestBean.toString(), "UTF-8"));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            if (entity != null) {
                PayResponseBean payResponseBean = PayResponseConvertor.convert(entity.getContent());
            }
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }

    public final static void main(String[] args) throws Exception {

        CloseableHttpClient httpclient = getHttpClient();
        sendPost(httpclient);
    }
}
