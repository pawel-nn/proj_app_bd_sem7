package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Country;

@Transactional
@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

}
