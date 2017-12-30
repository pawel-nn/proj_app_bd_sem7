package app.configuration;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import app.model.User;
import app.model.repository.UserRepository;

@Component
@Configuration
@EnableScheduling
public class ScheduledTasks {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepository;
	
	@Async
//	@Scheduled(fixedDelay = 21600000)
	public void sendNotificaitoin() throws MailException, InterruptedException {
		User owner = userRepository.findByUserRole("ROLE_OWNER");
		System.out.println("-------------------------- START email");
		MimeMessagePreparator message = newMessage -> {
            newMessage.setRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(owner.getEmail())  // Tu wpisz gdzie wysłać bo z owner nie istnieje..
            );
            newMessage.setFrom("zaczarowana.piwnica@gmail.com");
            newMessage.setSubject("nnn!");
            newMessage.setText("mmm");
        };
		javaMailSender.send(message);
		System.out.println("-------------------------- END email");
	}
	
}
