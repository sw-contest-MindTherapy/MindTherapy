package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {


    @NotEmpty(message = "제목은 필수사항입니다.")
    @Size(max=200)
    private String subject;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
//화면에서 전달되는 입력값을 검증하는 클래스