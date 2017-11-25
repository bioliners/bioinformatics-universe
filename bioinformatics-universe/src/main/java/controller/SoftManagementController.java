package controller;

import model.request.BioDropRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SoftManagementService;
import service.StorageService;

import java.io.IOException;

/**
 * Created by vadim on 11/22/17.
 */

@Controller
@RequestMapping("/bioadmin")
public class SoftManagementController extends BioUniverseController {

    @Autowired
    private final SoftManagementService softManagementService;

    public SoftManagementController(StorageService storageService, SoftManagementService softManagementService) {
        super(storageService);
        this.softManagementService = softManagementService;
    }

    @PostMapping(value="/drop-program", produces="text/plain")
    @ResponseBody
    public String dropProgram(BioDropRequest bioDropRequest) throws IOException {
        return softManagementService.dropProgram(bioDropRequest);
    }

    //TODO
    @Override
    void addToModelCommon(Model model) {

    }
}
