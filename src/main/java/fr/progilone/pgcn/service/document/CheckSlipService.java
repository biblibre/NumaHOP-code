package fr.progilone.pgcn.service.document;

import fr.progilone.pgcn.repository.document.CheckSlipRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckSlipService {

	private static final Logger LOG = LoggerFactory.getLogger(CheckSlipService.class);

	private final CheckSlipRepository checkSlipRepository;

	@Autowired
	public CheckSlipService(final CheckSlipRepository checkSlipRepository) {
		this.checkSlipRepository = checkSlipRepository;
	}

}
