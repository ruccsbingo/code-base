package com.yidian.wemedia.convertor;

import com.yidian.wemedia.bean.ScanEventBean;
import org.apache.commons.digester3.Digester;

import java.io.InputStream;

/**
 * Created by zhangbing on 16/5/27.
 */
public class ScanEventConvertor {
    public static ScanEventBean convert (InputStream inputStream){

        ScanEventBean response = null;
        try {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("xml", ScanEventBean.class);

            digester.addBeanPropertySetter("xml/ToUserName", "toUserName");
            digester.addBeanPropertySetter("xml/FromUserName", "fromUserName");
            digester.addBeanPropertySetter("xml/CreateTime", "createTime");
            digester.addBeanPropertySetter("xml/MsgType", "msgType");
            digester.addBeanPropertySetter("xml/Event", "event");
            digester.addBeanPropertySetter("xml/EventKey", "eventKey");
            digester.addBeanPropertySetter("xml/Ticket", "ticket");

            response = digester.parse(inputStream);

            System.out.println(response.toString());

        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static ScanEventBean convert (String data){

        ScanEventBean response = null;
        try {
            Digester digester = new Digester();
            digester.setValidating(false);

            digester.addObjectCreate("xml", ScanEventBean.class);

            digester.addBeanPropertySetter("xml/ToUserName", "toUserName");
            digester.addBeanPropertySetter("xml/FromUserName", "fromUserName");
            digester.addBeanPropertySetter("xml/CreateTime", "createTime");
            digester.addBeanPropertySetter("xml/MsgType", "msgType");
            digester.addBeanPropertySetter("xml/Event", "event");
            digester.addBeanPropertySetter("xml/EventKey", "eventKey");
            digester.addBeanPropertySetter("xml/Ticket", "ticket");

            response = digester.parse(data);

            System.out.println(response.toString());

        } catch(Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
