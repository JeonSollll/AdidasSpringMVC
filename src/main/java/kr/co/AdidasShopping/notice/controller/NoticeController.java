package kr.co.AdidasShopping.notice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.AdidasShopping.notice.domain.Notice;
import kr.co.AdidasShopping.notice.domain.PageData;
import kr.co.AdidasShopping.notice.service.NoticeService;


@Controller
public class NoticeController {
	@Autowired
	private NoticeService service;
	
	// doGet - ������ �̵���
	@RequestMapping(value="/notice/list.do", method=RequestMethod.GET)
	public String showListForm(
			@RequestParam(name = "currentPage", defaultValue = "1") int currentPage
			, Model model) {
		PageData pd = service.selectNoticeList(currentPage);
		List<Notice> nList = pd.getnList();
		String pageNavi = pd.getPageNavi();
		
		if(!nList.isEmpty()) {
			model.addAttribute("nList", nList);
			model.addAttribute("pageNavi", pageNavi);
			return "notice/list";
		}else {
			model.addAttribute("msg", "���� ��ȸ�� �Ϸ���� �ʾҽ��ϴ�.");
			return "common/errorPage";
		}
	}
	
	// doGet - ������ �̵���
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		return "notice/insert";
	}
	
	// doPost	������ �����
	@RequestMapping(value="/notice/insert.do", method=RequestMethod.POST)
	public String RegisterForm(
			@RequestParam("noticeSubject") String noticeSubject
			, @RequestParam("noticeContent") String noticeContent
			, Model model) {
		Notice notice = new Notice(noticeSubject, noticeContent);
		int result = service.registerNotice(notice);
		if(result > 0) {
			// ����!
			return "redirect:/notice/list.do";
		}else {
			// ����!!
			model.addAttribute("msg", "���� ����� �Ϸ���� �ʾҽ��ϴ�.");
			return "common/errorPage";
		}
	}
	
	// doGet - ������ �̵���
	@RequestMapping(value="/notice/detail.do", method=RequestMethod.GET)
	public String showDetailNotice(
			@RequestParam("noticeNo") int noticeNo
			, Model model) {
		Notice notice = service.showOneByNo(noticeNo);
		if(notice != null) {
			model.addAttribute("notice", notice);
			return "notice/detail";
		}else {
			model.addAttribute("msg", "�������� �ʴ� ���������Դϴ�.");
			return "common/errorPage";
		}
	}
	
	// doGet - ������ �̵���
	@RequestMapping(value="/notice/delete.do", method=RequestMethod.GET)
	public String deleteNotice(
			@RequestParam("noticeNo") int noticeNo
			, Model model) {
		int result = service.removeNotice(noticeNo);
		if(result > 0) {
			return "redirect:/notice/list.do";
		}else {
			model.addAttribute("msg", "ȸ�� ������ �Ϸ���� �ʾҽ��ϴ�.");
			return "common/errorPage";
		}
	}
	
	// doGet - ������ �̵���
		@RequestMapping(value="/notice/modify.do", method=RequestMethod.GET)
		public String clickModifyNotice(
				@RequestParam("noticeNo") int noticeNo
				, Model model) {
			Notice notice = service.showOneByNo(noticeNo);
			model.addAttribute("notice", notice);
			return "notice/modify";
		}
		
		@RequestMapping(value="/notice/modify.do", method=RequestMethod.POST)
		public String modifyNotice(
				@RequestParam("noticeSubject") String noticeSubject
				, @RequestParam("noticeContent") String noticeContent
				, @RequestParam("noticeNo") int noticeNo
				, Model model) {
			Notice notice = new Notice(noticeNo, noticeSubject, noticeContent);
			int result = service.modifyNotice(notice);
			if(result > 0 ) {
				return "redirect:/notice/detail.do?noticeNo=" + noticeNo;
			}else {
				model.addAttribute("msg", "�������� ������ �Ϸ���� �ʾҽ��ϴ�.");
				return "common/errorPage";
			}
		}
	
}
