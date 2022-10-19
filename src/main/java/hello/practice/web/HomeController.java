package hello.practice.web;


import hello.practice.domain.member.Member;
import hello.practice.domain.member.form.MemberInfoForm;
import hello.practice.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
            return "index";
        }

        Member member = memberService.findById( (Long) session.getAttribute(SessionConst.LOGIN_MEMBER) );
        MemberInfoForm memberInfoForm = new MemberInfoForm(member.getName(), member.getLoginId());
        model.addAttribute("memberInfo", memberInfoForm);
        return "login-index";
    }
}
