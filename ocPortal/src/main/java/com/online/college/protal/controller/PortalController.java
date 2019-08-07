package com.online.college.protal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.core.consts.domain.ConstsSiteCarousel;
import com.online.college.core.consts.service.IConstsSiteCarouselService;
import com.online.college.protal.business.IPortalBusiness;
import com.online.college.protal.vo.ConstsClassifyVO;

@RequestMapping
@Controller
public class PortalController {

	@Autowired
	private IConstsSiteCarouselService siteCarouselService;
	
	@Autowired
	private IPortalBusiness portalBusiness;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index"); 
		
		//加载轮播 
		List<ConstsSiteCarousel> carouselList = siteCarouselService.queryCarousels(4);
		mv.addObject("carouselList", carouselList);
		
		//课程分类
		List<ConstsClassifyVO> classifys =  portalBusiness.queryAllClassify();
		
		
		//课程推荐
		
		return mv;
	}
}
