package hello.practice.domain.member.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class JoinForm {

    @NotEmpty(message = "이름을 입력해 주세요.")
    @NotBlank(message = "이름은 최소 2자에서 최대 10자이며 공백을 포함할 수 없습니다.")
    @Length(min = 2, max = 10, message = "이름은 최소 2자에서 최대 10자이며 공백을 포함할 수 없습니다.")
    private String name;

    @NotBlank
    @NotEmpty(message = "아이디를 입력해 주세요")
    private String loginId;

    @NotBlank
    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    private String password;

}
