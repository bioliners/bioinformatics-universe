package controller;

import biojobs.BioJob;
import biojobs.BioJobResult;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import service.EvolutionService;
import service.StorageService;
import exceptions.IncorrectRequestException;
import enums.BioPrograms;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.ExecutionException;

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
    public String processRequest(EvolutionRequest evolutionRequest) throws IncorrectRequestException, ExecutionException, InterruptedException {
        Integer jobId = null;

        if (evolutionRequest.getCommandToBeProcessedBy().equals(BioPrograms.CREATE_COGS.getProgramName())) {
            //Split it to several functions because 'createCogs' method is asynchronous
            //and files in 'listOfFiles' field of evolutionRequest are got cleared at the end of request processing.
            String[] locations = evolutionService.createDirs();
            EvolutionInternal evolutionInternal = evolutionService.storeFilesAndPrepareCommandArguments(evolutionRequest, locations);
            jobId = evolutionInternal.getJobId();
            evolutionService.createCogs(evolutionInternal);
        }
        return String.valueOf(jobId);
    }

    @GetMapping(value="/get-filename", produces="text/plain")
    @ResponseBody
    public String getFileNameIfReady(@RequestParam("jobId") Integer jobId) {
        BioJob bioJob = null;
	    if (jobId != null ) {
            bioJob = evolutionService.getBioJobIfFinished(jobId);
        }
        return bioJob != null ? bioJob.getBioJobResultList().get(0).getResultFileName() : "notReady";
	}

    @GetMapping(value="/get-result")
    public void getFileIfReady(int jobId, HttpServletResponse response) throws IOException {
	    BioJob bioJob = evolutionService.getBioJobIfFinished(jobId);

        response.setContentType("text/plain");
        BioJobResult bioJobResult = bioJob.getBioJobResultList().get(0);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + bioJobResult.getResultFileName());

        OutputStream outputStream = response.getOutputStream();
        OutputStream buffOutputStream= new BufferedOutputStream(outputStream);
        OutputStreamWriter outputwriter = new OutputStreamWriter(buffOutputStream);

        outputwriter.write(bioJobResult.getResultFile());
        outputwriter.flush();
        outputwriter.close();
    }


    @Override
    void addToModelCommon(Model model) {
        model.addAttribute("mainTab", "evolution");
        model.addAttribute("specificJs", "/js/evolution.js");
    }

}
