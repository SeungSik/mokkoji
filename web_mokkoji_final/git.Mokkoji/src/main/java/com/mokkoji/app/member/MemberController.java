package com.mokkoji.app.member;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class MemberController {

	private static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	private MemberDao memberDao;

	// ~ Login

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLoginPage(@ModelAttribute Member member) {
		ModelAndView mav = new ModelAndView("member/login");
		return mav;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView postLoginPage(@ModelAttribute Member member,
			WebRequest request) {
		ModelAndView mav = new ModelAndView("member/login");
		Member getMember = memberDao.getMemberFromId(member.getId());
		if (member.getPassword().equals(getMember.getPassword())) {
			request.setAttribute("sMember", getMember, WebRequest.SCOPE_SESSION);
			logger.debug("Login success [ID :" + getMember.getId() +"]");
		}
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView getLogout(WebRequest request) {
		ModelAndView mav = new ModelAndView(new RedirectView("login"));
		Member member = (Member) request.getAttribute("sMember", WebRequest.SCOPE_SESSION);
		request.removeAttribute("sMember", WebRequest.SCOPE_SESSION);
		logger.debug("Logout [ID :" + member.getId() +"]");
		return mav;
	}

	// ~ Join

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public ModelAndView getJoinPage(@ModelAttribute Member member) {
		ModelAndView mav = new ModelAndView("member/join");
		return mav;
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView postJoinPage(@ModelAttribute Member member) {
		ModelAndView mav = new ModelAndView(new RedirectView("login"));
		memberDao.insertMember(member);
		return mav;
	}

	// ~ MemberList

	@RequestMapping(value = "/memberList", method = RequestMethod.GET)
	public ModelAndView getMemberListPage() {
		ModelAndView mav = new ModelAndView("member/member_list");
		List<Member> memberList = memberDao.getMemeberList();
		mav.addObject("memberList", memberList);
		return mav;
	}

	@RequestMapping(value = "/memberView", method = RequestMethod.GET)
	public ModelAndView getMemberViewPage(@ModelAttribute Member member) {
		ModelAndView mav = new ModelAndView("member/member_info");
		Member detailMember = memberDao.getMemberFromPn(member.getPn());
		mav.addObject("member", detailMember);
		return mav;
	}

	@RequestMapping(value = "/memberDelete", method = RequestMethod.GET)
	public ModelAndView getMemberDeletePage(@ModelAttribute Member member) {
		ModelAndView mav = new ModelAndView(new RedirectView("memberList"));
		memberDao.deleteMember(member.getPn());
		return mav;
	}
}
