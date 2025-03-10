package fr.progilone.pgcn.service.document.ui;

import fr.progilone.pgcn.domain.document.BibliographicRecord;
import fr.progilone.pgcn.domain.document.DocUnit;
import fr.progilone.pgcn.domain.dto.document.BibliographicRecordDTO;
import fr.progilone.pgcn.domain.dto.document.BibliographicRecordDcDTO;
import fr.progilone.pgcn.domain.dto.document.DocPropertyDTO;
import fr.progilone.pgcn.domain.dto.document.SimpleBibliographicRecordDTO;
import fr.progilone.pgcn.domain.dto.document.SimpleListBibliographicRecordDTO;
import fr.progilone.pgcn.exception.PgcnBusinessException;
import fr.progilone.pgcn.exception.PgcnValidationException;
import fr.progilone.pgcn.exception.message.PgcnError;
import fr.progilone.pgcn.exception.message.PgcnErrorCode;
import fr.progilone.pgcn.service.document.BibliographicRecordService;
import fr.progilone.pgcn.service.document.common.LanguageCodeService;
import fr.progilone.pgcn.service.document.mapper.BibliographicRecordMapper;
import fr.progilone.pgcn.service.document.mapper.SimpleBibliographicRecordMapper;
import fr.progilone.pgcn.service.document.mapper.UIBibliographicRecordMapper;
import fr.progilone.pgcn.service.util.transaction.VersionValidationService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service dédié à les gestion des vues des notices
 *
 * @author jbrunet
 */
@Service
public class UIBibliographicRecordService {

	private static final Logger LOG = LoggerFactory.getLogger(UIBibliographicRecordService.class);

	private final BibliographicRecordService bibliographicRecordService;

	private final UIBibliographicRecordMapper uiBibliographicRecordMapper;

	private final LanguageCodeService languageCodeService;

	@Autowired
	public UIBibliographicRecordService(final BibliographicRecordService bibliographicRecordService,
			final UIBibliographicRecordMapper uiBibliographicRecordMapper,
			final LanguageCodeService languageCodeService) {
		this.bibliographicRecordService = bibliographicRecordService;
		this.uiBibliographicRecordMapper = uiBibliographicRecordMapper;
		this.languageCodeService = languageCodeService;
	}

	@Transactional
	public BibliographicRecordDTO create(final BibliographicRecordDTO request) throws PgcnValidationException {
		final BibliographicRecord record = new BibliographicRecord();
		uiBibliographicRecordMapper.mapInto(request, record);

		try {
			final BibliographicRecord savedRecord = bibliographicRecordService.save(record);
			final BibliographicRecord docWithProperties = bibliographicRecordService
				.getOne(savedRecord.getIdentifier());
			return BibliographicRecordMapper.INSTANCE.bibliographicRecordToBibliographicRecordDTO(docWithProperties);

		}
		catch (final PgcnBusinessException e) {
			e.getErrors().forEach(semanthequeError -> request.addError(buildError(semanthequeError.getCode())));
			throw new PgcnValidationException(request);
		}
	}

	/**
	 * Mise à jour d'une notice
	 * @param request un objet contenant les informations necessaires à l'enregistrement
	 * d'une notice
	 * @return la notice nouvellement créée ou mise à jour
	 * @throws PgcnValidationException
	 */
	@Transactional
	public BibliographicRecordDTO update(final BibliographicRecordDTO request) throws PgcnValidationException {
		final BibliographicRecord record = bibliographicRecordService.getOne(request.getIdentifier());

		// Contrôle d'accès concurrents
		VersionValidationService.checkForStateObject(record, request);

		uiBibliographicRecordMapper.mapInto(request, record);

		try {
			final BibliographicRecord savedRecord = bibliographicRecordService.save(record);
			// Prise en compte des modifications de la notice dans l'export IA et CINES
			if (savedRecord.getDocUnit() != null) {
				savedRecord.getDocUnit().setArchiveItem(null);
				savedRecord.getDocUnit().setExportData(null);
			}

			final BibliographicRecordDTO dto = getOne(savedRecord.getIdentifier());

			// controle language
			final DocPropertyDTO docProp = request.getProperties()
				.stream()
				.filter(dp -> StringUtils.equals(dp.getType().getIdentifier(), "language"))
				.findFirst()
				.orElse(null);
			if (docProp != null) {
				// test valididite language saisi pour warning
				if (!languageCodeService.checkCinesLangCodeExists(docProp.getValue())) {
					final PgcnError.Builder builder = new PgcnError.Builder();
					dto.addError(builder.reinit()
						.setCode(PgcnErrorCode.RECORD_LANGUAGE_UNKNOWN)
						.setMessage("Attention: la propriété Language n'est pas valide pour l'export Cines.")
						.build());
				}
			}
			return dto;

		}
		catch (final PgcnBusinessException e) {
			e.getErrors().forEach(semanthequeError -> request.addError(buildError(semanthequeError.getCode())));
			throw new PgcnValidationException(request);
		}
	}

	private PgcnError buildError(final PgcnErrorCode pgcnErrorCode) {
		final PgcnError.Builder builder = new PgcnError.Builder();
		switch (pgcnErrorCode) {
			case DOC_UNIT_PGCN_ID_MANDATORY:
				builder.setCode(pgcnErrorCode).setField("pgcnId");
				break;
			default:
				break;
		}
		return builder.build();
	}

	@Transactional(readOnly = true)
	public BibliographicRecordDTO getOne(final String id) {
		final BibliographicRecord record = bibliographicRecordService.getOne(id);
		return BibliographicRecordMapper.INSTANCE.bibliographicRecordToBibliographicRecordDTO(record);
	}

	@Transactional(readOnly = true)
	public BibliographicRecordDcDTO getOneDc(final String id) {
		final BibliographicRecord record = bibliographicRecordService.getOne(id);
		return bibliographicRecordService.bibliographicRecordToDcDTO(record);
	}

	@Transactional
	public void delete(final List<String> ids) throws PgcnBusinessException {
		bibliographicRecordService.delete(ids);
	}

	@Transactional(readOnly = true)
	public List<SimpleBibliographicRecordDTO> findAllSimpleDTO() {
		final List<BibliographicRecord> docs = bibliographicRecordService.findAll();
		return docs.stream()
			// notice non rattachée, ou UD disponible
			.filter(rec -> rec.getDocUnit() == null || rec.getDocUnit().getState() == DocUnit.State.AVAILABLE)
			.map(BibliographicRecordMapper.INSTANCE::bibliographicRecordToSimpleBibliographicRecordDTO)
			.collect(Collectors.toList());
	}

	/**
	 * Recherches
	 * @param search
	 * @param libraries
	 * @param orphan
	 * @param page
	 * @param size
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<SimpleBibliographicRecordDTO> search(final String search, final List<String> libraries,
			final List<String> projects, final List<String> lots, final List<String> statuses,
			final LocalDate lastModifiedDateFrom, final LocalDate lastModifiedDateTo, final LocalDate createdDateFrom,
			final LocalDate createdDateTo, final Boolean orphan, final Integer page, final Integer size,
			final List<String> sorts) {
		final Page<BibliographicRecord> docs = bibliographicRecordService.search(search, libraries, projects, lots,
				statuses, Collections.emptyList(), lastModifiedDateFrom, lastModifiedDateTo, createdDateFrom,
				createdDateTo, orphan, page, size, sorts);
		return docs.map(BibliographicRecordMapper.INSTANCE::bibliographicRecordToSimpleBibliographicRecordDTO);
	}

	/**
	 * Recherches
	 * @param search
	 * @param libraries
	 * @param orphan
	 * @param page
	 * @param size
	 * @param sorts
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<SimpleListBibliographicRecordDTO> searchAsList(final String search, final List<String> libraries,
			final List<String> projects, final List<String> lots, final List<String> statuses,
			final List<String> trains, final LocalDate lastModifiedDateFrom, final LocalDate lastModifiedDateTo,
			final LocalDate createdDateFrom, final LocalDate createdDateTo, final Boolean orphan, final Integer page,
			final Integer size, final List<String> sorts) {
		final Page<BibliographicRecord> docs = bibliographicRecordService.search(search, libraries, projects, lots,
				statuses, trains, lastModifiedDateFrom, lastModifiedDateTo, createdDateFrom, createdDateTo, orphan,
				page, size, sorts);
		return docs.map(SimpleBibliographicRecordMapper.INSTANCE::docUnitToSimpleListDocUnitDTO);
	}

	@Transactional(readOnly = true)
	public List<SimpleBibliographicRecordDTO> findAllSimpleDTOForDocUnit(final String docUnitId) {
		final List<BibliographicRecord> records = bibliographicRecordService.findAllByDocUnitId(docUnitId);
		return records.stream()
			.map(BibliographicRecordMapper.INSTANCE::bibliographicRecordToSimpleBibliographicRecordDTO)
			.collect(Collectors.toList());
	}

	@Transactional
	public BibliographicRecordDTO duplicate(final String id) {
		final BibliographicRecord duplRecord = bibliographicRecordService.duplicate(id);
		return getOne(duplRecord.getIdentifier());
	}

	/**
	 * On a besoin de la notice.
	 * @param docUnit
	 * @return
	 */
	@Transactional
	// FIXME: FACTORISE THIS METHOD TO AVOID DUPLICATE CODE (OmekaService: method
	// getDataRecord)
	public BibliographicRecordDcDTO getBibliographicRecordDcDTOFromDocUnit(final DocUnit docUnit) {
		final BibliographicRecordDcDTO dto;

		if (docUnit.getRecords().iterator().hasNext()) {
			final BibliographicRecord record = docUnit.getRecords().iterator().next();
			dto = getOneDc(record.getIdentifier());
		}
		else {
			dto = null;
		}
		return dto;
	}

}
