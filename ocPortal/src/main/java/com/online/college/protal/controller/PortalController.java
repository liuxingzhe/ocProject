package com.online.college.protal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping
@Controller
public class PortalController {

	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index"); 
		mv.addObject("info", "PC站首页");
		return mv;
	}
}
