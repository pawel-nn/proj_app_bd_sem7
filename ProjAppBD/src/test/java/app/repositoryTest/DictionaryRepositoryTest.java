package app.repositoryTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.model.Dictionary;
import app.model.DictionaryCategoryName;
import app.model.repository.DictionaryRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
public class DictionaryRepositoryTest {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Before
    public void beforeTest() {
    	
    }
    
    @Test
    public void saveDictionaryKeywordTest() {
		DictionaryCategoryName dictionaryCategoryName = new DictionaryCategoryName("Kraje");
		Dictionary dictionary = new Dictionary(dictionaryCategoryName, "Polska", 1);
		dictionary = dictionaryRepository.save(dictionary);
    	Assert.assertEquals("Kraje", dictionaryRepository.findByDictionaryKeyword("Polska").get(0).getDictionaryCategoryName().getCategoryName());
    	Assert.assertEquals("Polska", dictionaryRepository.findByDictionaryKeyword("Polska").get(0).getDictionaryKeyword());
    	Assert.assertEquals(1, dictionaryRepository.count());
    }

}
