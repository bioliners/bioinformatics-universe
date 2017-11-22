package service;

import model.request.BioDropRequest;

import java.io.IOException;

public interface SoftManagementService {
    String dropProgram(BioDropRequest bioDropRequest) throws IOException;
}

