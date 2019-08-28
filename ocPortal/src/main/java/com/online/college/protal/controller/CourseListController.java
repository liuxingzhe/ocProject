package com.online.college.protal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.protal.business.IPortalBusiness;
import com.online.college.protal.vo.ConstsClassifyVO;

/**
 * 
 * @ClassName:     CourseController.java
 * @Description:  课程分类页
 * @author         liu
 * @version        V1.0  
 * @Date           2019年8月12日 下午8:06:41
 */
@Controller
@RequestMapping("/course")
public class CourseListController {
	@Autowired
	private IPortalBusiness portalBusiness;
	@Autowired
	private IConstsClassifyService constsClassifyService;
	@Autowired
	private ICourseService courseService;
	/**
	 * 
	 * @Title:        list 
	 * @Description:  TODO(这里用一句话描述这个方法的作用) 
	 * @param:        @param c 		分类
	 * @param:        @param sort	排序
	 * @param:        @param page	分页
	 * @param:        @return    
	 * @return:       ModelAndView    
	 * @throws 
	 * @author        liu
	 * @Date          2019年8月12日 下午8:12:03
	 */
	@RequestMapping("/list")
	public ModelAndView  list(String c,String sort,TailPage<Course> page) {
		ModelAndView mv = new ModelAndView("list");
		String curCode= "-1";	 //当前方向code
		String curSubCode= "-2"; //当前分类code
		
		//加载所有课程分类
		 Map<String, ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		
		//所有一级分类
		List<ConstsClassifyVO> classifyList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()) {
			classifyList.add(vo);
		}
		
		mv.addObject("classifys", classifyList);
		//当前分类
		ConstsClassify curClassify = constsClassifyService.getByCode(c);
		
		if(null == curClassify){//没有此分类，加载所有二级分类
			List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
			for(ConstsClassifyVO vo : classifyMap.values()){
				subClassifys.addAll(vo.getSubClassifyList());
			}
			mv.addObject("subClassifys", subClassifys);
		}else{
			if(!"0".endsWith(curClassify.getParentCode())){//当前是二级分类
				curSubCode = curClassify.getCode();
				curCode = curClassify.getParentCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getParentCode()).getSubClassifyList());//此分类平级的二级分类
			}else{//当前是一级分类
				curCode = curClassify.getCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getCode()).getSubClassifyList());//此分类下的二级分类
			}
		}
		mv.addObject("curCode", curCode);
		mv.addObject("curSubCode", curSubCode);
		
		//分页排序数据
		//分页的分类参数
		Course queryEntity = new Course();
		if(!"-1".equals(curCode)){
			queryEntity.setClassify(curCode);
		}
		if(!"-2".equals(curSubCode)){
			queryEntity.setSubClassify(curSubCode);
		}
		
		
		//排序参数
		if("pop".equals(sort)){//最热
			page.descSortField("studyCount");
		}else{
			sort = "last";
			page.descSortField("id");
		}
		mv.addObject("sort", sort);
		
		//分页参数
		queryEntity.setOnsale(CourseEnum.ONSALE.value());
		page = this.courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		return mv;
	}
	
}
