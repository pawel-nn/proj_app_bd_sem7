package app.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.dataTransportObject.NewPasswordDTO;
import app.model.User;
import app.model.repository.UserRepository;
import app.validation.UserValidation;

@Service
public class UserService {

    private static int MAX_ROWS_PER_PAGE = 10;	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserValidation userValidation;
	
	public boolean updateUserPassword(NewPasswordDTO newPasswordDTO, String username) {
		try {
			newPasswordDTO = userValidation.validateNewPassword(newPasswordDTO);
			if(newPasswordDTO.isValidationResult()) {
				User user = userRepository.findByUsername(username);
				user.setPassword(newPasswordDTO.getViewObject().getNewPassword_1());
				userRepository.save(user);
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean blockUser(String username) {
		try {
			User user = userRepository.findByUsername(username);
	    	if(user.hasOwnerRole())
	    		return false;
			user.setEnabled(false);
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean unlockUser(String username) {
		try {
			User user = userRepository.findByUsername(username);
			user.setEnabled(true);
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void getUsersByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) userRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<User> userList = (Page<User>) userRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.ASC, "username"));
    	if(userList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("userList", userList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber);    	
	}
	
}
