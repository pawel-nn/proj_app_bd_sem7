package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Dictionary;

@Transactional
@Repository
public interface DictionaryRepository extends CrudRepository<Dictionary, Integer> {

}
