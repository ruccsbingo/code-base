package com.yidian.wemedia.convertor;

import com.yidian.wemedia.bean.ScanPayResponseBean;
import org.apache.commons.digester3.Digester;

import java.io.InputStream;

/**
 * Created by zhangbing on 16/5/26.
 */
public class ScanPayResponseConvertor {
    public static ScanPayResponseBean convert(InputStream inputStream){
        ScanPayResponseBean response = null;
        try {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("xml", ScanPayResponseBean.class);

            digester.addBeanPropertySetter("xml/return_code", "return_code");
            digester.addBeanPropertySetter("xml/return_msg", "return_msg");
            digester.addBeanPropertySetter("xml/appid", "appid");
            digester.addBeanPropertySetter("xml/mch_id", "mch_id");
            digester.addBeanPropertySetter("xml/nonce_str", "nonce_str");
            digester.addBeanPropertySetter("xml/sign", "sign");
            digester.addBeanPropertySetter("xml/result_code", "result_code");
            digester.addBeanPropertySetter("xml/prepay_id", "prepay_id");
            digester.addBeanPropertySetter("xml/trade_type", "trade_type");
            digester.addBeanPropertySetter("xml/code_url", "code_url");

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

        } catch( Exception exc ) {
            exc.printStackTrace();
        }
        return response;
    }
}
