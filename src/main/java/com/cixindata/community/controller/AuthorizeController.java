package com.cixindata.community.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cixindata.community.dto.AccessTokenDTO;
import com.cixindata.community.dto.GithubUser;
import com.cixindata.community.mapper.UserMapper;
import com.cixindata.community.model.User;
import com.cixindata.community.provider.GithubProvider;

/**
 * GitHub应用登录
 * 
 * @author Administrator
 *
 */
@Controller
public class AuthorizeController {

	@Autowired
	private GithubProvider githubProvider;

	@Value("${github.client.id}")
	private String clientId;

	@Value("${github.client.secret}")
	private String clientSecret;

	@Value("${github.redirect.uri}")
	private String redirectUri;

	@Autowired
	private UserMapper userMapper;

	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code, @RequestParam("state") String state,
			HttpServletRequest request,HttpServletResponse response) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if (githubUser != null) {
			User user = new User();
			String token=UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModfied(user.getGmtCreate());
			userMapper.insert(user);
			response.addCookie(new Cookie("token", token));
			// 登录成功,写conkie和session
			request.getSession().setAttribute("user", githubUser);
			return "redirect:/";

		} else {
			return "redirect:/";
			// 登录失败，重新登录
		}
	}

}
