package com.yidian.media;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by zhangbing on 16/12/15.
 */
public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
