package service;

import model.request.SequenceRequest;

public interface SequenceService {
	
	String getByName(SequenceRequest sequence);
	String makeUnique();
	String extract();

}
