package com.mysite.sbb.question;

import com.mysite.sbb.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*레포지토리란? 엔티티에 의해 생성된 데이터베이스 테이블에 접근하는 메서드들(findAll,save)을 사용하기 위한 인터페이스
* data처리를 위해서 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD(Create,Read,Update,Delete)가 필요함. 이러한 CRUD 어떻게 처리할지
* 정의하는 계층이 바로 레포지토리이다.
* */

public interface QuestionRepository extends JpaRepository<Question,Integer> {
//JpaRepository를 상속할 때는 제네릭스 타입으로 대상이 되는 엔티티의 타입(Question)과 해당 엔티티의 PK속성 id 타입 (Integer)지정해야함.

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    Page<Question> findAll(Pageable pageable);


}

//인터페이스 쓰는 이유 : 스프링이 알아서 해줌
