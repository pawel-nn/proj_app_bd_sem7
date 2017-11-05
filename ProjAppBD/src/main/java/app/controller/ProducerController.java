package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.dataTransportObject.ProducerDTO;
import app.dataTransportObject.ProductCategoryDTO;
import app.dataTransportObject.ProductDTO;
import app.model.Product;
import app.operations.ProducerService;
import app.operations.ProductCategoryService;
import app.operations.ProductService;
import app.viewObject.ProducerVO;
import app.viewObject.ProductCategoryVO;
import app.viewObject.ProductVO;

@Controller
public class ProducerController {

	@Autowired ProducerService producerService;
	
    @GetMapping("/owner/producerList")
    public String producerList(@RequestParam(value="page", required=false) Integer page, ProducerVO producerVO, Model model) {
    	producerService.getProducersByPagination(page, model);
    	return "producer_list";
    }

	@PostMapping("/owner/producerList/addProducer")
	public String addNewProductCategoryPOST(ProducerVO producerVO, Model m) {
		ProducerDTO producerDTO = new ProducerDTO(producerVO);
		producerDTO = producerService.saveNewProducer(producerDTO);
		if(producerDTO == null) {
			m.addAttribute("msg", "Nie można dodac producenta.");
			return "redirect:/owner/producerList";
		}
		m.addAttribute("msg", "Dodano nowego producenta.");
		return "redirect:/owner/producerList";
	}
	
    @GetMapping("/owner/producerList/deleteProducer")
    public String deleteProducerGET(@RequestParam(value="page", required=true) Integer page,
    							   @RequestParam(value="producerId", required=true) Integer producerId) {
    	producerService.deleteProducer(producerId);
    	return "redirect:/owner/producerList?page=" + page;
	}
	
}
