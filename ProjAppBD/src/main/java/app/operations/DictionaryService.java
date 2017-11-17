package app.operations;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import app.model.Dictionary;
import app.model.DictionaryCategoryName;
import app.model.repository.DictionaryRepository;

@Service
public class DictionaryService {
    
    private static int MAX_ROWS_PER_PAGE = 10;
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	@Autowired
	private DatabaseLogService databaseLogService;
    
    @Autowired
    private DictionaryRepository dictionaryRepository;

	public void saveDictionaryKeyword(String keyword, Integer idx, String categoryName) {
		try {
			DictionaryCategoryName dictionaryCategoryName = new DictionaryCategoryName(categoryName);
			Dictionary dictionary = new Dictionary(dictionaryCategoryName, keyword, idx);
			dictionary = dictionaryRepository.save(dictionary);
			log.info("DS: New keyword created: {}.", keyword);
			databaseLogService.info("DS: New keyword created: " + keyword);
		} catch(Exception e) {
			log.error("DS: Keyword: {}, cannot be created.", keyword);
			databaseLogService.error("DS: Keyword: " +keyword+ ", cannot be created.");
			e.printStackTrace();
			return;
		}
	}

	public void getDictionaryListByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) dictionaryRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<Dictionary> dictionaryList = (Page<Dictionary>) dictionaryRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.ASC, "dictionaryKeyword"));
    	if(dictionaryList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("dictionaryList", dictionaryList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber); 
    	log.info("DS: Get keyword list");
    	databaseLogService.info("DS: Get keyword list");
	}

}
