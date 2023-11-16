package com.mysite.sbb.chattt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.concurrent.Future;


@RequiredArgsConstructor
@RestController //데이터 리턴하는 서버가 되야함.
//@Controller
//@RequestMapping("/chattt")
public class Chat2Controller {
/*private final Chat2Repository chatRepository; //의존성 주입, 생성자 생성
   //귓속말할떄 사용할거임.
   @GetMapping("/chatgo")
   public String chatgo() {
       return "chat2";
   }*/
   /* @CrossOrigin//자바 스크립트
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    //얘가 SSE프로토콜임. 어떤애가 데이터 요청을 하면 한방에 응답하고 끝나는데(HTTP프로토콜), 리스폰스가 안끊기고 계속 유지가 되는겨.
    public Flux<Chat2> getMsg(@PathVariable String sender, @PathVariable String receiver) {
        //@PathVariable은 URL경로에서 변수값을 추출하는데 쓰임.

        return chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @CrossOrigin
    @GetMapping(value = "/chat2/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat2> findByRoomNum(@PathVariable Integer roomNum) {

        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }*/
   private final Chat2Repository chatRepository;

    @Autowired
    private AsyncTaskExecutor taskExecutor; // TaskExecutor를 주입받습니다.

    @CrossOrigin
    @GetMapping(value = "/sender/{sender}/receiver/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Async
    public Future<Flux<Chat2>> getMsgAsync(@PathVariable String sender, @PathVariable String receiver) {
        return new AsyncResult<>(chatRepository.mFindBySender(sender, receiver)
                .subscribeOn(Schedulers.fromExecutor(taskExecutor)));
    }
    @CrossOrigin
    @GetMapping(value = "/chat2/roomNum/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // procedure ="" SSE를 위한 미디어 타입
    @Async // 메서드가 비동기적으로 실행이 되야함을 명시해주는 어노테이션.
    public Future<Flux<Chat2>> findByRoomNum(@PathVariable Integer roomNum) {
        //Mono가 아닌 Flux를 사용한 이유는 데이터 베이스에 roomNum에 해당하는 값을 전부 꺼내서 클라이언트에게 보내줘야하는데 Mono는 0,1두개 밖에 처리를 못하지만
        //Flux는 0~N개까지 처리가 가능해서 리턴타입으로 쓰는것이다.

        return new AsyncResult<>(chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic()));
    }


    @CrossOrigin//자바스크립트 요청 받는 어노테이션
    @PostMapping("/chat2")
    public Mono<Chat2> setMsg(@RequestBody Chat2 chat2){
        chat2.setCreatedAt(LocalDateTime.now());
        //setCreatedAt은 주로 객체의 생성일을 설정하는데 사용되고, 간단한 예시는 this.setCreatedAt = LocalDateTime.now();
        return chatRepository.save(chat2); //객체를 리턴하면 JSON으로 변환(MessageConverter가 함)
        //리턴 타입을 Mono<chat2>로 설정을 하였으므로, 데이터 베이스에 저장과 동시에 입력이 들어온 값을 다시 반환, 반환이 싫으면 Mono(void) 해주면 됌
    }
}