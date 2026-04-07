package org.numahop.numahop.service.sample;

import org.numahop.numahop.domain.document.sample.Sample;
import org.numahop.numahop.domain.dto.sample.SampleDTO;
import org.numahop.numahop.exception.PgcnBusinessException;
import org.numahop.numahop.exception.PgcnValidationException;
import org.numahop.numahop.repository.sample.SampleRepository;
import org.numahop.numahop.service.sample.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleService {

	private final SampleRepository sampleRepository;

	@Autowired
	public SampleService(final SampleRepository sampleRepository) {
		this.sampleRepository = sampleRepository;
	}

	@Transactional
	public Sample save(final Sample sample) throws PgcnValidationException, PgcnBusinessException {
		final Sample savedSample = sampleRepository.save(sample);
		return sampleRepository.getOne(savedSample.getIdentifier());
	}

	@Transactional
	public void delete(final String identifier) {
		sampleRepository.deleteById(identifier);
	}

	@Transactional(readOnly = true)
	public Sample getOneWithDep(final String id) {
		return sampleRepository.getSampleWithDep(id);
	}

	@Transactional(readOnly = true)
	public SampleDTO getOne(final String id) {
		final Sample sample = sampleRepository.getOne(id);
		return SampleMapper.INSTANCE.sampleToSampleDTO(sample);
	}

	@Transactional(readOnly = true)
	public SampleDTO findByDelivery(final String deliveryId) {
		final Sample sample = sampleRepository.findByDeliveryIdentifier(deliveryId);
		return SampleMapper.INSTANCE.sampleToSampleDTO(sample);
	}

}
