package com.cixindata.community.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cixindata.community.mapper.UserMapper;
import com.cixindata.community.model.User;

@Controller
public class IndexController {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String index(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			String token = cookie.getValue();
			User user = userMapper.findByToken(token);
			if (user != null) {
				request.getSession().setAttribute("user", user);
			}
			break;
		}
		return "index";
	}
}
