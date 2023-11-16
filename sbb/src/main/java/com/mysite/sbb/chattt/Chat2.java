package com.mysite.sbb.chattt;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data //Lombok 프로젝트의 어노테이션, getter,setter등을 자동으로 생성해줌
@Document(collection = "chat") //몽고DB에서 사용 되는거고 chat이라는 콜렉션을 쓴다는 의미.
//Chat이라는 클레스를 DB컬렉션으로 매핑해줌.
public class Chat2 {
    @Id
    private String id;
    private String msg;
    private String sender; // 보내는 사람.
    private  String receiver; // 받는 사람(방의 개념이 있으면 사실 받는사람의 의미는 없고 귓속말에 쓰임 )
    private Integer roomNum; // 방번호
    private LocalDateTime createdAt;
}
//채팅을 하면서 위에 @Document(colletion = "chat") 에다가 채팅한 내용을 집어넣을거임.
