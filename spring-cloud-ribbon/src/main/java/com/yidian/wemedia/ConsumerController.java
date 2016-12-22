package com.yidian.wemedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by zhangbing on 16/12/19.
 */

@RestController
public class ConsumerController {
//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    private ComputeService computeService;


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return computeService.addService();
    }
}
