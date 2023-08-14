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
	
	// doGet - ������ �̵���
	@RequestMapping(value="/member/register.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "member/signUpPage";
	}
	
	// doPost	������ �����
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
            // ���� ó�� ���� �߰� (��: ����ڿ��� ���� �޽��� ǥ��)
        }
		
		Member member = new Member(memberName, memberId, memberPw, memberEmail, memberBirthDate, memberGender, memberAddress, memberPhone);
		
		try {
			int result = service.registerMember(member);
			if(result > 0) {
				// ����!
				return "redirect:/index.jsp";
			}else {
				// ����!!
				model.addAttribute("msg", "ȸ�������� �Ϸ���� �ʾҽ��ϴ�.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();		// �ܼ�â�� ���������� �߰� ��
			model.addAttribute("msg", e.getMessage());		// �ܼ�â�� �ߴ� �޽����� �� �������� �߰� ��
			return "common/errorPage";
		}
//		request.setAttribute("", "");
//		request.getRquestDispatcher("WEB-INF/views/member/register.jsp").forward(request, response);
	}
	
	// doGet - ������ �̵���
	@RequestMapping(value="/member/login.do", method=RequestMethod.GET)
	public String showmemberLoginForm(Model model) {
		return "member/loginPage";
	}
	
	// doPost	������ �����
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
				// �����ϸ� �α��� �������� �̵�
//				model.addAttribute("member", mOne);
//				HttpSession session = request.getSession();
//				session.setAttribute("memberId", mOne.getMemberId());
//				session.setAttribute("memberName", mOne.getMemberName());
				model.addAttribute("memberId", mOne.getMemberId());
				model.addAttribute("memberName", mOne.getMemberName());
				return "redirect:/index.jsp";
			}else {
				// �����ϸ� ���� �޽��� ���
				model.addAttribute("msg", "�������� �ʴ� ȸ�� �����Դϴ�!!");
				return "common/errorPage";
			}
		} catch (Exception e) {
			// ���� �߻� �� ���� �޽��� ���
			e.printStackTrace();
			model.addAttribute("msg", e.getMessage());
			return "common/errorPage";
		}
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public String memberLogout(
			HttpSession sessionPrev
			// SessionStatus�� �������� ������̼�(@SessionAttributes)�� �����Ǵ� ������ �����Ų��.
			// ���� �޼ҵ�� setComplete();
			, SessionStatus session
			, Model model) {
		if(session != null) {
//			session.invalidate();
			session.setComplete();
			if(session.isComplete()) {
				// ������ ���� ��ȿ�� üũ
			}
			return "redirect:/index.jsp";
		}else {
			model.addAttribute("msg", "�α׾ƿ��� �Ϸ����� ���߽��ϴ�.");
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
			model.addAttribute("msg", "ȸ�� ��ȸ�� �Ϸ����� ���߽��ϴ�.");
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
				// ����
				return "redirect:/member/logout.do";
			}else {
				// ����
				model.addAttribute("msg", "ȸ�� Ż�� �Ϸ���� �ʾҽ��ϴ�.");
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
				// ����!
//				return "redirect:/index.jsp";
//				redirect.addAttribute("memberId", memberId);
				return "redirect:/shop/myPage.do";
			}else {
				// ����!!
				model.addAttribute("msg", "ȸ�������� �Ϸ���� �ʾҽ��ϴ�.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			e.printStackTrace();		// �ܼ�â�� ���������� �߰� ��
			model.addAttribute("msg", e.getMessage());		// �ܼ�â�� �ߴ� �޽����� �� �������� �߰� ��
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
