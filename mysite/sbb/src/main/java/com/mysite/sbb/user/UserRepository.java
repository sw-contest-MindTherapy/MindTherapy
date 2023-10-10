package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
    //PK type Long

    Optional<SiteUser> findByusername(String username);//사용자를 조회하는 기능 필요
}
