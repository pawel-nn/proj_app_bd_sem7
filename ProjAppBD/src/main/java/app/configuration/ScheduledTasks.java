package app.configuration;

import java.util.ArrayList;

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

import app.model.Product;
import app.model.User;
import app.model.repository.ProductRepository;
import app.model.repository.UserRepository;

@Component
@Configuration
@EnableScheduling
public class ScheduledTasks {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private ProductRepository productRepository;

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
            newMessage.setFrom("email.systemowy");
            newMessage.setSubject("Stan zapasów produktów");
            newMessage.setText(getEmailContent());
        };
		javaMailSender.send(message);
		System.out.println("-------------------------- END email");
	}
	
    public String getEmailContent() {
        StringBuilder sb = new StringBuilder();
        boolean firstOcc = true;
        ArrayList<Product> productList = (ArrayList<Product>) productRepository.findAll();
        for(Product p : productList) {
            if(p.getStockSize() <= 10) {
                if(firstOcc) {
                    sb.append("Produkty na wyczerpaniu:\n");
                    firstOcc = false;
                }
                sb.append("ID:" + p.getProductId() + ", N:" + p.getName() + ", Ilość:" + p.getStockSize() + "\n");
            }
        }
        if(firstOcc)
            sb.append("Niczego nie brakuje!");
        sb.append("Produktów jest ogółem:" + productList.size());
        return sb.toString();
    }

	
}
