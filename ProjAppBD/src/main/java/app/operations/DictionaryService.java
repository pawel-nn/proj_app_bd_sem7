package app.operations;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

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
import app.model.repository.DictionaryCategoryNameRepository;
import app.model.repository.DictionaryRepository;

@Service
public class DictionaryService {

	private static int MAX_ROWS_PER_PAGE = 10;
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private DatabaseLogService databaseLogService;

	@Autowired
	private DictionaryRepository dictionaryRepository;

	@Autowired
	private DictionaryCategoryNameRepository dictionaryCategoryNameRepository;

	public void saveDictionaryKeyword(String keyword, Integer idx, String categoryName) {
		try {
			DictionaryCategoryName dictionaryCategoryName;
			ArrayList<DictionaryCategoryName> list = dictionaryCategoryNameRepository.findByCategoryName(categoryName);
			if(list.size() == 0) {
				dictionaryCategoryName = new DictionaryCategoryName(categoryName);
				dictionaryCategoryNameRepository.save(dictionaryCategoryName);
			} else {
				dictionaryCategoryName = list.get(0);
			}
			Dictionary dictionary = new Dictionary(dictionaryCategoryName, keyword, idx);
			dictionary = dictionaryRepository.save(dictionary);
			log.info("DS: New keyword created: {}.", keyword);
			databaseLogService.info("DS: New keyword created: " + keyword);
		} catch(Exception e) {
			log.error("DS: Keyword: {}, cannot be created.", keyword);
			databaseLogService.error("DS: Keyword: " +keyword+ ", cannot be created.");
		}
	}


	public void updateDictionaryKeyword(String oldKeyword, String newKeyword) {
		try {
			Dictionary dictionary = dictionaryRepository.findByDictionaryKeyword(oldKeyword).get(0);
			dictionary.setDictionaryKeyword(newKeyword);
			dictionary = dictionaryRepository.save(dictionary);
			log.info("DS: Keyword update, from {} to {}.", oldKeyword, newKeyword);
			databaseLogService.info("DS: Keyword update, from " + oldKeyword + " to " + newKeyword);
		} catch(Exception e) {
			log.error("DS: Keyword: {}, cannot be updated to {}.", oldKeyword, newKeyword);
			databaseLogService.error("DS: Keyword: " +oldKeyword+ ", uppdated to: " +newKeyword);
		}
	}


	public void deleteDictionaryKeyword(String keyword) {
		try {
			Dictionary dictionary = dictionaryRepository.findByDictionaryKeyword(keyword).get(0);
			dictionaryRepository.delete(dictionary);
			log.info("DS: Keyword deleted: {}.", keyword);
			databaseLogService.info("DS: Keyword deleted: " + keyword);
		} catch(Exception e) {
			log.error("DS: Keyword deleted: {}.", keyword);
			databaseLogService.error("DS: Keyword deleted:" + keyword);
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
