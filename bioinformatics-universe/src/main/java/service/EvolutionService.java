package service;

import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;

public interface EvolutionService {
	String createCogs(EvolutionRequest evolutionRequest) throws IncorrectRequestException;

}
