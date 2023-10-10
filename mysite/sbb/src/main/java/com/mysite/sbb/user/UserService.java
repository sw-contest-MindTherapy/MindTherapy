package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username,String email, String password){
        SiteUser user=new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
//User레포지토리를 사용하여 user 데이터를 생성하는 create 메서드!
//사용자 암호화를위해서BCryptPasswordEncoder 클래스 사용 -> Bean으로 직접 등록해서 PasswordEncoder 객체 주입받아 사용
