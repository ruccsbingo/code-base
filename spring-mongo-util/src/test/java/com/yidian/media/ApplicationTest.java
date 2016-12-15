package com.yidian.media;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/12/15.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoRepository repository;

    @Test
    public void main() throws Exception {

        repository.save(new User(1L, "didi", 30));

        // 创建三个User，并验证User总数
        userRepository.save(new User(1L, "didi", 30));
        userRepository.save(new User(2L, "mama", 40));
        userRepository.save(new User(3L, "kaka", 50));
        Assert.assertEquals(3, userRepository.findAll().size());

        // 删除一个User，再验证User总数
        User u = userRepository.findOne(1L);
        userRepository.delete(u);
        Assert.assertEquals(2, userRepository.findAll().size());

        // 删除一个User，再验证User总数
        u = userRepository.findByUsername("mama");
        userRepository.delete(u);
        Assert.assertEquals(1, userRepository.findAll().size());
    }
}