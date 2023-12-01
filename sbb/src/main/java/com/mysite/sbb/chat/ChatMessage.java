package com.mysite.sbb.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    public enum MessageType{
        ENTER, TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}

//Enum은 열거형이라고도 불리며, 여러 개의 상수 값을 갖는 특별한 데이터 타입입니다. Enum을 사용하면 코드의 가독성을 높이고, 값의 범위를 제한하여 오류를 줄일 수 있습니다.
//enum을 쓰는 이유 : MessageType이라는 새로운 자료형 만들고 그에대한 값은 ENTER , TALK만 쓸 수 있게 해줌?
