package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import service.StorageService;


/**
 * Created by vadim on 8/13/17.
 */

public abstract class BioUniverseController {

    private final StorageService storageService;

    @Autowired
    public BioUniverseController(StorageService storageService) {
        this.storageService = storageService;
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

    abstract void addToModelCommon(Model model);
}
