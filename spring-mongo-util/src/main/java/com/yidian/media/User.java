package com.yidian.media;

import org.springframework.data.annotation.Id;

/**
 * Created by zhangbing on 16/12/15.
 */
public class User {
    @Id
    private Long id;
    private String username;
    private Integer age;
    public User(Long id, String username, Integer age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
