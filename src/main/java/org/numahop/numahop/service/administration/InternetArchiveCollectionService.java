package org.numahop.numahop.service.administration;

import org.numahop.numahop.domain.administration.InternetArchiveCollection;
import org.numahop.numahop.domain.administration.InternetArchiveConfiguration;
import org.numahop.numahop.repository.administration.InternetArchiveCollectionRepository;
import org.numahop.numahop.security.SecurityUtils;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service de gestion des {@link InternetArchiveConfiguration}
 *
 * @author jbrunet Créé le 19 avr. 2017
 */
@Service
public class InternetArchiveCollectionService {

	private final InternetArchiveCollectionRepository iaCollectionRepository;

	@Autowired
	public InternetArchiveCollectionService(final InternetArchiveCollectionRepository iaCollectionRepository) {
		this.iaCollectionRepository = iaCollectionRepository;
	}

	@Transactional(readOnly = true)
	public InternetArchiveCollection findOne(final String identifier) {
		return iaCollectionRepository.findById(identifier).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<InternetArchiveCollection> findAll(final List<String> identifiers) {
		if (CollectionUtils.isEmpty(identifiers)) {
			return iaCollectionRepository.findAllWithDependencies();
		}
		else {
			return iaCollectionRepository.findAllForLibraries(identifiers);
		}
	}

	@Transactional(readOnly = true)
	public InternetArchiveCollection findByName(final String name) {
		final String libraryId = SecurityUtils.getCurrentUserLibraryId();
		return iaCollectionRepository.findByNameAndLibrary(name, libraryId);
	}

}
