package app.operationsTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import app.configuration.PasswordEncoderService;
import app.dataTransportObject.OwnerDTO;
import app.model.User;
import app.model.repository.UserRepository;
import app.operations.DatabaseLogService;
import app.operations.UserService;
import app.viewObject.OwnerVO;
import app.viewObject.UserVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private DatabaseLogService databaseLogService;
	
	@MockBean
	private PasswordEncoderService passwordEncoderService;
	
	@Before
	public void beforeTest() {
		Mockito
			.when(passwordEncoderService.encode("pasw"))
			.thenReturn("encoded_pasw");
		
        Mockito
        	.when(userRepository.save(new User("username", "encoded_pasw", "email@qwerty.com", true, "ROLE_OWNER")))
        	.thenReturn(new User(1, "username", "encoded_pasw", "email@qwerty.com", true, "ROLE_OWNER"));
	}

	@Test
	public void saveOwnerTest() {
		UserVO userVO = new UserVO("username", "pasw", "pasw","email@qwerty.com");
		OwnerVO ownerVO = new OwnerVO();
		ownerVO.setUserVO(userVO);
		ownerVO.setSecret("secret");
		OwnerDTO ownerDTO = new OwnerDTO(ownerVO);
		ownerDTO = userService.registerOwner(ownerDTO);
		Assert.assertFalse(ownerDTO.getViewObject().isInvalidOverall());
	}

}
