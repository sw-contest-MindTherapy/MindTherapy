package com.mysite.sbb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SbbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbbApplication.class, args);
	}

}

// 자바를 실행하면 스프링만 실해해주는데 나머지 어떻게 사용할거냐? -> 이때 스프링 컨테이너가 생기고,
//
// @Bean으로 등록해논 알멩이들을 찾아서 스프링이 알아서 실행해주는 구조