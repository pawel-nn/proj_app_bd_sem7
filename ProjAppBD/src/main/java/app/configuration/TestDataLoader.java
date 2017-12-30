package app.configuration;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import app.model.Country;
import app.model.Customer;
import app.model.CustomerDetails;
import app.model.Producer;
import app.model.Product;
import app.model.ProductCategory;
import app.model.User;
import app.model.repository.CountryRepository;
import app.model.repository.CustomerRepository;
import app.model.repository.ProducerRepository;
import app.model.repository.ProductCategoryRepository;
import app.model.repository.ProductRepository;
import app.model.repository.UserRepository;

@Controller
public class TestDataLoader {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordEncoderService passwordEncoderService;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProducerRepository producerRepository;
    
    @Autowired
    ProductRepository productRepository;
    
    @GetMapping("/bd/run")
    public String run() {
   	 initialRun();
   	 return "ZZZ_db_test";
    }
    
    public void initialRun() {
   	 addOwnerIfNotExists("qq");
   	 addClientIfNotExists("aa");
   	 addProductCategoryIfNotExists("AGD");
   	 addProductCategoryIfNotExists("RTV");
   	 addProducerIfNotExists("QQQ");
   	 addProducerIfNotExists("WWW");
    }
    
    public void addProductIfNotExists(String name) {
   	 Product product = productRepository.findByName(name);
   	 if(product == null) {
   		 product = new Product(null, productCategoryRepository.findByProductCategoryName("AGD"),
   				 producerRepository.findByProducerName("QQQ"), name, new BigDecimal(300), 200, "123456789012", null);
   		 productRepository.save(product);
   	 }
    }
    
    public void addProducerIfNotExists(String producerName) {
   	 Producer producer = producerRepository.findByProducerName(producerName);
   	 if(producer == null) {
   		 producer = new Producer(producerName);
   		 producerRepository.save(producer);
   	 }
    }
    
    public void addProductCategoryIfNotExists(String productCategoryName) {
   	 ProductCategory productCategory = productCategoryRepository.findByProductCategoryName(productCategoryName);
   	 if(productCategory == null) {
   		 productCategory = new ProductCategory(productCategoryName);
   		 productCategoryRepository.save(productCategory);
   	 }
    }

    public void addOwnerIfNotExists(String username) {
   	 User user = userRepository.findByUsername(username);
   	 if(user == null) {
   		 user = new User(username, passwordEncoderService.encode(username), "qq@qq.pl", true, "ROLE_OWNER");
   		 userRepository.save(user);
   	 }
    }

    public void addClientIfNotExists(String username) {
   	 User user = userRepository.findByUsername(username);
   	 if(user == null) {
   		 Country country = countryRepository.findByCountryId(1);
   		 if(country != null) {
   			 CustomerDetails customerDetails = new CustomerDetails("aa", "aa", "aa", "aa", "aa", "aa");
   			 user = new User(username, passwordEncoderService.encode(username), "aa@aa.pl", true, "ROLE_CUSTOMER");
   			 Customer customer = new Customer(user, customerDetails, country);
   			 customer = customerRepository.save(customer);
   			 userRepository.save(user);
   		 }
   	 }
    }

}




