package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
//엔티티 클래스
//엔티티(Entity)란 데이터베이스 테이블과 매핑되는 자바 클래스이다. SBB는 질문과 답변을 할 수 있는 게시판-> 질문과 답변에 대한 엔티티가 필요.
//데베에 저장할 요소들
public class Question {

    @Id //즉 데이터베이스에서의 primary key로 지정한것과 동일
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가하여 저장됨
    private Integer id;

    @Column(length=200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy= "question", cascade=CascadeType.REMOVE)
    private List<Answer> answerList;
}
