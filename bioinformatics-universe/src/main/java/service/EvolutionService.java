package service;

import biojobs.BioJob;
import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;

import java.util.concurrent.CompletableFuture;

public interface EvolutionService {
	CompletableFuture<Integer> createCogs(EvolutionRequest evolutionRequest) throws IncorrectRequestException;

	BioJob getBioJobIfFinished(int jobId);

}
