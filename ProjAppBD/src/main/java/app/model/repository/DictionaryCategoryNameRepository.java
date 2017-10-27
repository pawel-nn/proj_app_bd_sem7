package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.DictionaryCategoryName;

@Transactional
@Repository
public interface DictionaryCategoryNameRepository extends CrudRepository<DictionaryCategoryName, Integer> {

}
