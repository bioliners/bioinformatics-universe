package serviceimpl;

import org.springframework.stereotype.Service;

import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;
import service.EvolutionService;

@Service
public class EvolutionServiceImpl implements EvolutionService {
	@Override
	public String createCogs(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		return null;
	}
}
