package controller;

import model.request.BioDropRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by vadim on 11/22/17.
 */

@Controller
@RequestMapping("/bioadmin")
public class SoftManagementController {

    public String dropProgram(BioDropRequest bioDropRequest) {
        return null;
    }
}
