package controller;

import java.io.IOException;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exceptions.StorageFileNotFoundException;
import model.request.SequenceRequest;
import service.SequenceService;
import service.StorageService;

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
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(SequenceController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList()));
        model.addAttribute("tab", "sequence");
        model.addAttribute("sequenceTab", "get-by-name");

        return "sequence";
    }

    @GetMapping("/get-by-name/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {


        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
    
    @PostMapping(value="/get-by-name", produces="application/json",consumes="application/json")
    public String handleFileUpload2(SequenceRequest sequence) {
    	
        String resultWithrelativePath = sequenceService.getByName(sequence);
        
        Resource file = storageService.loadAsResource("relativePath");
                
        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "serveFile", resultWithrelativePath).build().toString();
    }

    
    
    @PostMapping("/get-by-name")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    	

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/sequence/get-by-name";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
