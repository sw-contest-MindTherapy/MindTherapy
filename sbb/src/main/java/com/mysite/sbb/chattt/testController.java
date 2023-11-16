package com.mysite.sbb.chattt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chattt")
public class testController {

    @GetMapping("/chat2")
    public String chat2(){
        return "chat2";
    }
}
