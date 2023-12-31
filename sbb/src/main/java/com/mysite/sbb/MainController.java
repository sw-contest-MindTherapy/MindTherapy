package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Controller
public class MainController {
    @GetMapping("/")
    public String root(){
        return "redirect:/home"; ///question/list URL로 페이지를 리다이렉트 하라는 명령어
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
//위 코드에서 return "redirect:/question/list"; 부분은 사용자가 웹 애플리케이션의 루트 URL("/")에 접속할 때, 대신 "/question/list" 페이지로 리다이렉트하도록 지시하고 있습니다.