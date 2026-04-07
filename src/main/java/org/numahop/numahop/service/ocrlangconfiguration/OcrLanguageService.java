package org.numahop.numahop.service.ocrlangconfiguration;

import org.numahop.numahop.domain.dto.ocrlangconfiguration.OcrLanguageDTO;
import org.numahop.numahop.domain.ocrlangconfiguration.OcrLanguage;
import org.numahop.numahop.repository.ocrlangconfiguration.OcrLanguageRepository;
import org.numahop.numahop.service.ocrlangconfiguration.mapper.OcrLanguageMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OcrLanguageService {

	private final OcrLanguageRepository ocrLanguageRepository;

	public OcrLanguageService(final OcrLanguageRepository ocrLanguageRepository) {
		this.ocrLanguageRepository = ocrLanguageRepository;
	}

	@Transactional(readOnly = true)
	public List<OcrLanguageDTO> findAll() {
		final List<OcrLanguage> langs = ocrLanguageRepository.findAllByOrderByLabelAsc();
		return OcrLanguageMapper.INSTANCE.objsToDtos(langs);
	}

	@Transactional(readOnly = true)
	public OcrLanguageDTO getLanguageDTO(final String id) {
		return OcrLanguageMapper.INSTANCE.objToDTO(ocrLanguageRepository.getOne(id));
	}

	@Transactional(readOnly = true)
	public OcrLanguage getOne(final String id) {
		return ocrLanguageRepository.getOne(id);
	}

	// @Transactional(readOnly = true)
	// public List<OcrLanguageDTO> findLangsByLibrary(final String libraryId) {
	//
	//
	// //final List<OcrLanguage> langs =
	// ocrLanguageRepository.findLanguagesByLibrary(libraryId);
	//
	// final List<ActivatedOcrLanguage> langs =
	// ocrLanguageRepository.findLanguagesByLibrary(libraryId);
	//
	// return null; //OcrLanguageMapper.INSTANCE.objsToDtos(langs);
	// }

}
