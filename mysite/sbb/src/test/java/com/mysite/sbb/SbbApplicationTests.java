package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJap(){

		for(int i=0;i<=300;i++){
			String subject=String.format("테스트 데이터입니다:[%03d]",i);
			String content="내용임";
			this.questionService.create(subject,content);
		}
	}

}
/*답변 데이터 처리를 위해서는 답변 리포지터리가 필요하므로 AnswerRepository 객체를 @Autowired로 주입했다.
답변 데이터를 생성하려면 질문 데이터가 필요하므로 우선 질문 데이터를 구해야 한다.
id가 2인 질문 데이터를 가져온 다음 Answer 엔티티의 question 속성에 방금 가져온 질문 데이터를 대입해(a.setQuestion(q)) 답변 데이터를 생성했다.
Answer 엔티티에는 어떤 질문에 해당하는 답변인지 연결할 목적으로 question 속성이 필요하다*/
