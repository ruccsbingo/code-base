package com.bingo.enums;


/**
 * Created by zhangbing on 16/11/26.
 */
public enum DataSourceType {
    read("read", "从库"), write("write", "主库");
    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
