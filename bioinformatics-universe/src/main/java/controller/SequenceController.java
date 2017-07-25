package controller;

import java.io.IOException;
import java.io.InputStream;

import model.request.SequenceRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import service.SequenceService;
import service.StorageService;
import exceptions.StorageFileNotFoundException;

@Controller
@RequestMapping("/sequence")
public class SequenceController {

	@Autowired
    private final StorageService storageService;

    @Autowired
    private final SequenceService sequenceService;
    
    public SequenceController(StorageService storageService, SequenceService sequenceService) {
        this.storageService = storageService;
        this.sequenceService = sequenceService;
    }
    
    @GetMapping("/get-by-name")
    public String getByNamePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("sequenceTab", "get-by-name");
        return "sequence";
    }
 

    @GetMapping("/get-by-name/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> handleFileDownload(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @PostMapping(value="/get-by-name", produces="text/plain")
    @ResponseBody
    public String getByName(SequenceRequest sequence) {
        String fileName = sequenceService.getByName(sequence);
        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
    }
    
    

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
