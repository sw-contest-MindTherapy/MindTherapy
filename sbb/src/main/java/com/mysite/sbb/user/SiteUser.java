package com.mysite.sbb.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity // DB 테이블에 연동되는 변수
public class SiteUser {
    @Id //이 어노테이션은 id 필드가 엔티티의 주키(primary key)임을 나타냅니다. 주키는 각 엔티티 레코드를 고유하게 식별하는 데 사용됩니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이 어노테이션은 id 필드의 값이 자동으로 생성되며, 데이터베이스의 IDENTITY 전략을 사용함을 나타냅니다. 이렇게 하면 데이터베이스가 자동으로 고유한 ID 값을 할당합니다.
    private Long id; //엔티티 식별하기 위한 id이다 실제 우리가 사용하느 id와는 다르다

    @Column(unique = true)
    private String username; //우리가 입력하는 아이디

    private String password;

    @Column(unique = true)
    private String email;
}
//BaseEntitiy 다음에 이용해보기