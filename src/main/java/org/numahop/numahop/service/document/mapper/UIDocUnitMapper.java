package org.numahop.numahop.service.document.mapper;

import org.numahop.numahop.domain.administration.CinesPAC;
import org.numahop.numahop.domain.administration.InternetArchiveCollection;
import org.numahop.numahop.domain.administration.omeka.OmekaList;
import org.numahop.numahop.domain.document.DigitalDocument;
import org.numahop.numahop.domain.document.DocUnit;
import org.numahop.numahop.domain.document.PhysicalDocument;
import org.numahop.numahop.domain.dto.administration.CinesPACDTO;
import org.numahop.numahop.domain.dto.administration.InternetArchiveCollectionDTO;
import org.numahop.numahop.domain.dto.administration.omeka.OmekaListDTO;
import org.numahop.numahop.domain.dto.document.DocUnitBibliographicRecordDTO;
import org.numahop.numahop.domain.dto.document.DocUnitDTO;
import org.numahop.numahop.domain.dto.library.LibraryDTO;
import org.numahop.numahop.domain.dto.lot.SimpleLotDTO;
import org.numahop.numahop.domain.dto.ocrlangconfiguration.OcrLanguageDTO;
import org.numahop.numahop.domain.dto.project.SimpleProjectDTO;
import org.numahop.numahop.domain.dto.train.SimpleTrainDTO;
import org.numahop.numahop.domain.library.Library;
import org.numahop.numahop.domain.lot.Lot;
import org.numahop.numahop.domain.project.Project;
import org.numahop.numahop.domain.train.Train;
import org.numahop.numahop.repository.document.BibliographicRecordRepository;
import org.numahop.numahop.service.administration.CinesPACService;
import org.numahop.numahop.service.administration.InternetArchiveCollectionService;
import org.numahop.numahop.service.administration.omeka.OmekaConfigurationService;
import org.numahop.numahop.service.administration.omeka.OmekaListService;
import org.numahop.numahop.service.library.LibraryService;
import org.numahop.numahop.service.lot.LotService;
import org.numahop.numahop.service.ocrlangconfiguration.OcrLanguageService;
import org.numahop.numahop.service.project.ProjectService;
import org.numahop.numahop.service.train.TrainService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UIDocUnitMapper {

	private final BibliographicRecordRepository bibliographicRecordRepository;

	private final LibraryService libraryService;

	private final LotService lotService;

	private final ProjectService projectService;

	private final TrainService trainService;

	private final InternetArchiveCollectionService iaCollectionService;

	private final CinesPACService cinesPACService;

	private final OmekaListService omekaListService;

	private final OmekaConfigurationService omekaConfigurationService;

	private final OcrLanguageService ocrLanguageService;

	@Autowired
	public UIDocUnitMapper(final BibliographicRecordRepository bibliographicRecordRepository,
			final LibraryService libraryService, final LotService lotService, final ProjectService projectService,
			final TrainService trainService, final InternetArchiveCollectionService iaCollectionService,
			final CinesPACService cinesPACService, final OmekaListService omekaListService,
			final OmekaConfigurationService omekaConfigurationService, final OcrLanguageService ocrLanguageService) {
		this.libraryService = libraryService;
		this.projectService = projectService;
		this.lotService = lotService;
		this.bibliographicRecordRepository = bibliographicRecordRepository;
		this.trainService = trainService;
		this.iaCollectionService = iaCollectionService;
		this.cinesPACService = cinesPACService;
		this.omekaListService = omekaListService;
		this.omekaConfigurationService = omekaConfigurationService;
		this.ocrLanguageService = ocrLanguageService;
	}

	@Transactional(readOnly = true)
	public void mapInto(final DocUnitDTO docDTO, final DocUnit doc) {

		doc.setArchivable(docDTO.getArchivable());
		doc.setDistributable(docDTO.getDistributable());
		doc.setCheckDelay(docDTO.getCheckDelay());
		doc.setCheckEndTime(docDTO.getCheckEndTime());
		doc.setEmbargo(docDTO.getEmbargo());
		doc.setPgcnId(docDTO.getPgcnId());
		doc.setLabel(docDTO.getLabel());
		doc.setType(docDTO.getType());
		doc.setRights(docDTO.getRights());
		doc.setCondReportType(docDTO.getCondReportType());
		doc.setDigitizingNotes(docDTO.getDigitizingNotes());
		doc.setFoundRefAuthor(docDTO.getFoundRefAuthor());
		doc.setProgressStatus(docDTO.getProgressStatus());
		doc.setRequestDate(docDTO.getRequestDate());
		doc.setAnswerDate(docDTO.getAnswerDate());
		doc.setImageHeight(docDTO.getImageHeight());
		doc.setImageWidth(docDTO.getImageWidth());

		final InternetArchiveCollectionDTO iaCollection = docDTO.getCollectionIA();
		if (iaCollection != null && iaCollection.getIdentifier() != null) {
			final InternetArchiveCollection internetArchiveCollection = iaCollectionService
				.findOne(iaCollection.getIdentifier());
			doc.setCollectionIA(internetArchiveCollection);
		}
		else {
			doc.setCollectionIA(null);
		}

		final CinesPACDTO cinesPACDTO = docDTO.getPlanClassementPAC();
		if (cinesPACDTO != null && cinesPACDTO.getIdentifier() != null) {
			final CinesPAC cinesPAC = cinesPACService.findOne(cinesPACDTO.getIdentifier());
			doc.setPlanClassementPAC(cinesPAC);
		}
		else {
			doc.setPlanClassementPAC(null);
		}

		final OmekaListDTO collecOmeka = docDTO.getOmekaCollection();
		if (collecOmeka != null && collecOmeka.getIdentifier() != null) {
			final OmekaList omekaCollection = omekaListService.findOne(collecOmeka.getIdentifier());
			doc.setOmekaCollection(omekaCollection);
		}
		else {
			doc.setOmekaCollection(null);
		}

		final OmekaListDTO itemOmeka = docDTO.getOmekaItem();
		if (itemOmeka != null && itemOmeka.getIdentifier() != null) {
			final OmekaList omekaItem = omekaListService.findOne(itemOmeka.getIdentifier());
			doc.setOmekaItem(omekaItem);
		}
		else {
			doc.setOmekaItem(null);
		}

		// Biliographic Records
		final List<DocUnitBibliographicRecordDTO> recordsDTO = docDTO.getRecords();
		if (recordsDTO != null) {
			doc.setRecords(recordsDTO.stream()
				.map(record -> bibliographicRecordRepository.findById(record.getIdentifier()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet()));
		}

		// Library
		final LibraryDTO library = docDTO.getLibrary();
		if (library != null && library.getIdentifier() != null) {
			final Library lib = libraryService.findOne(library.getIdentifier());
			doc.setLibrary(lib);
		}

		// Lot
		final SimpleLotDTO lotDTO = docDTO.getLot();
		final Lot lot;
		if (lotDTO != null && lotDTO.getIdentifier() != null) {
			lot = lotService.getOne(lotDTO.getIdentifier());
		}
		else {
			lot = null;
		}
		doc.setLot(lot);

		// Project
		final SimpleProjectDTO projectDTO = docDTO.getProject();
		if (projectDTO != null && projectDTO.getIdentifier() != null) {
			final Project project = projectService.findByIdentifier(projectDTO.getIdentifier());
			doc.setProject(project);
		}
		else {
			doc.setProject(null);
		}

		// Notes de controle => digital Document
		if (doc.getDigitalDocuments() != null && doc.getDigitalDocuments().size() == 1) {
			final DigitalDocument digDoc = doc.getDigitalDocuments().iterator().next();
			if (CollectionUtils.isNotEmpty(docDTO.getDigitalDocuments())) {
				digDoc.setCheckNotes(docDTO.getDigitalDocuments().iterator().next().getCheckNotes());
			}
		}

		// PhysicalDocuments
		if (doc.getPhysicalDocuments() != null && doc.getPhysicalDocuments().size() == 1) {
			final PhysicalDocument physicalDocument = doc.getPhysicalDocuments().iterator().next();
			physicalDocument.setDigitalId(docDTO.getDigitalId());
			if (docDTO.getPhysicalDocuments() != null && !docDTO.getPhysicalDocuments().isEmpty()) {
				final SimpleTrainDTO trainDTO = docDTO.getPhysicalDocuments().iterator().next().getTrain();
				if (trainDTO != null && trainDTO.getIdentifier() != null) {
					final Train train = trainService.getOne(trainDTO.getIdentifier());
					physicalDocument.setTrain(train);
				}
				else {
					physicalDocument.setTrain(null);
				}
			}
		}

		// langage OCR
		final OcrLanguageDTO langOcr = docDTO.getActiveOcrLanguage();
		if (langOcr != null && langOcr.getIdentifier() != null) {
			doc.setActiveOcrLanguage(ocrLanguageService.getOne(langOcr.getIdentifier()));
		}
		else if (lot != null && lot.getActiveOcrLanguage() != null) {
			doc.setActiveOcrLanguage(lot.getActiveOcrLanguage());
		}

	}

}
