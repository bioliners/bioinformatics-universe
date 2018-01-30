package service;

import biojobs.BioJob;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;


public interface EvolutionService {
	void runMainProgram(EvolutionInternal evolutionInternal) throws IncorrectRequestException;

	String[] createDirs();
	String[] createDirsConcat();

	EvolutionInternal storeFilesAndPrepareCommandArguments (EvolutionRequest evolutionRequest, String[] locations) throws IncorrectRequestException;
	EvolutionInternal storeFilesAndPrepareCommandArgumentsConcat(final EvolutionRequest evolutionRequest, String[] locations) throws IncorrectRequestException;
	BioJob getBioJobIfFinished(int jobId);

}
