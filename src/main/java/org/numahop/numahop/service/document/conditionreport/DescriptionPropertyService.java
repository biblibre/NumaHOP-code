package org.numahop.numahop.service.document.conditionreport;

import org.numahop.numahop.domain.document.conditionreport.DescriptionProperty;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.exception.message.PgcnError;
import org.numahop.numahop.exception.message.PgcnErrorCode;
import org.numahop.numahop.exception.message.PgcnList;
import org.numahop.numahop.repository.document.conditionreport.DescriptionPropertyRepository;
import org.numahop.numahop.repository.document.conditionreport.DescriptionRepository;
import org.numahop.numahop.repository.document.conditionreport.DescriptionValueRepository;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DescriptionPropertyService {

	private final DescriptionRepository descriptionRepository;

	private final DescriptionPropertyRepository descriptionPropertyRepository;

	private final DescriptionValueRepository descriptionValueRepository;

	@Autowired
	public DescriptionPropertyService(final DescriptionRepository descriptionRepository,
			final DescriptionPropertyRepository descriptionPropertyRepository,
			final DescriptionValueRepository descriptionValueRepository) {
		this.descriptionRepository = descriptionRepository;
		this.descriptionPropertyRepository = descriptionPropertyRepository;
		this.descriptionValueRepository = descriptionValueRepository;
	}

	@Transactional(readOnly = true)
	public List<DescriptionProperty> findAll() {
		return descriptionPropertyRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<DescriptionProperty> findAllOrderByOrder() {
		return descriptionPropertyRepository.findAllByOrderByOrderAsc();
	}

	@Transactional(readOnly = true)
	public DescriptionProperty findByIdentifier(final String identifier) {
		return descriptionPropertyRepository.findById(identifier).orElseThrow();
	}

	@Transactional
	public void delete(final String identifier) {
		descriptionPropertyRepository.findById(identifier).ifPresent(property -> {
			// Validation de la suppression
			validateDeletion(property);
			// Suppression
			descriptionValueRepository.deleteByPropertyIdentifier(identifier);
			descriptionPropertyRepository.deleteById(identifier);
		});
	}

	private void validateDeletion(final DescriptionProperty property) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// Descriptions
		final Long descCount = descriptionRepository.countByProperty(property);
		if (descCount > 0) {
			errors.add(builder.reinit()
				.setCode(PgcnErrorCode.DESC_PROPERTY_DEL_EXISTS_DESC)
				.setAdditionalComplement(descCount)
				.build());
		}

		if (!errors.isEmpty()) {
			property.setErrors(errors);
			throw new PgcnValidationException(property, errors);
		}
	}

	@Transactional
	public DescriptionProperty save(final DescriptionProperty property) throws PgcnValidationException {
		validate(property);
		return descriptionPropertyRepository.save(property);
	}

	private void validate(final DescriptionProperty property) throws PgcnValidationException {
		final PgcnList<PgcnError> errors = new PgcnList<>();
		final PgcnError.Builder builder = new PgcnError.Builder();

		// le libellé est obligatoire
		if (StringUtils.isBlank(property.getLabel())) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DESC_PROPERTY_LABEL_MANDATORY).setField("label").build());
		}
		// le type est obligatoire
		if (property.getType() == null) {
			errors.add(builder.reinit().setCode(PgcnErrorCode.DESC_PROPERTY_TYPE_MANDATORY).setField("type").build());
		}

		if (!errors.isEmpty()) {
			property.setErrors(errors);
			throw new PgcnValidationException(property, errors);
		}
	}

	public enum FakeDescriptionProperty {

		INSURANCE, NB_VIEW_BODY, NB_VIEW_BINDING, NB_VIEW_ADDITIONNAL, ADDITIONNAL_DESC, BODY_DESC, BINDING_DESC,
		DIMENSION

	}

}
