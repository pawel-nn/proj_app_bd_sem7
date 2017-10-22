package app.controller;

import java.util.Collection;
import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/* text */

@Controller
public class MainController {


	@GetMapping("/")
	String index() {
		return "index";
	}

	@RequestMapping("/login")
	public String loginClient() {
		return "login";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() {
		return "access_denied";
	}

	@RequestMapping(value = "/home")
	public String home() {
		return "home_client";
	}

	@GetMapping("/home")
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		model.addAttribute("usernameMsg","Witaj: "+username);
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
		Iterator<SimpleGrantedAuthority> it = authorities.iterator();
		String authority = null;
		if( it.hasNext() ) {
			SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
			authority = sga.getAuthority();
		}
		if(authority.equals("ROLE_ADMIN")) {
			model.addAttribute("roleMsg","Twoja rola: Admin");
			return "home_admin";
		} else if(authority.equals("ROLE_EMPLOYEE")) {
			model.addAttribute("roleMsg","Twoja rola: Employee");
			return "home_employee";
		}
		return "access_denied";
	}
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
}