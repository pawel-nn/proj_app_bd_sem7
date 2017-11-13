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

import app.dataTransportObject.ProducerDTO;
import app.model.Producer;
import app.model.repository.ProducerRepository;
import app.validation.ProducerValidation;

@Service
public class ProducerService {

    private static int MAX_ROWS_PER_PAGE = 20;	
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
	@Autowired
	private DatabaseLogService databaseLogService;
    
    @Autowired
    private ProducerRepository producerRepository;
    
    @Autowired
    private ProducerValidation producerValidation;
    
    public ArrayList<Producer> findAllProducers() {
    	return (ArrayList<Producer>) producerRepository.findAll();
    }
    
	public void getProducersByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) producerRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<Producer> producerList = (Page<Producer>) producerRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.DESC, "producerId"));
    	if(producerList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("producerList", producerList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber);   
    	log.info("PrS: Get product list");
    	databaseLogService.info("PrS: Get product list");
	}

	public ProducerDTO saveNewProducer(ProducerDTO producerDTO) {
		producerDTO = producerValidation.validateNewProduct(producerDTO);
		if(producerDTO.isValid()) {
			try {
				Producer producer = new Producer(producerDTO.getViewObject().getProducerName());
				producerRepository.save(producer);
				log.info("PrS: New producer created: {}", producerDTO.getViewObject().getProducerName());
				databaseLogService.info("PrS: New producer created: " + producerDTO.getViewObject().getProducerName());
				return producerDTO;
			} catch (Exception e) {
				log.error("PrS: Producer: {} cannot be created.", producerDTO.getViewObject().getProducerName());
				databaseLogService.error("PrS: Producer: " +producerDTO.getViewObject().getProducerName()+ " cannot be created." );
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}
	
	public boolean deleteProducer(Integer producerId) {
		try {
			Producer producer = producerRepository.findByProducerId(producerId);
			producerRepository.delete(producer);
			log.info("PrS: Producer of id: {} was deleted.", producerId);
			databaseLogService.info("PrS: Producer of id: " +producerId+ "was deleted.");
			return true;
		} catch ( Exception e ) {
			log.error("PrS: Producer of id: {} cannot be deleted.", producerId);
			databaseLogService.info("PrS: Producer of id: " +producerId+ " cannot be deleted.");
			return false;
		}	
	}

}
