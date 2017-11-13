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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.dataTransportObject.CustomerDTO;
import app.dataTransportObject.NewPasswordDTO;
import app.dataTransportObject.OwnerDTO;
import app.dataTransportObject.ProductDTO;
import app.operations.ProductService;
import app.operations.UserService;
import app.viewObject.CustomerVO;
import app.viewObject.NewPasswordVO;
import app.viewObject.OwnerVO;
import app.viewObject.ProductVO;

@Controller
public class MainController {   
	
	@Autowired
	private UserService userService;
	
	@Autowired ProductService productService;
    
	@GetMapping("/")
	String index(@RequestParam(value="page", required=false) Integer page, Model model) {
		productService.getProductsByPagination(page, model);
		return "product_list_client";
	}

	@RequestMapping("/login")
	public String loginClient() {
		return "login";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() {
		return "access_denied";
	}

	@GetMapping("/home")
	public String home(Model model) {
		try {
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
		model.addAttribute("authority", authority);
		if(authority.equals("ROLE_OWNER")) {
			model.addAttribute("roleMsg","Witaj sprzedawco!");
			return "home_owner";
		} else if(authority.equals("ROLE_CUSTOMER")) {
			model.addAttribute("roleMsg","Witaj kliencie!");
			return "home_client";
		}
		} catch (Exception e){}
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
	
	@GetMapping("/registerCustomer")
	public String addRegisterCustomerGET(CustomerVO customerVO, Model m) {
		return "register_customer";
	}

	@PostMapping("/registerCustomer")
	public String addRegisterCustomerPOST(CustomerVO customerVO, Model m) {
		CustomerDTO customerDTO = new CustomerDTO(customerVO);
		customerDTO = userService.registerCustomer(customerDTO);
		if(customerDTO == null) {
			m.addAttribute("msg", "Błąd, nie można utworzyć konta!");
			return "result";	
		}		
		m.addAttribute("msg", "Utworzono konto");
		return "result";	
	}
	
	
	@GetMapping("/registerOwner")
	public String addRegisterOwnerGET(OwnerVO ownerVO, Model m) {
		return "register_owner";
	}

	@PostMapping("/registerOwner")
	public String addRegisterOwnerOST(OwnerVO ownerVO, Model m) {
		OwnerDTO ownerDTO = new OwnerDTO(ownerVO);
		ownerDTO = userService.registerOwner(ownerDTO);
		if(ownerDTO == null) {
			m.addAttribute("msg", "Błąd, nie można utworzyć konta!");
			return "result";	
		}		
		m.addAttribute("msg", "Utworzono konto");
		return "result";	
	}
	
}