package com.yidian.media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * Created by zhangbing on 16/12/15.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void main() throws Exception {

    }

    @Test
    public void restTemplate() throws Exception {

    }

    @Test
    public void run() throws Exception {
        Quote quote = restTemplate.getForObject(
                "http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        System.out.println(quote.toString());
    }

}