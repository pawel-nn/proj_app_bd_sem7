package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.operations.UserService;


@Controller
public class OwnerController {

	@Autowired
	private UserService userService;
	
    @GetMapping("/owner/accountsControl")
    public String accountsControl(@RequestParam(value="page", required=false) Integer page, Model model) {
    	userService.getUsersByPagination(page, model);
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

}
