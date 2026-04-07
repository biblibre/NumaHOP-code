package org.numahop.numahop.service.exchange.internetarchive.mapper;

import org.numahop.numahop.domain.document.ArchiveCollection;
import org.numahop.numahop.domain.document.ArchiveContributor;
import org.numahop.numahop.domain.document.ArchiveCoverage;
import org.numahop.numahop.domain.document.ArchiveCreator;
import org.numahop.numahop.domain.document.ArchiveHeader;
import org.numahop.numahop.domain.document.ArchiveItem;
import org.numahop.numahop.domain.document.ArchiveLanguage;
import org.numahop.numahop.domain.document.ArchiveSubject;
import org.numahop.numahop.service.exchange.internetarchive.InternetArchiveItemDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArchiveItemMapper {

	ArchiveItemMapper INSTANCE = Mappers.getMapper(ArchiveItemMapper.class);

	InternetArchiveItemDTO archiveItemToInternetArchiveItemDTO(ArchiveItem item);

	default List<String> archiveSubjectToString(final Set<ArchiveSubject> archiveSubject) {
		return archiveSubject.stream().map(ArchiveSubject::getValue).collect(Collectors.toList());
	}

	default List<String> archiveCollectionToString(final Set<ArchiveCollection> archiveCollection) {
		return archiveCollection.stream().map(ArchiveCollection::getValue).collect(Collectors.toList());
	}

	default List<String> archiveCoverageToString(final Set<ArchiveCoverage> archiveCoverage) {
		return archiveCoverage.stream().map(ArchiveCoverage::getValue).collect(Collectors.toList());
	}

	default List<String> archiveContributorToString(final Set<ArchiveContributor> archiveContributor) {
		return archiveContributor.stream().map(ArchiveContributor::getValue).collect(Collectors.toList());
	}

	default List<String> archiveCreatorToString(final Set<ArchiveCreator> archiveCreator) {
		return archiveCreator.stream().map(ArchiveCreator::getValue).collect(Collectors.toList());
	}

	default List<String> archiveLanguageToString(final Set<ArchiveLanguage> archiveLanguage) {
		return archiveLanguage.stream().map(ArchiveLanguage::getValue).collect(Collectors.toList());
	}

	default List<InternetArchiveItemDTO.CustomHeader> archiveHeaderToCustomHeader(
			final Set<ArchiveHeader> archiveHeaders) {
		final List<InternetArchiveItemDTO.CustomHeader> customHeaders = new ArrayList<>();
		for (final ArchiveHeader archiveHeader : archiveHeaders) {
			final InternetArchiveItemDTO.CustomHeader customHeader = new InternetArchiveItemDTO.CustomHeader();
			customHeader.setValue(archiveHeader.getValue());
			customHeader.setType(archiveHeader.getType());
			customHeaders.add(customHeader);
		}
		return customHeaders;
	}

}
