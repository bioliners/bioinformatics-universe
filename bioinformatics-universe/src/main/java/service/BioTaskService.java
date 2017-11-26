package service;

import exceptions.IncorrectRequestException;
import model.request.BioTaskRequest;

import java.util.List;

public interface BioTaskService {
    List<String> runProgram(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException;
}

