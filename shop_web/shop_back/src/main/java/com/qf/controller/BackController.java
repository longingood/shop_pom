package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user qf
 * @date 2019/5/20 21:02
 */
@Controller
@RequestMapping(value = "backController")
public class BackController {

    @RequestMapping("/{topage}")
    public String toPage(@PathVariable("topage") String topage){

        return topage;
    }
}
