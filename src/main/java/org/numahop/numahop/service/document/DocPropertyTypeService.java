package org.numahop.numahop.service.document;

import org.numahop.numahop.domain.document.DocPropertyType;
import org.numahop.numahop.domain.document.DocPropertyType.DocPropertySuperType;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import org.numahop.numahop.repository.document.DocPropertyTypeRepository;
import org.numahop.numahop.service.exchange.MappingService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DocPropertyTypeService {

	private final DocPropertyTypeRepository docPropertyTypeRepository;

	private final DocPropertyService docPropertyService;

	private final MappingService mappingService;

	@Autowired
	public DocPropertyTypeService(final DocPropertyTypeRepository docPropertyTypeRepository,
			final DocPropertyService docPropertyService, final MappingService mappingService) {
		this.docPropertyTypeRepository = docPropertyTypeRepository;
		this.docPropertyService = docPropertyService;
		this.mappingService = mappingService;
	}

	@Transactional(readOnly = true)
	public List<DocPropertyType> findAll() {
		return docPropertyTypeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<DocPropertyType> findCustom() {

		final DocPropertyType.DocPropertySuperType[] customTypes = { DocPropertySuperType.CUSTOM,
				DocPropertySuperType.CUSTOM_ARCHIVE, DocPropertySuperType.CUSTOM_CINES,
				DocPropertySuperType.CUSTOM_OMEKA };
		return docPropertyTypeRepository.findAllBySuperTypeIn(Arrays.asList(customTypes));
	}

	@Transactional(readOnly = true)
	public List<DocPropertyType> findAllBySuperType(final DocPropertyType.DocPropertySuperType superType) {
		return docPropertyTypeRepository.findAllBySuperType(superType);
	}

	@Transactional(readOnly = true)
	public List<DocPropertyType> findAllByIdentifierIn(final List<String> fields) {
		return CollectionUtils.isNotEmpty(fields) ? docPropertyTypeRepository.findAllById(fields)
				: Collections.emptyList();
	}

	@Transactional(readOnly = true)
	public DocPropertyType findOne(final String identifier) {
		return docPropertyTypeRepository.findById(identifier).orElse(null);
	}

	@Transactional
	public DocPropertyType save(final DocPropertyType propertyType) throws PgcnValidationException {
		validateSave(propertyType);
		handleRank(propertyType);
		return docPropertyTypeRepository.save(propertyType);
	}

	@Transactional
	public void delete(final DocPropertyType type) throws PgcnValidationException {
		validateDeletion(type);
		docPropertyTypeRepository.delete(type);
	}

	/**
	 * Validation de la sauvegarde
	 * @param type
	 * @throws PgcnValidationException
	 */
	private void validateSave(final DocPropertyType type) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// Le libellé est renseigné
		if (StringUtils.isBlank(type.getLabel())) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DOC_PROP_TYPE_LABEL_MANDATORY).setField("label").build());
		}

		if (!errors.isEmpty()) {
			type.setErrors(errors);
			throw new PgcnValidationException(type, errors);
		}
	}

	/**
	 * Validation de la suppression
	 * @param type
	 * @throws PgcnValidationException
	 */
	private void validateDeletion(final DocPropertyType type) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// Le type de propriété est utilisé sur une propriété
		Integer count = docPropertyService.countByType(type);
		if (count != null && count > 0) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DOC_PROP_TYPE_DEL_USED_PROP).build());
		}

		// Le type de propriété est utilisé sur une règle de mapping
		count = mappingService.countByPropertyType(type);
		if (count != null && count > 0) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DOC_PROP_TYPE_DEL_USED_MAPPING).build());
		}

		if (!errors.isEmpty()) {
			type.setErrors(errors);
			throw new PgcnValidationException(type, errors);
		}
	}

	/**
	 * Définit le rang s'il n'est pas renseigné
	 * @param propertyType
	 */
	private void handleRank(final DocPropertyType propertyType) {
		if (propertyType.getRank() == null) {
			final Integer currentRank = docPropertyTypeRepository
				.findCurrentRankForPropertyType(propertyType.getSuperType());
			final Integer nextRank = currentRank != null ? currentRank + 1 : 1;
			propertyType.setRank(nextRank);
		}
	}

}
