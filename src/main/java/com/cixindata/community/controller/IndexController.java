package com.cixindata.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

	
	@GetMapping("/hello")
	public String home(@RequestParam(name="name") String name,Model model) {
		model.addAttribute("name",name);
		return "hello";
	}
}