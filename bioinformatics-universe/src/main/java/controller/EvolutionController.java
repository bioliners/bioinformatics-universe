package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vadim on 8/13/17.
 */

@Controller
@RequestMapping("/evolution")
public class EvolutionController {




    public EvolutionController() {

    }

    @GetMapping("/create-cogs")
    public String getByNamePage(Model model) {
        model.addAttribute("tab", "evolution");
        model.addAttribute("evolutionTab", "create-cogs");
        return "evolution-create-cogs";
    }


//    @PostMapping(value="/process-request", produces="text/plain")
//    @ResponseBody
//    public String processRequest(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
//        String fileName = "";
//        //Needs to be refactored
//        if (evolutionRequest.getCommandToBeProcessedBy().equals("create-cogs")) {
//            fileName = evolutionService.createCogs(evolutionRequest);
//            return MvcUriComponentsBuilder.fromMethodName(SequenceController.class, "handleFileDownload", fileName).build().toString();
//        }
//
//    }


}
