package kr.co.AdidasShopping.member.controller;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.co.AdidasShopping.member.domain.Member;
import kr.co.AdidasShopping.member.service.MemberService;


@Controller
@SessionAttributes({"memberId"})
public class MemberController {
	@Autowired
	private MemberService service;
	
	// doGet - 페이지 이동용
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/signUpPage";
	}
	
	// doPost	데이터 저장용
	@RequestMapping(value="/member/register.do", method=RequestMethod.POST)
	public String registerMember(
			@RequestParam("name") String memberName
			, @RequestParam("username") String memberId
			, @RequestParam("password") String memberPw
			, @RequestParam("email") String memberEmail
			, @RequestParam("phone") String memberPhone
			, @RequestParam("birthdate") String birthDateString
			, @RequestParam("gender") String memberGender
			, @RequestParam("address") String memberAddress
			, Model model) {
		
		Date memberBirthDate = null;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(birthDateString);
            memberBirthDate = new Date(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // 오류 처리 로직 추가 (예: 사용자에게 오류 메시지 표시)
        }
		
		Member member = new Member(memberName, memberId, memberPw, memberEmail, memberBirthDate, memberGender, memberAddress, memberPhone);
		
		try {
			int result = service.registerMember(member);
			if(result > 0) {
				// 성공!
				return "redirect:/index.jsp";
			}else {
				// 실패!!
				model.addAttribute("msg", "회원가입이 완료되지 않았습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();		// 콘솔창에 빨간색으로 뜨게 함
			model.addAttribute("msg", e.getMessage());		// 콘솔창에 뜨는 메시지를 웹 페이지로 뜨게 함
			return "common/errorPage";
		}
//		request.setAttribute("", "");
//		request.getRquestDispatcher("WEB-INF/views/member/register.jsp").forward(request, response);
	}
	
	// doGet - 페이지 이동용
	@RequestMapping(value="/member/login.do", method=RequestMethod.GET)
	public String showmemberLoginForm(Model model) {
		return "member/loginPage";
	}
	
	// doPost	데이터 저장용
	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public String memberLogin(
			HttpServletRequest request
			, @RequestParam("username") String memberId
			, @RequestParam("password") String memberPw
			, Model model) {
		// SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ? AND MEMBER_PW = ?
		try {
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberPw(memberPw);
			Member mOne = service.memberLoginCheck(member);
			if(mOne != null) {
				// 성공하면 로그인 페이지로 이동
//				model.addAttribute("member", mOne);
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			}else {
				// 실패하면 실패 메시지 출력
				model.addAttribute("msg", "존재하지 않는 회원 정보입니다!!");
				return "common/errorPage";
			}
		} catch (Exception e) {
			// 예외 발생 시 예외 메시지 출력
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			// SessionStatus는 스프링의 어노테이션(@SessionAttributes)로 지원되는 세션을 만료시킨다.
			// 사용된 메소드는 setComplete();
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate();
			session.setComplete();
			if(session.isComplete()) {
				// 세션의 만료 유효성 체크
			}
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("msg", "로그아웃을 완료하지 못했습니다.");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/shop/myPage.do", method=RequestMethod.GET)
	public String showDetailMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		Member member = service.showOneById(memberId);
		if(member != null) {
			model.addAttribute("member", member);
			return "member/myPage";
		}else {
			model.addAttribute("msg", "회원 조회를 완료하지 못했습니다.");
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String removeMember(
			@RequestParam("memberId") String memberId
			, Model model) {
		try {
			int result = service.removeMember(memberId);
			if(result > 0) {
				// 성공
				return "redirect:/member/logout.do";
			}else {
				// 실패
				model.addAttribute("msg", "회원 탈퇴가 완료되지 않았습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/update.do", method=RequestMethod.POST)
	public String updateMember(
			@RequestParam("member-id") String memberId
			, @RequestParam("member-pw") String memberPw
			, @RequestParam("member-email") String memberEmail
			, @RequestParam("member-address") String memberAddress
			, @RequestParam("member-phone") String memberPhone
//			, RedirectAttributes redirect
			, Model model) {
		Member member = new Member(memberId, memberPw, memberEmail, memberAddress, memberPhone);
		try {
			int result = service.modifyMember(member);
			if(result > 0) {
				// 성공!
//				return "redirect:/index.jsp";
//				redirect.addAttribute("memberId", memberId);
				return "redirect:/shop/myPage.do";
			}else {
				// 실패!!
				model.addAttribute("msg", "회원수정이 완료되지 않았습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();		// 콘솔창에 빨간색으로 뜨게 함
			model.addAttribute("msg", e.getMessage());		// 콘솔창에 뜨는 메시지를 웹 페이지로 뜨게 함
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/shop/productPage.do", method=RequestMethod.GET)
	public String productPage(Model model) {
		return "shopping/productPage";
	}
	
	@RequestMapping(value="/shop/paymentPage.do", method=RequestMethod.GET)
	public String paymentPage(Model model) {
		return "shopping/paymentPage";
	}
	
}
