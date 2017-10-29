package app.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import app.dataTransportObject.NewPasswordDTO;
import app.operations.UserService;
import app.viewObject.NewPasswordVO;

@Controller
public class MainController {   
	
	@Autowired
	private UserService userService;
    
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
		model.addAttribute("usernameMsg","Hello: "+username);
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
		Iterator<SimpleGrantedAuthority> it = authorities.iterator();
		String authority = null;
		if( it.hasNext() ) {
			SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
			authority = sga.getAuthority();
		} 
		if(authority.equals("ROLE_OWNER")) {
			model.addAttribute("roleMsg","Hi: owner");
			return "home_owner";
		} else if(authority.equals("ROLE_CLIENT")) {
			model.addAttribute("roleMsg","Hi: client");
			return "home_owner";
		}
		return "access_denied";
	}
	
    @GetMapping("/changePassword")
    public String changePassword(NewPasswordVO newPasswordVO) {
    	newPasswordVO.setNewPassword_1(null);
    	newPasswordVO.setNewPassword_2(null);
    	return "change_password";
   	}

    @PostMapping("/changePassword")
    public String changePasswordValidation(NewPasswordVO newPasswordVO, BindingResult bindingResult, Model m) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();
        boolean status = userService.updateUserPassword(new NewPasswordDTO(newPasswordVO), username);
        if(!status) {
        	m.addAttribute("msg", "Error, password was not changed.");
            return "result";	
        }
        m.addAttribute("msg", "Password was changed.");
        return "result";
    }
	
	@GetMapping("/error")
	public String error() {
		return "error";
	}
}