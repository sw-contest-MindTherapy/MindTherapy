package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
//엔티티클래스
@Getter
@Setter
@Entity
public class Answer {

    @Id //즉 데이터베이스에서의 primary key로 지정한것과 동일
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 1씩 증가하여 저장됨
    private Integer id;


    @Column(columnDefinition = "TEXT")
    private String content;
    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private Question question;
}
