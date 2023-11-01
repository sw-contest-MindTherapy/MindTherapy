package com.mysite.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email
    private String email;
}
//usercreateform을 이용하는 이유 : 바로 entitiy를 통해 db로 접근하면 위험하거나 오류가 날 상황이 많이 존재하기 떄문
//ex) entitiy에 있는 모든값을 매번 다 입력 받지 않는다, 또 바로 접근할경우 오류가 났을때 대체하기가 힘들다