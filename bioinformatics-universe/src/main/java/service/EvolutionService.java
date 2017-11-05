package service;

import biojobs.BioJob;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;

import java.util.List;

public interface EvolutionService {
	void createCogs(EvolutionInternal evolutionInternal, List<String> locations) throws IncorrectRequestException;

	List<String> createDirs();

	EvolutionInternal storeFiles(EvolutionRequest evolutionRequest, String inputFilesLocation1) throws IncorrectRequestException;

	BioJob getBioJobIfFinished(int jobId);

}
