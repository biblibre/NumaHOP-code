package org.numahop.numahop.service.document;

import org.numahop.numahop.domain.AbstractDomainObject;
import org.numahop.numahop.domain.document.DocPage;
import org.numahop.numahop.exception.PgcnTechnicalException;
import org.numahop.numahop.repository.document.DocPageRepository;
import org.numahop.numahop.service.storage.BinaryStorageManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocPageService {

	@Autowired
	private DocPageRepository docPageRepository;

	@Autowired
	private BinaryStorageManager bm;

	@Transactional
	public DocPage save(final DocPage docPage) {
		return docPageRepository.save(docPage);
	}

	@Transactional
	public void delete(final DocPage docPage, final String libraryId) throws PgcnTechnicalException {
		try {
			bm.deleteAllFilesFromPage(docPage, libraryId);
		}
		catch (final IOException e) {
			throw new PgcnTechnicalException(e);
		}
		docPageRepository.delete(docPage);
	}

	@Transactional
	public DocPage findOne(final String identifier) {
		return docPageRepository.findById(identifier).orElse(null);
	}

	@Transactional
	public List<String> getAllPageIdsByDigitalDocumentId(final String identifier) {
		final List<DocPage> dps = docPageRepository.getAllByDigitalDocumentIdentifier(identifier);
		return dps.stream().map(AbstractDomainObject::getIdentifier).collect(Collectors.toList());
	}

	@Transactional
	public Map<String, List<DocPage>> getPagesByProjectId(final String projectId) {
		final List<Object[]> results = docPageRepository.getPagesByProjectIdentifier(projectId);
		final Map<String, List<DocPage>> pages = new HashMap<>();
		results.forEach(res -> {
			final String key = (String) res[0];
			final DocPage pg = (DocPage) res[1];
			if (pages.get(key) == null) {
				final List<DocPage> dps = new ArrayList<>();
				dps.add(pg);
				pages.put((String) res[0], dps);
			}
			else {
				pages.get(key).add(pg);
			}

		});
		return pages;
	}

	@Transactional
	public Map<String, List<DocPage>> getPagesByLotId(final String lotId) {
		final List<Object[]> results = docPageRepository.getPagesByLotIdentifier(lotId);
		final Map<String, List<DocPage>> pages = new HashMap<>();
		results.forEach(res -> {
			final String key = (String) res[0];
			final DocPage pg = (DocPage) res[1];
			if (pages.get(key) == null) {
				final List<DocPage> dps = new ArrayList<>();
				dps.add(pg);
				pages.put((String) res[0], dps);
			}
			else {
				pages.get(key).add(pg);
			}

		});
		return pages;
	}

}
