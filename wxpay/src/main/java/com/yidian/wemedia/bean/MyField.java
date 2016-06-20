package com.yidian.wemedia.bean;

import com.google.common.collect.ComparisonChain;

import java.lang.reflect.Field;

/**
 * Created by zhangbing on 16/5/27.
 */
public class MyField implements Comparable<MyField>{

    Field field;
    public MyField(Field field){
        this.field = field;
    }

    public String getName(){
        return this.field.getName();
    }

    public String getValue(Object obj) throws IllegalAccessException {
        return String.valueOf(this.field.get(obj));
    }

    @Override
    public int compareTo(MyField that) {
        if(that == null)
            return -1;
        return ComparisonChain.start()
                .compare(this.field.getName(), that.field.getName())
                .result();
    }
}
