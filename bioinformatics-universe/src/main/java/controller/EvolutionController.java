package controller;

import model.request.EvolutionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import service.EvolutionService;
import service.StorageService;
import exceptions.IncorrectRequestException;
import enums.BioPrograms;

/**
 * Created by vadim on 8/13/17.
 */

@Controller
@RequestMapping("/evolution")
public class EvolutionController extends BioUniverseController {

    @Autowired
	public final EvolutionService evolutionService;


	public EvolutionController(StorageService storageService, EvolutionService evolutionService) {
    	super(storageService);
    	this.evolutionService = evolutionService;
    }

    @GetMapping(value={"", "/", "/create-cogs"})
    public String getByNamePage(Model model) {
        addToModelCommon(model);
        model.addAttribute("subnavigationTab", BioPrograms.CREATE_COGS.getProgramName());
        return "main-view  :: addContent(" +
                "fragmentsMain='evolution-fragments', searchArea='evolution-create-cogs', " +
                "tab='evolution-navbar', filter='evolution-createCogs-filter')";
    }


    @PostMapping(value="/process-request", produces="text/plain")
    @ResponseBody
    public String processRequest(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
        String fileName = "";
        if (evolutionRequest.getCommandToBeProcessedBy().equals("createCogsProgram")) {
            fileName = evolutionService.createCogs(evolutionRequest);
        }
        return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
    }

    @Override
    void addToModelCommon(Model model) {
        model.addAttribute("mainTab", "evolution");
        model.addAttribute("specificJs", "/js/evolution.js");
    }

}
