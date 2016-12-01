package com.bingo.aspect;

import com.bingo.dao.AccountDao;
import com.bingo.model.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/11/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceAopTest {
    @Autowired
    AccountDao accountDao;

    @Test
    public void setReadDataSourceType() throws Exception {
        Account account = accountDao.getByPK(30923421l);
        System.out.println(account.getApproverName());

        account.setApproverName("bingobingo");
        account.setAccount(1);
        account.setArticle(1);
        account.setbProfile(1);
        account.setGrading(1);
        account.setInvite(1);
        account.setVideo(1);
        account.setUid(12345l);
        account.setOffWorkTime("123");
        account.setOnWorkTime("234");
        account.setUpdatedTime(34567l);
        account.setCreatedTime(456789l);
        account.setQuality(3);
        accountDao.insert(1l, account);

        account.setApproverName("bingobingobingo");
        account.setAccount(1);
        account.setArticle(1);
        account.setbProfile(1);
        account.setGrading(1);
        account.setInvite(1);
        account.setVideo(1);
        account.setUid(12345l);
        account.setOffWorkTime("123");
        account.setOnWorkTime("234");
        account.setUpdatedTime(34567l);
        account.setCreatedTime(456789l);
        account.setQuality(3);
        accountDao.insert(2l, account);
    }

    @Test
    public void setWriteDataSourceType() throws Exception {

    }
}