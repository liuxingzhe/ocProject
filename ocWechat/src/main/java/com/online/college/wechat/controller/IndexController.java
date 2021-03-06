package com.online.college.wechat.controller;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * M站
 */
@Controller
@RequestMapping()
public class IndexController {

	@Autowired
	private ICourseService courseService;
	
	//memcache客户端
	/**/
	@Autowired
	MemcachedClient memcachedClient;
	
	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index(TailPage<Course> page){
//		String key ="test_memcash";
//		try {
//			memcachedClient.set(key, 3600, "测试memcash");
//			String result = memcachedClient.get(key);
//			System.out.println("缓存result"+result);
//			
//		} catch (TimeoutException | InterruptedException | MemcachedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ModelAndView mv = new ModelAndView("index");
		
		//只展示第一页的课程
		Course queryEntity = new Course();
		queryEntity.setOnsale(CourseEnum.ONSALE.value());
		
		page.descSortField("weight");
		
		page = this.courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		return mv;
	}
	
}
