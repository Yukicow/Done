package hello.practice.domain.member;

import lombok.Data;

@Data
public class Member {

    private Long id;
    private String name;
    private String loginId;
    private String password;


    public Member() {
    }

    public Member(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }
}
