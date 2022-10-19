package hello.practice.domain.member.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginForm {

    @NotEmpty(message = "아이디를 입력해 주세요")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;
}
