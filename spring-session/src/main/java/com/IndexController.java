package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zhangbing on 16/12/8.
 */

@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
