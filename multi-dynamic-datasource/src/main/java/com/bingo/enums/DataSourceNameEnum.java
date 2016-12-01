package com.bingo.enums;

/**
 * Created by zhangbing on 16/12/1.
 */
public enum DataSourceNameEnum {
    read("read", "从库"),
    write("write", "主库");

    private String beanName;
    private String propName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    DataSourceNameEnum(String beanName, String propName) {
        this.beanName = beanName;
        this.propName = propName;
    }
}
