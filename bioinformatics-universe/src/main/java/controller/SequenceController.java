package controller;

import java.io.IOException;

import exceptions.IncorrectRequestException;
import exceptions.BioServerException;
import model.request.SequenceRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        return "sequence-get-by-name";
    }

    @GetMapping("/make-unique")
    public String makeUniquePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("sequenceTab", "make-unique");
        return "sequence-make-unique";
    }

    @GetMapping("/delete-by-name")
    public String deleteByNamePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("sequenceTab", "delete-by-name");
        return "sequence-delete-by-name";
    }
 

    @GetMapping("/files/{filename:.+}")
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
    public String getByName(SequenceRequest sequence) throws IncorrectRequestException {
        String fileName = sequenceService.getByName(sequence);

        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
    }


    @PostMapping(value="/make-unique", produces="text/plain")
    @ResponseBody
    public String makeUnique(SequenceRequest sequence) throws IncorrectRequestException {
        String fileName = sequenceService.getByName(sequence);
        //String fileName = sequenceService.makeUnique(sequence);

        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
    }
    
    

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
