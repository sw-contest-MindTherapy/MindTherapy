package com.mysite.sbb.chattt;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface Chat2Repository extends ReactiveMongoRepository<Chat2,String> {
    @Tailable // 커서를 안닫고 유지함, 몽고디비 cmd창으로 데이터 삽입,탐색하는 과정을 생각해보면, 그 과정이 한번이 아니라
    //여러번 해줄수있게 상태윶를 해주는거임.
    @Query("{sender:?0,receiver:?1}")
//몽고디비 문법임.
//만약 Sender가 김우성(?0)이고 recevier가 문성현(?1)이면 이라는 데이터를 찾을거임.

    Flux<Chat2> mFindBySender(String sender, String receiver);
    //Flux는 흐름이고, 데이터를 한번만 주고 받는게 아니라 여러번 계속 흐음대로 받겠다는거.
    //예시로 친구가 전화로 안녕하고 전화를 바로 끊는게 아니라 이야기 하다가 통화를 내가 직접 끊어야 끊기는거

    @Tailable
    @Query("{roomNum:?0}")
    Flux<Chat2> mFindByRoomNum(Integer roomNum);


}
