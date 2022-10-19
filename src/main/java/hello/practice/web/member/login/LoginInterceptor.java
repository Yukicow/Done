package hello.practice.web.member.login;

import hello.practice.domain.member.Member;
import hello.practice.domain.member.form.MemberInfoForm;
import hello.practice.domain.member.service.MemberService;
import hello.practice.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private MemberService memberService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                String redirectUrl = request.getRequestURI();
                response.sendRedirect("/member/login?redirectUrl=" + redirectUrl);
                return false;
            }

        Long memberId = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);
        Member member = memberService.findById(memberId);
        request.setAttribute("memberInfo", new MemberInfoForm(member.getName(), member.getLoginId()));

        return true;
    }
}
