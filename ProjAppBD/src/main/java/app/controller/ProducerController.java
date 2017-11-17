package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.dataTransportObject.ProducerDTO;
import app.operations.ProducerService;
import app.viewObject.ProducerVO;

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
    							   @RequestParam(value="oId", required=true) Integer oId) {
    	producerService.deleteProducer(oId);
    	return "redirect:/owner/producerList?page=" + page;
	}
	
}
