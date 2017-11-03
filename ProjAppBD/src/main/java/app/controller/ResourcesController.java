package app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ResourcesController {
	
	private static final String rootPath = "C:\\";
	private static final String dirPath = rootPath + File.separator + "projectFiles";
    
    @GetMapping(value = "/resources/images/{photoName}", produces = "image/png")
    public ResponseEntity<byte[]> getProductPhoto(@PathVariable(value="photoName") String photoName, Model model) {	
    	System.out.println("photoName=" +photoName);
		String filePath = dirPath + File.separator + photoName;
		File photo = new File(filePath);
		byte[] bytes = new byte[(int) photo.length()];
		try {
			if (photo.exists()) {
				BufferedInputStream stream;
				stream = new BufferedInputStream(new FileInputStream(photo));
				stream.read(bytes);
				stream.close();	
			} else {
				InputStream is = ResourcesController.class.getResourceAsStream("/no-photo.png");
				bytes = new byte[1000];
				is.read(bytes);
			    return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }
	
}
