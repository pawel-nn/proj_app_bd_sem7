package app.operations;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import app.dataTransportObject.ProductDTO;
import app.model.Producer;
import app.model.Product;
import app.model.ProductCategory;
import app.model.ProductImage;
import app.model.repository.ProducerRepository;
import app.model.repository.ProductCategoryRepository;
import app.model.repository.ProductImageRepository;
import app.model.repository.ProductRepository;
import app.validation.ProductValidation;
import app.viewObject.ProductVO;

@Service
public class ProductService {

	private static final String rootPath = "C:\\";
	private static final String dirPath = rootPath + File.separator + "projectFiles";
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private static int MAX_ROWS_PER_PAGE = 10;	
    
	@Autowired
	private DatabaseLogService databaseLogService;
    
    @Autowired
    private ProductRepository productRepository;
	
    @Autowired
    private ProducerRepository producerRepository;
    
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    
    @Autowired
    private ProductImageRepository productImageRepository;
    
    @Autowired
    private ProductValidation productValidation;
    
	public ProductDTO saveProduct(MultipartFile productPhoto, ProductDTO productDTO) {
		productDTO = productValidation.validateNewProduct(productDTO);
		if(productDTO.isValid()) {
			try {
				ProductVO vo = productDTO.getViewObject();
				ProductImage productImage = productImageRepository.findByProductImageName(productPhoto.getOriginalFilename());
				if(productImage == null) {
					saveImage(productPhoto);
				    productImage = new ProductImage(StringUtils.isNotBlank(productPhoto.getOriginalFilename())?productPhoto.getOriginalFilename():"no_photo");
				} else {
					log.warn("PtS: Product image already in DB");
					databaseLogService.warn("PtS: Product image already in DB");
				}
				ProductCategory productCategory = productCategoryRepository.findByProductCategoryId(vo.getProductCategoryId());
				Producer producer = producerRepository.findByProducerId(vo.getProducerId());
				Product product = new Product(productImage, productCategory, producer, vo.getName(), vo.getValidatedPrice(), vo.getStockSize(), vo.getCode());
				product = productRepository.save(product);
				productDTO.getViewObject().setProductId(product.getProductId());
				log.info("PtS: New product: {}.", productDTO.getViewObject().getName());
				databaseLogService.info("PtS: New product: " + productDTO.getViewObject().getName());
				return productDTO;
			} catch (Exception e) {
				log.error("PtS: Product: {}, cannot be created.", productDTO.getViewObject().getName());
				databaseLogService.error("PtS: Product: " +productDTO.getViewObject().getName()+ ", cannot be created.");
				e.printStackTrace();
				return null;
			}
		} else {
	    	log.info("PtS: New product not valid.");
	    	databaseLogService.info("PtS: Product not valid.");
			return null;
		}
	}
	
	private void saveImage(MultipartFile productPhoto ) throws Exception{
		if (!productPhoto.isEmpty() && productPhoto.getOriginalFilename().split("\\.")[1].equals("png")) {
				byte[] bytes = productPhoto.getBytes();

				String filePath = dirPath + File.separator + productPhoto.getOriginalFilename();			
				File dir = new File(dirPath);
				if (!dir.exists())
					dir.mkdirs();

				File convertedFile = new File(filePath);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(convertedFile));
				stream.write(bytes);
				stream.close();	
				log.warn("PtS: New image saved.");
				databaseLogService.warn("PtS: New image saved.");
		} else {
			log.warn("PtS: Product image is null.");
			databaseLogService.warn("PtS: Product image is null.");
			return;
//			throw new NoSuchFileException("File not given!");
		}
	}
	
	public boolean deleteProduct(Integer productId) {
		try {
			Product product = productRepository.findByProductId(productId);
			product.setProductCategory(null);
			productRepository.save(product);
			productRepository.delete(productId);
			log.info("PtS: Product of id: {} was deleted.", productId);
			databaseLogService.info("PtS: Product of id: " +productId+ "was deleted.");
			return true;
		} catch ( Exception e ) {
			log.error("PtS: Product of id: {} cannot be deleted.", productId);
			databaseLogService.info("PtS: Product of id: " +productId+ " cannot be deleted.");
			return false;
		}
	}

	public void getProductsByPagination(Integer pageReq, Model model) {
    	int usersNumber = (int) productRepository.count();
    	int maxPagesNumber = (int) (Math.ceil(1.0*usersNumber/MAX_ROWS_PER_PAGE));
    	int pageNumber = 1;
    	if( maxPagesNumber == 0 )
    		maxPagesNumber = 1;
    	if(pageReq != null)
    		pageNumber = pageReq;
    	Page<Product> productList = (Page<Product>) productRepository.findAll(new PageRequest(pageNumber - 1, MAX_ROWS_PER_PAGE, Sort.Direction.ASC, "name"));
    	if(productList.getNumberOfElements() == 0) 
    		model.addAttribute("isEmpty", true); 
    	else 
    		model.addAttribute("isEmpty", false);
    	model.addAttribute("productList", productList);
    	model.addAttribute("pageNumber",pageNumber);
    	model.addAttribute("maxPagesNumber",maxPagesNumber); 
    	log.info("PtS: Get product List.");
    	databaseLogService.info("PtS: Get product List.");
	}

	public boolean existsProduct(Integer productId) {
		if(productRepository.exists(productId))
			return true;
		else 
			return false;
	}

	public Product getProductById(Integer productId) {
    	log.info("PtS: Get product by id: {}", productId);
    	databaseLogService.info("\"PtS: Get product by id: " + productId);
		return productRepository.findByProductId(productId);
	}

}
