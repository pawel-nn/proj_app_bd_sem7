package app.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.operations.DictionaryService;
import app.operations.UserService;


@Controller
public class OwnerController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DictionaryService dictionaryService;
	
    @GetMapping("/owner/accountsControl")
    public String accountsControl(@RequestParam(value="page", required=false) Integer page, Model model) {
    	userService.getUsersByPagination(page, model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
		Iterator<SimpleGrantedAuthority> it = authorities.iterator();
        String authority = null;
		if (it.hasNext()) {
			SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
			authority = sga.getAuthority();
		}
		System.out.println("authority" + authority);
		model.addAttribute("authority", authority);
    	return "accounts_control";
    }
    
    @GetMapping("/owner/accountsControl/blockUser")
    public String blockUser(@RequestParam(value="page", required=true) Integer page,
    						@RequestParam(value="username", required=true) String username) {
    	userService.blockUser(username);
    	return "redirect:/owner/accountsControl?page=" + page;
	}
    
    @GetMapping("/owner/accountsControl/unlockUser")
    public String unlockUser(@RequestParam(value="page", required=true) Integer page,
    						 @RequestParam(value="username", required=true) String username) {
    	userService.unlockUser(username);
    	return "redirect:/owner/accountsControl?page=" + page;
	}
    
    @GetMapping("/owner/dictionary")
    public String productCategoryList(@RequestParam(value="page", required=false) Integer page, Model model) {
    	dictionaryService.getDictionaryListByPagination(page, model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) auth.getAuthorities();
		Iterator<SimpleGrantedAuthority> it = authorities.iterator();
        String authority = null;
		if (it.hasNext()) {
			SimpleGrantedAuthority sga = (SimpleGrantedAuthority) it.next();
			authority = sga.getAuthority();
		}
		System.out.println("authority" + authority);
		model.addAttribute("authority", authority);
    	return "dictionary";
    }

}
