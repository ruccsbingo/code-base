package com.yidian.wemedia.convertor;

import com.yidian.wemedia.bean.PayResponseBean;
import org.apache.commons.digester3.Digester;

import java.io.InputStream;

/**
 * Created by zhangbing on 16/5/26.
 */
public class PayResponseConvertor {
    public static PayResponseBean convert(InputStream inputStream){
        PayResponseBean response = null;
        try {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("xml", PayResponseBean.class);

            digester.addBeanPropertySetter("xml/return_code", "return_code");
            digester.addBeanPropertySetter("xml/return_msg", "return_msg");
            digester.addBeanPropertySetter("xml/mch_appid", "mch_appid");
            digester.addBeanPropertySetter("xml/appid", "appid");
            digester.addBeanPropertySetter("xml/mchid", "mchid");
            digester.addBeanPropertySetter("xml/device_info", "device_info");
            digester.addBeanPropertySetter("xml/nonce_str", "nonce_str");
            digester.addBeanPropertySetter("xml/result_code", "result_code");
            digester.addBeanPropertySetter("xml/partner_trade_no", "partner_trade_no");
            digester.addBeanPropertySetter("xml/payment_no", "payment_no");
            digester.addBeanPropertySetter("xml/payment_time", "payment_time");


//            Reader reader = new StringReader(
//                    "<?xml version='1.0' encoding='UTF-8'?>" +
//                            "<xml>" +
//                            "<appid>wx2c55358f2acf43f6</appid>" +
//                            "<attach>支付测试</attach>" +
//                            "<body>扫码支付测试</body>" +
//                            "<mch_id>1344075701</mch_id>" +
//                            "<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>" +
//                            "<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>" +
//                            "<out_trade_no>1415659992</out_trade_no>" +
//                            "<spbill_create_ip>14.23.150.211</spbill_create_ip>" +
//                            "<total_fee>1</total_fee>" +
//                            "<trade_type>NATIVE</trade_type>" +
//                            "<sign>F3A7723C5DDA17C9ACF189B41F10271D</sign>" +
//                            "</xml>");

            response = digester.parse(inputStream);

            System.out.println( response.toString() );

        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
