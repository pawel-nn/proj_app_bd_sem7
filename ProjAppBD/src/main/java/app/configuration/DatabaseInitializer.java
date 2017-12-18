package app.configuration;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.model.Country;
import app.model.repository.CountryRepository;
import app.operations.CountryService.CountryType;

@Component
public class DatabaseInitializer {

	@Autowired
	private CountryRepository countryRepository;

	@PostConstruct
	public void initialRun() {
		addCountry();
	}

	public void addCountry() {
		for (CountryType ct : CountryType.values()) {
			if (countryRepository.findByCountryName(ct.getName()) == null) {
				Country country = new Country(ct.getName(), ct.getCode());
				countryRepository.save(country);
			}
		}
	}

}
