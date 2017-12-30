package app.model.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.model.Dictionary;
import app.model.DictionaryCategoryName;

@Transactional
@Repository
public interface DictionaryCategoryNameRepository extends CrudRepository<DictionaryCategoryName, Integer> {

	ArrayList<DictionaryCategoryName> findByCategoryName(String categoryName);

}
