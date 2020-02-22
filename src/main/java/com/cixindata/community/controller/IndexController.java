package com.cixindata.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}
}
