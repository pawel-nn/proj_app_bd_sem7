package app.operations;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.model.Country;
import app.model.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;
    
    public ArrayList<Country> getAllCountrie() {
   	 return (ArrayList<Country>) countryRepository.findAll();
    }
    
    public enum CountryType {

        AUSTRIA("Austria", "AUT"),
        BELGIUM("Belgia", "BEL"),
        CZECH_REPUBLIC("Czechy", "CZE"),
        FRANCE("Francja","FRA"),
        GERMANY("Niemcy","DEU"),
        HUNGARY("Węgry","HUN"),
        ITALY("Włochy","ITA"),
        POLAND("Polska","POL"),
        RUSSIA("Rosja","RUS"),
        UNITED_KINGDOM("Anglia","GBR");
        
        private String name;
        private String code;
        
        CountryType(String name, String code) {
       	 this.name = name;
       	 this.code = code;
        }

        public String getName() {
       	 return name;
        }
        
        public String getCode() {
       	 return code;
        }
        
    }

}  

