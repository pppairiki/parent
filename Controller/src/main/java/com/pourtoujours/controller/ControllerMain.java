package com.pourtoujours.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerMain {
    public ControllerMain() {
    }

    //路由匹配，使用index.jsp进行页面渲染
    @RequestMapping(value="/",method= RequestMethod.GET)
    public String Index() {
        return "redirect:index.html";
            }
}
