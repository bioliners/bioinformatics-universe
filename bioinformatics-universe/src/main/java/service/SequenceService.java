package service;

import exceptions.IncorrectRequestException;
import model.request.SequenceRequest;

public interface SequenceService {
	
	String getByName(SequenceRequest sequence) throws IncorrectRequestException;
	String makeUnique();
	String extract();

}
