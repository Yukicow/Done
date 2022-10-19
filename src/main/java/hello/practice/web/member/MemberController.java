package hello.practice.web.member;


import hello.practice.domain.member.Member;
import hello.practice.domain.member.form.JoinForm;
import hello.practice.domain.member.form.LoginForm;
import hello.practice.domain.member.form.MemberInfoForm;
import hello.practice.domain.member.service.MemberService;
import hello.practice.web.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm form){
        return "/member/login/login-form";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(name = "redirectUrl", defaultValue = "/") String redirectUrl,
                        HttpServletRequest request, Model model){

        if(bindingResult.hasErrors()){
            return "/member/login/login-form";
        }

        Member member = memberService.login(loginForm.getLoginId(), loginForm.getPassword());

        if(member == null){
            bindingResult.reject("loginMisMatch", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/member/login/login-form";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        return "redirect:" + redirectUrl;

    }


    @GetMapping("/join")
    public String joinForm(@ModelAttribute JoinForm form){
        return "/member/join/join-form";
    }

    @PostMapping("/join")
    public String join(@Validated @ModelAttribute JoinForm form, BindingResult bindingResult,
                       HttpServletRequest request, Model model){

        if(bindingResult.hasErrors()){
            return "/member/join/join-form";
        }

        Member member = createMember(form);
        memberService.saveMember(member);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());

        MemberInfoForm memberInfoForm = new MemberInfoForm(member.getName(), member.getLoginId());
        model.addAttribute("memberInfo", memberInfoForm);

        return "login-index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
            return "index";
        }
        return "index";
    }

    private Member createMember(JoinForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setLoginId(form.getLoginId());
        member.setPassword(form.getPassword());
        return member;
    }
}
