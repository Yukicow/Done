package hello.practice.domain.member.form;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberInfoForm {

    private String name;
    private String loginId;

    public MemberInfoForm(String name, String loginId) {
        this.name = name;
        this.loginId = loginId;
    }
}
