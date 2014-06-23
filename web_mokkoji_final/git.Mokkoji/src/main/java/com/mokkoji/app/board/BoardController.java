package com.mokkoji.app.board;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mokkoji.app.util.Pagination;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Inject
	private BoardDao boardDao;

	// ~ List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getList(@ModelAttribute BoardFilter boardFilter) {
		ModelAndView mav = new ModelAndView("board/list");

		Pagination pagination = boardFilter.getPagination();
		int count = boardDao.getBoardCount(boardFilter);
		pagination.setNumItems(count);
			
		mav.addObject("boards", boardDao.getBoards(boardFilter));
		return mav;
	}

	// ~ View

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView getView(@RequestParam Integer pn) {
		ModelAndView mav = new ModelAndView("board/view");
		mav.addObject("board", boardDao.getBoard(pn));
		return mav;
	}

	// ~ Write

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public ModelAndView getWrite() {
		ModelAndView mav = new ModelAndView("board/write");
		mav.addObject("board", new Board());
		return mav;
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView postWrite(@ModelAttribute Board board) {
		boardDao.insertBoard(board);
		return new ModelAndView(new RedirectView("list"));
	}

	// ~ Modify

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public ModelAndView getModify(@RequestParam Integer pn) {
		ModelAndView mav = new ModelAndView("board/modify");
		mav.addObject("board", boardDao.getBoard(pn));
		return mav;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public ModelAndView postModify(@ModelAttribute Board board) {
		ModelAndView mav = new ModelAndView(new RedirectView("view"));
		boardDao.updateBoard(board);
		mav.addObject("pn", board.getPn());
		return mav;
	}

	// ~ Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView getDelete(@RequestParam Integer pn) {
		boardDao.deleteBoard(pn);
		return new ModelAndView(new RedirectView("list"));
	}

}
