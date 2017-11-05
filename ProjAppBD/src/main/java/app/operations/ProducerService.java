package app.operations;

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
	
    @Autowired
    private ProducerRepository producerRepository;
    
    @Autowired
    private ProducerValidation producerValidation;
    
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
	}

	public ProducerDTO saveNewProducer(ProducerDTO producerDTO) {
		producerDTO = producerValidation.validateNewProduct(producerDTO);
		if(producerDTO.isValid()) {
			try {
				Producer producer = new Producer(producerDTO.getViewObject().getProducerName());
				producerRepository.save(producer);
			return producerDTO;
			} catch (Exception e) {
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
			return true;
		} catch ( Exception e ) {
			return false;
		}	
	}

}
