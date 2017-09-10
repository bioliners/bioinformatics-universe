package controller;

import java.io.IOException;

import model.request.SequenceRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import service.SequenceService;
import service.StorageService;
import exceptions.IncorrectRequestException;
import exceptions.StorageFileNotFoundException;

@Controller
@RequestMapping("/sequence")
public class SequenceController extends BioUniverseController {

    @Autowired
    private final SequenceService sequenceService;
    
    public SequenceController(StorageService storageService, SequenceService sequenceService) {
    	super(storageService);
        this.sequenceService = sequenceService;
    }
    
    @GetMapping({"", "/", "/make-unique"})
    public String makeUniquePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("subnavigationTab", "make-unique");
        return "main-view  :: addContent(" +
                "fragmentsMain='sequence-fragments', searchArea='sequence-make-unique', " +
                "tab='sequence-navbar')";
    }
    
    @GetMapping("/get-by-name")
    public String getByNamePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("subnavigationTab", "get-by-name");

        return "main-view :: addContent(" +
                "fragmentsMain='sequence-fragments', searchArea='sequence-get-by-name', " +
                "tab='sequence-navbar', filter='sequence-getByName-filter')";
    }

    @GetMapping("/delete-by-name")
    public String deleteByNamePage(Model model) throws IOException {
        model.addAttribute("tab", "sequence");
        model.addAttribute("subnavigationTab", "delete-by-name");
        return "main-view  :: addContent(" +
                "fragmentsMain='sequence-fragments', searchArea='sequence-delete-by-name', " +
                "tab='sequence-navbar')";
    }
 
    @PostMapping(value="/process-request", produces="text/plain")
    @ResponseBody
    public String processRequest(SequenceRequest sequence) throws IncorrectRequestException {
        String fileName = "";
        //Needs to be refactored
        if (sequence.getCommandToBeProcessedBy().equals("get-by-name")) {
            fileName = sequenceService.getByName(sequence);
        } else if (sequence.getCommandToBeProcessedBy().equals("make-unique")) {
            fileName = sequenceService.makeUnique(sequence);
        }
        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
    }
    
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}
