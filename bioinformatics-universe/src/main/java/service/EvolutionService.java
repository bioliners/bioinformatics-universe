package service;

import biojobs.BioJob;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;


public interface EvolutionService {
	void createCogs(EvolutionInternal evolutionInternal) throws IncorrectRequestException;

	String[] createDirs();

	EvolutionInternal storeFilesAndPrepareCommandArguments (EvolutionRequest evolutionRequest, String[] locations) throws IncorrectRequestException;

	BioJob getBioJobIfFinished(int jobId);

}
