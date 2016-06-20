package com.yidian.wemedia.util;

import com.google.common.collect.Lists;
import com.yidian.wemedia.bean.MyField;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangbing on 16/5/27.
 */
public class BeanUtil {

    public static List<MyField> getMyFieldList(Class myClass){
        Field[] fields = myClass.getFields();
        List<MyField> list = Lists.newArrayList();

        for(Field field : fields){
            list.add(new MyField(field));
        }

        Collections.sort(list);
        return list;
    }

    public static String toString(List<MyField> myFieldList, Object obj){

        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");

        for(MyField myField : myFieldList){
            buffer.append("<" + myField.getName() + ">");
            try {
                buffer.append(myField.getValue(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            buffer.append("</" + myField.getName() + ">");
        }
        buffer.append("</xml>");
        return buffer.toString();
    }

}
