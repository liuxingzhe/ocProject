package com.online.college.protal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.web.JsonView;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private IAuthUserService authUserService;
	/**
	 * 
	 * @Title:        register 
	 * @Description:  跳转到注册页面
	 * @param:        @return    
	 * @return:       ModelAndView    
	 * @throws 
	 * @author        liu
	 * @Date          2019年7月26日 上午6:09:46
	 */
	@RequestMapping("/register")
	public ModelAndView register() {
		return  new ModelAndView("auth/register");
	}
	
	/**
	 * 实现注册
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public String doRegister(AuthUser authUser) {
		AuthUser  tmpUser = authUserService.getByUsername(authUser.getUsername());
		if(tmpUser!=null) {
			return JsonView.render(1);
		}else {
			authUserService.createSelectivity(authUser);
			return JsonView.render(0);
		}
	}
}
