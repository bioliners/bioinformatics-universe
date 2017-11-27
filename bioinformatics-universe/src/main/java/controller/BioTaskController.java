package controller;

import enums.BioPrograms;
import exceptions.IncorrectRequestException;
import model.request.EvolutionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.BioTaskLongRunningService;
import service.BioTaskService;
import service.StorageService;

@Controller
public class BioTaskController extends BioUniverseController {
    public final BioTaskService bioTaskService;
    public final BioTaskLongRunningService bioTaskLongRunningService;

    public BioTaskController(StorageService storageService, BioTaskService bioTaskService, BioTaskLongRunningService bioTaskLongRunningService) {
        super(storageService);
        this.bioTaskService = bioTaskService;
        this.bioTaskLongRunningService = bioTaskLongRunningService;
    }

    @GetMapping(value={"/{tab:tab.+}/{subTab:subTab.+}"})
    public String getByNamePage(@PathVariable("tab") String tab, @PathVariable("subTab") String subTab, Model model) {
        addToModelCommon(model);
        model.addAttribute("subnavigationTab", BioPrograms.CREATE_COGS.getProgramName());
        return "main-view  :: addContent(" +
                "fragmentsMain='evolution-fragments', searchArea='evolution-create-cogs', " +
                "tab='evolution-navbar', filter='evolution-createCogs-filter')";
    }

    @PostMapping(value="/{tab:tab.+}/process-request", produces="text/plain")
    @ResponseBody
    public String processRequest(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
        return null;
    }

    @Override
    public void addToModelCommon(Model model) {

    }
}
