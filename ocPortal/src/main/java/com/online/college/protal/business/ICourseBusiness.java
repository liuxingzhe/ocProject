package com.online.college.protal.business;

import java.util.List;

import com.online.college.protal.vo.CourseSectionVO;


public interface ICourseBusiness {

	/**
	 * 获取课程章节
	 */
	List<CourseSectionVO> queryCourseSection(Long courseId);
	
}
